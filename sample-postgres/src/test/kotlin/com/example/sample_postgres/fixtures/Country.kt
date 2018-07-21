package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Country (
    val country_id: Int = 0, 
    val country: String = "", 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertCountry(f: Country) {
    insertInto("country") {
        mappedValues(
                "country_id" to f.country_id,
                "country" to f.country,
                "last_update" to f.last_update
        )
    }
}