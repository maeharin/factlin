package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class StoreFixture (
    val store_id: Byte = 0, // 
    val manager_staff_id: Byte = 0, // 
    val address_id: Short = 0, // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertStoreFixture(f: StoreFixture) {
    insertInto("store") {
        mappedValues(
                "store_id" to f.store_id,
                "manager_staff_id" to f.manager_staff_id,
                "address_id" to f.address_id,
                "last_update" to f.last_update
        )
    }
}