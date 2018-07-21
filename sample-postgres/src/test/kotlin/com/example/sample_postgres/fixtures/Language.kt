package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Language (
    val language_id: Int = 0, 
    val name: String = "", 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertLanguage(f: Language) {
    insertInto("language") {
        mappedValues(
                "language_id" to f.language_id,
                "name" to f.name,
                "last_update" to f.last_update
        )
    }
}