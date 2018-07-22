package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class CategoryFixture (
    val category_id: Int = 0, 
    val name: String = "", 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertCategoryFixture(f: CategoryFixture) {
    insertInto("category") {
        mappedValues(
                "category_id" to f.category_id,
                "name" to f.name,
                "last_update" to f.last_update
        )
    }
}