package com.example.sample_mariadb.dbtest

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class WithDbsetupTest {
    val url = System.getenv("FACTLIN_MARIA_DB_URL")
    val dest = DriverManagerDestination(url, "root", "test1234")

    init {
        Class.forName("org.mariadb.jdbc.Driver")
    }

    @Test
    fun testInsertUser() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture())
        }.launch()
        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users")
        assertTrue(rs.next())
        assertAll("users assertions",
                { assertEquals("", rs.getString("name")) }
        )
    }
}