package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Store (
    val store_id: Int = 0, 
    val manager_staff_id: Short = 0, 
    val address_id: Short = 0, 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertStore(f: Store) {
    insertInto("store") {
        mappedValues(
                "store_id" to f.store_id,
                "manager_staff_id" to f.manager_staff_id,
                "address_id" to f.address_id,
                "last_update" to f.last_update
        )
    }
}