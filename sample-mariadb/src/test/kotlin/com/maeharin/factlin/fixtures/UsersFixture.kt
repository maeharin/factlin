package com.maeharin.factlin.fixtures

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class UsersFixture (
    val id: Long = 0, // primary key
    val name: String = "", // user name
    val job: String = "", // job name
    val status: String = "", // activate status
    val age: Int = 0, // user age
    val score: BigDecimal = 0.toBigDecimal(), // game score
    val is_admin: Byte = 0, // is admin user or not
    val birth_day: LocalDate = LocalDate.now(), // user birth day
    val nick_name: String? = null, // user nick name
    val created_timestamp: LocalDateTime = LocalDateTime.now(), // 
    val updated_timestamp: LocalDateTime? = null // 
)

fun DbSetupBuilder.insertUsersFixture(f: UsersFixture) {
    insertInto("users") {
        mappedValues(
                "id" to f.id,
                "name" to f.name,
                "job" to f.job,
                "status" to f.status,
                "age" to f.age,
                "score" to f.score,
                "is_admin" to f.is_admin,
                "birth_day" to f.birth_day,
                "nick_name" to f.nick_name,
                "created_timestamp" to f.created_timestamp,
                "updated_timestamp" to f.updated_timestamp
        )
    }
}