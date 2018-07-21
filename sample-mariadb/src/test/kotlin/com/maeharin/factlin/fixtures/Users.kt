package com.maeharin.factlin.fixtures

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Users (
    val id: Long = 0, // primary key
    val name: String = "", // name
    val job: String = "'engineer'", // job name
    val status: String = "'ACTIVE'", // activate status
    val age: Int = 30, // age
    val nick_name: String? = null // nick name
)

fun DbSetupBuilder.insertUsers(f: Users) {
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