package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Category (
    val category_id: Byte = 0, // 
    val name: String = "", // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertCategory(f: Category) {
    insertInto("category") {
        mappedValues(
                "category_id" to f.category_id,
                "name" to f.name,
                "last_update" to f.last_update
        )
    }
}