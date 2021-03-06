package com.maeharin.factlin.fixtures

import java.time.LocalDate
import java.math.BigDecimal
import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class FilmFixture (
    val film_id: Short = 0, // 
    val title: String = "", // 
    val description: String? = null, // 
    val release_year: LocalDate? = null, // 
    val language_id: Byte = 0, // 
    val original_language_id: Byte? = null, // 
    val rental_duration: Byte = 0, // 
    val rental_rate: BigDecimal = 0.toBigDecimal(), // 
    val length: Short? = null, // 
    val replacement_cost: BigDecimal = 0.toBigDecimal(), // 
    val rating: String? = null, // 
    val special_features: String? = null, // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertFilmFixture(f: FilmFixture) {
    insertInto("film") {
        mappedValues(
                "film_id" to f.film_id,
                "title" to f.title,
                "description" to f.description,
                "release_year" to f.release_year,
                "language_id" to f.language_id,
                "original_language_id" to f.original_language_id,
                "rental_duration" to f.rental_duration,
                "rental_rate" to f.rental_rate,
                "length" to f.length,
                "replacement_cost" to f.replacement_cost,
                "rating" to f.rating,
                "special_features" to f.special_features,
                "last_update" to f.last_update
        )
    }
}