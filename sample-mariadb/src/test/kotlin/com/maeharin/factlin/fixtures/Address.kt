package com.maeharin.factlin.fixtures

import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Address (
    val address_id: Short = 0, // 
    val address: String = "", // 
    val address2: String? = null, // 
    val district: String = "", // 
    val city_id: Short = 0, // 
    val postal_code: String? = null, // 
    val phone: String = "", // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertAddress(f: Address) {
    insertInto("address") {
        mappedValues(
                "address_id" to f.address_id,
                "address" to f.address,
                "address2" to f.address2,
                "district" to f.district,
                "city_id" to f.city_id,
                "postal_code" to f.postal_code,
                "phone" to f.phone,
                "last_update" to f.last_update
        )
    }
}