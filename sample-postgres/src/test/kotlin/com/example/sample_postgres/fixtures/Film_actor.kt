package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Film_actor (
    val actor_id: Short = 0, 
    val film_id: Short = 0, 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertFilm_actor(f: Film_actor) {
    insertInto("film_actor") {
        mappedValues(
                "actor_id" to f.actor_id,
                "film_id" to f.film_id,
                "last_update" to f.last_update
        )
    }
}