package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class City (
    val city_id: Int = 0, 
    val city: String = "", 
    val country_id: Short = 0, 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertCity(f: City) {
    insertInto("city") {
        mappedValues(
                "city_id" to f.city_id,
                "city" to f.city,
                "country_id" to f.country_id,
                "last_update" to f.last_update
        )
    }
}