package com.maeharin.factlin.core

class CodeGenerator(
        val table: Table
) {
    fun generate() {
        // todo
        println("""
            data class ${table.name} (
            """)

        table.columns.forEach { column ->
            println("""
                val ${column.name}: ${column.typeName}${column.isNullable} = ${column.defaultValue},
            """)
        }

        println("""
            )
            """)
    }
}