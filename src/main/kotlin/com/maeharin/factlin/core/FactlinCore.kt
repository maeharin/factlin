package com.maeharin.factlin.core

import com.maeharin.factlin.core.code.CodeGenerator
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

        val tables = SchemaRetriever(extension).retrieve()

        tables.forEach { table ->
            CodeGenerator(table).generate()
        }
    }
}

