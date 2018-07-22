package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class ActorFixture (
    val actor_id: Short = 0, // 
    val first_name: String = "", // 
    val last_name: String = "", // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertActorFixture(f: ActorFixture) {
    insertInto("actor") {
        mappedValues(
                "actor_id" to f.actor_id,
                "first_name" to f.first_name,
                "last_name" to f.last_name,
                "last_update" to f.last_update
        )
    }
}