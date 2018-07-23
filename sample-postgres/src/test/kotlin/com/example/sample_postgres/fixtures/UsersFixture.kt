package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class UsersFixture (
    val id: Int = 0, // primary key
    val name: String = "", // name
    val job: String = "", // job name
    val status: String = "", // activate status
    val age: Int = 0, // age
    val nick_name: String? = null // nick name
)

fun DbSetupBuilder.insertUsersFixture(f: UsersFixture) {
    insertInto("users") {
        mappedValues(
                "id" to f.id,
                "name" to f.name,
                "job" to f.job,
                "status" to f.status,
                "age" to f.age,
                "nick_name" to f.nick_name
        )
    }
}