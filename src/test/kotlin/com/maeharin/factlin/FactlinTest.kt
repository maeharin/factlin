package com.maeharin.factlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FactlinTest {
    @Test
    fun testFactlin() {
        val factlin = Factlin()
        assertEquals("fuge", factlin.exec())
    }
}