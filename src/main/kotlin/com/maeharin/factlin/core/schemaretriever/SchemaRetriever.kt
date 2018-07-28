package com.maeharin.factlin.core.schemaretriever

import com.maeharin.factlin.core.Dialect
import com.maeharin.factlin.gradle.FactlinExtension
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.util.*

class SchemaRetriever(
        val extension: FactlinExtension,
        val dialect: Dialect
) {
    fun retrieve(): List<Table> {
        when(dialect) {
            Dialect.POSTGRES -> {
                Class.forName("org.postgresql.Driver")
            }
            Dialect.MARIADB -> {
                Class.forName("org.mariadb.jdbc.Driver")
            }
        }

        val conn = if (dialect == Dialect.MARIADB) {
            val prop = Properties().also {
                it.setProperty("user", extension.dbUser)
                it.setProperty("password", extension.dbPassword)
                // By default, mariadb jdbc driver treat tinyint(1) as Boolean
                // We dont't treat tinyint(1) as Boolean.
                // Because when 1 is provided as defaultValue, type mismatch,
                // similar case: https://github.com/prestodb/presto/issues/5102
                it.setProperty("tinyInt1isBit", "false")
            }
            DriverManager.getConnection(extension.dbUrl, prop)
        } else {
            DriverManager.getConnection(extension.dbUrl, extension.dbUser, extension.dbPassword)
        }

        val metaData = conn.metaData

        return _getTables(metaData)
                .filter {table ->
                    !extension.excludeTables.contains(table.name)
                }
                .map { table ->
                    table.copy(columns = _getColumns(metaData, table))
                }
    }

    private fun _getTables(metaData: DatabaseMetaData): List<Table> {
        // todo
        val tableSet = metaData.getTables(null, null, "%", arrayOf("TABLE"))

        val tables = ArrayList<Table>()

        while (tableSet.next()) {
            val table = Table(
                    name = tableSet.getString("TABLE_NAME"),
                    comment = tableSet.getString("REMARKS"),
                    schema = tableSet.getString("TABLE_SCHEM"),
                    catalog = tableSet.getString("TABLE_CAT"),
                    columns = emptyList()
            )

            tables.add(table)
        }

        tableSet.close()

        return tables
    }

    private fun _getColumns(metaData: DatabaseMetaData, table: Table): ArrayList<Column> {
        val primaryKeySet = metaData.getPrimaryKeys(null, null, table.name)
        val primaryKeyNames = ArrayList<String>()
        while(primaryKeySet.next()) {
            primaryKeyNames.add(primaryKeySet.getString("COLUMN_NAME"))
        }
        primaryKeySet.close()

        val colSet = metaData.getColumns(null, null, table.name, "%")
        val columns = ArrayList<Column>()
        while(colSet.next()) {
            val name = colSet.getString("COLUMN_NAME")

            val column = Column(
                    name = name,
                    typeName = colSet.getString("TYPE_NAME"),
                    type = colSet.getInt("DATA_TYPE"),
                    isNullable = colSet.getBoolean("NULLABLE"),
                    defaultValue =  colSet.getString("COLUMN_DEF"),
                    isPrimaryKey = primaryKeyNames.contains(name),
                    comment = colSet.getString("REMARKS")
            )
            columns.add(column)
        }
        colSet.close()

        return columns
    }
}
