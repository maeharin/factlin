package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDate
import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class CustomerFixture (
    val customer_id: Int = 0, 
    val store_id: Short = 0, 
    val first_name: String = "", 
    val last_name: String = "", 
    val email: String? = null, 
    val address_id: Short = 0, 
    val activebool: Boolean = false, 
    val create_date: LocalDate = LocalDate.now(), 
    val last_update: LocalDateTime? = LocalDateTime.now(), 
    val active: Int? = null 
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
                "activebool" to f.activebool,
                "create_date" to f.create_date,
                "last_update" to f.last_update,
                "active" to f.active
        )
    }
}