package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class RentalFixture (
    val rental_id: Int = 0, 
    val rental_date: LocalDateTime = LocalDateTime.now(), 
    val inventory_id: Int = 0, 
    val customer_id: Short = 0, 
    val return_date: LocalDateTime? = null, 
    val staff_id: Short = 0, 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertRentalFixture(f: RentalFixture) {
    insertInto("rental") {
        mappedValues(
                "rental_id" to f.rental_id,
                "rental_date" to f.rental_date,
                "inventory_id" to f.inventory_id,
                "customer_id" to f.customer_id,
                "return_date" to f.return_date,
                "staff_id" to f.staff_id,
                "last_update" to f.last_update
        )
    }
}