package com.maeharin.factlin.fixtures

import java.math.BigDecimal
import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class Payment (
    val payment_id: Short = 0, // 
    val customer_id: Short = 0, // 
    val staff_id: Byte = 0, // 
    val rental_id: Int? = null, // 
    val amount: BigDecimal = 0.toBigDecimal(), // 
    val payment_date: LocalDateTime = LocalDateTime.now(), // 
    val last_update: LocalDateTime = LocalDateTime.now() // 
)

fun DbSetupBuilder.insertPayment(f: Payment) {
    insertInto("payment") {
        mappedValues(
                "payment_id" to f.payment_id,
                "customer_id" to f.customer_id,
                "staff_id" to f.staff_id,
                "rental_id" to f.rental_id,
                "amount" to f.amount,
                "payment_date" to f.payment_date,
                "last_update" to f.last_update
        )
    }
}