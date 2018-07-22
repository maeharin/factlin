package com.maeharin.factlin.fixtures

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class FilmTextFixture (
    val film_id: Short = 0, // 
    val title: String = "", // 
    val description: String? = null // 
)

fun DbSetupBuilder.insertFilmTextFixture(f: FilmTextFixture) {
    insertInto("film_text") {
        mappedValues(
                "film_id" to f.film_id,
                "title" to f.title,
                "description" to f.description
        )
    }
}