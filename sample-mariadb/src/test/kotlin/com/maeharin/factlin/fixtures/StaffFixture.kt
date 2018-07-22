package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class StaffFixture (
    val staff_id: Byte = 0, // 
    val first_name: String = "", // 
    val last_name: String = "", // 
    val address_id: Short = 0, // 
    val picture: Byte? = null, // 
    val email: String? = null, // 
    val store_id: Byte = 0, // 
    val active: Byte = 1, // 
    val username: String = "", // 
    val password: String? = null, // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertStaffFixture(f: StaffFixture) {
    insertInto("staff") {
        mappedValues(
                "staff_id" to f.staff_id,
                "first_name" to f.first_name,
                "last_name" to f.last_name,
                "address_id" to f.address_id,
                "picture" to f.picture,
                "email" to f.email,
                "store_id" to f.store_id,
                "active" to f.active,
                "username" to f.username,
                "password" to f.password,
                "last_update" to f.last_update
        )
    }
}