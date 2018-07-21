package com.maeharin.factlin.core.kclassbuilder

import com.maeharin.factlin.core.Dialect
import com.maeharin.factlin.core.schemaretriever.Table
import java.sql.Types

class KClassBuilder(
        val table: Table,
        val dialect: Dialect
) {
    fun build(): KClass {
        val dialectConverter = when(dialect) {
            Dialect.POSTGRES -> PostgresDialectConverter()
            Dialect.MARIADB -> MariadbDialectConverter()
        }

        return KClass(
                tableName = table.name,
                name = table.name,
                comment = table.comment ?: "",
                schema = table.schema,
                catalog = table.catalog,
                props = table.columns.map { columnMeta ->
                    val type = _buildKType(columnMeta.type)

                    KProp(
                            tableName = table.name,
                            columnName = columnMeta.name,
                            type = type,
                            typeName = columnMeta.typeName,
                            defaultValue = columnMeta.defaultValue?.let {
                                dialectConverter.convertDefaultValue(it, type)
                            },
                            isNullable = columnMeta.isNullable,
                            isPrimaryKey = columnMeta.isPrimaryKey,
                            comment = columnMeta.comment
                    )
                }
        )
    }


    /**
     * build prop's Kotlin type
     */
    private fun _buildKType(type: Int): KType {
        // todo customizable
        return when (type) {
        // java.sql.Types => Kotlin type
        // see: https://docs.oracle.com/cd/E16338_01/java.112/b56281/datacc.htm#BHCJBJCC
            Types.BIT -> KType.BOOLEAN
            Types.TINYINT -> KType.BYTE
            Types.SMALLINT -> KType.SHORT
            Types.INTEGER -> KType.INT
            Types.BIGINT -> KType.LONG
            Types.FLOAT -> KType.DOUBLE
            Types.REAL -> KType.FLOAT
            Types.DOUBLE -> KType.DOUBLE
            Types.NUMERIC -> KType.BIG_DECIMAL
            Types.DECIMAL -> KType.BIG_DECIMAL
            Types.CHAR -> KType.STRING
            Types.VARCHAR -> KType.STRING
            Types.LONGVARCHAR -> KType.STRING
            Types.DATE -> KType.LOCAL_DATE
            Types.TIME -> KType.LOCAL_TIME
            Types.TIMESTAMP -> KType.LOCAL_DATE_TIME
            Types.BINARY -> KType.BYTE
            Types.VARBINARY -> KType.BYTE
            Types.LONGVARBINARY -> KType.BYTE
            Types.BOOLEAN -> KType.BOOLEAN
            Types.NCHAR -> KType.STRING
            Types.NVARCHAR -> KType.STRING
            Types.LONGNVARCHAR -> KType.STRING
            Types.TIME_WITH_TIMEZONE -> KType.LOCAL_TIME
            Types.TIMESTAMP_WITH_TIMEZONE -> KType.LOCAL_DATE_TIME
            else -> {
                //Types.NULL
                //Types.OTHER
                //Types.JAVA_OBJECT
                //Types.DISTINCT
                //Types.STRUCT
                //Types.ARRAY
                //Types.BLOB
                //Types.CLOB
                //Types.REF
                //Types.DATALINK
                //Types.ROWID
                //Types.NCLOB
                //Types.SQLXML
                //Types.REF_CURSOR
                println("---------------------")
                //println("unkown. [${tableName}.${name}] type: ${type}")
                println("---------------------")
                KType.STRING
            }
        }
    }

}