package com.maeharin.factlin.core

import com.maeharin.factlin.core.code.CodeGenerator
import com.maeharin.factlin.core.dialect.MariadbDialect
import com.maeharin.factlin.core.dialect.PostgresDialect
import com.maeharin.factlin.core.schema.SchemaRetriever
import com.maeharin.factlin.gradle.FactlinExtension

data class FactlinCore(
        val extension: FactlinExtension
) {
    fun exec() {
        // todo use logger
        println("------------------------------------")
        println("[factlin generate]")
        println("------------------------------------")

        val dialect = when(extension.dbDialect) {
            "postgres" -> {
                Class.forName("org.postgresql.Driver")
                PostgresDialect()
            }
            "mariadb" -> {
                Class.forName("org.mariadb.jdbc.Driver")
                MariadbDialect()
            }
            else -> throw Exception("dialect ${extension.dbDialect} is not supported")
        }

        val tables = SchemaRetriever(extension, dialect).retrieve()

        tables.forEach { table ->
            CodeGenerator(table, dialect).generate()
        }
    }
}

