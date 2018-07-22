package com.example.sample_postgres.fixtures

// aaaaaaaaaaaaaaaa
// bbbbbbbbbbbbbbbb

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class InventoryFixture (
    val inventory_id: Int = 0, 
    val film_id: Short = 0, 
    val store_id: Short = 0, 
    val last_update: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertInventoryFixture(f: InventoryFixture) {
    insertInto("inventory") {
        mappedValues(
                "inventory_id" to f.inventory_id,
                "film_id" to f.film_id,
                "store_id" to f.store_id,
                "last_update" to f.last_update
        )
    }
}