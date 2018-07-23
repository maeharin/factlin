package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class CustomerFixture (
    val customer_id: Short = 0, // 
    val store_id: Byte = 0, // 
    val first_name: String = "", // 
    val last_name: String = "", // 
    val email: String? = null, // 
    val address_id: Short = 0, // 
    val active: Byte = 0, // 
    val create_date: LocalDateTime = LocalDateTime.now(), // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertCustomerFixture(f: CustomerFixture) {
    insertInto("customer") {
        mappedValues(
                "customer_id" to f.customer_id,
                "store_id" to f.store_id,
                "first_name" to f.first_name,
                "last_name" to f.last_name,
                "email" to f.email,
                "address_id" to f.address_id,
                "active" to f.active,
                "create_date" to f.create_date,
                "last_update" to f.last_update
        )
    }
}