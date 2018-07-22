package com.maeharin.factlin.ext

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtensionTest {
    @Test
    fun testToCamelCase() {
        assertEquals("CityBoy", "city_boy".toCamelCase())
        assertEquals("cityBoy", "city_boy".toCamelCase(false))
    }
}