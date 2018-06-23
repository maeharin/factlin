package com.maeharin.factlin

import com.maeharin.factlin.core.FactlinCore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FactlinCoreTest {
    @Test
    fun testFactlin() {
        val factlin = FactlinCore("aaa")
        factlin.exec()
    }
}