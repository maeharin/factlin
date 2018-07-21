package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Actor (
    val actor_id: Int = 0, 
    val first_name: String = "", 
    val last_name: String = "", 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertActor(f: Actor) {
    insertInto("actor") {
        mappedValues(
                "actor_id" to f.actor_id,
                "first_name" to f.first_name,
                "last_name" to f.last_name,
                "last_update" to f.last_update
        )
    }
}