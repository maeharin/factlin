package com.maeharin.factlin.core

import com.maeharin.factlin.core.code.CodeGenerator
import com.maeharin.factlin.core.kclassbuilder.KClassBuilder
import com.maeharin.factlin.core.schema.SchemaRetriever
import com.maeharin.factlin.gradle.FactlinExtension

enum class Dialect {
    POSTGRES,
    MARIADB
}

data class FactlinCore(
        val extension: FactlinExtension
) {
    fun exec() {
        // todo use logger
        println("------------------------------------")
        println("[factlin generate]")
        println("------------------------------------")

        val dialect = when(extension.dbDialect) {
            "postgres" -> Dialect.POSTGRES
            "mariadb" -> Dialect.MARIADB
            else -> throw Exception("dialect ${extension.dbDialect} is not supported")
        }

        val tables = SchemaRetriever(extension, dialect).retrieve()

        tables.forEach { table ->
            val klass = KClassBuilder(table, dialect).build()
            CodeGenerator(klass).generate()
        }
    }
}

