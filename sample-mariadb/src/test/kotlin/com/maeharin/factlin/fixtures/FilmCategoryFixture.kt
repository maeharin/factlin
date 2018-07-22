package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class FilmCategoryFixture (
    val film_id: Short = 0, // 
    val category_id: Byte = 0, // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertFilmCategoryFixture(f: FilmCategoryFixture) {
    insertInto("film_category") {
        mappedValues(
                "film_id" to f.film_id,
                "category_id" to f.category_id,
                "last_update" to f.last_update
        )
    }
}