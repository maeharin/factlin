package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Songs (
    val id: Int = 0, // primary key
    val title: String? = null, // title of song
    val created_at: LocalDateTime? = LocalDateTime.now() // created timestamp
)

fun DbSetupBuilder.insertSongs(f: Songs) {
    insertInto("songs") {
        mappedValues(
                "id" to f.id,
                "title" to f.title,
                "created_at" to f.created_at
        )
    }
}