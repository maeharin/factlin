package com.example.sample_mariadb.dbtest

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.time.LocalDateTime

class WithDbsetupTest {
    val url = System.getenv("FACTLIN_MARIA_DB_URL")
    val dest = DriverManagerDestination(url, "root", "test1234")

    init {
        Class.forName("org.mariadb.jdbc.Driver")
    }

    @Test
    fun testInsertUser() {
        val now = LocalDateTime.parse("2018-01-01T23:59:59")
        val today = LocalDate.parse("2018-01-01")

        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture(id = 1, name = "user1", birth_day = today, created_timestamp = now))
            insertUsersFixture(UsersFixture(id = 2, name = "user2", birth_day = today, is_admin = 1, created_timestamp = now))
        }.launch()
        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users order by id asc")

        assertTrue(rs.next())
        assertAll("users assertions",
                { assertEquals(1, rs.getInt("id"))},
                { assertEquals("user1", rs.getString("name"))},
                { assertEquals("", rs.getString("job"))},
                { assertEquals("", rs.getString("status"))},
                { assertEquals(0, rs.getInt("age"))},
                { assertEquals(0.toBigDecimal(), rs.getBigDecimal("score"))},
                { assertEquals(false, rs.getBoolean("is_admin"))},
                { assertEquals(java.sql.Date.valueOf(today), rs.getDate("birth_day"))},
                { assertEquals(null, rs.getString("nick_name"))},
                { assertEquals(now, rs.getTimestamp("created_timestamp").toLocalDateTime())},
                { assertEquals(null, rs.getTimestamp("updated_timestamp"))}
        )

        assertTrue(rs.next())
        assertAll("users assertions",
                { assertEquals(2, rs.getInt("id"))},
                { assertEquals("user2", rs.getString("name"))},
                { assertEquals("", rs.getString("job"))},
                { assertEquals("", rs.getString("status"))},
                { assertEquals(0, rs.getInt("age"))},
                { assertEquals(0.toBigDecimal(), rs.getBigDecimal("score"))},
                { assertEquals(true, rs.getBoolean("is_admin"))},
                { assertEquals(java.sql.Date.valueOf(today), rs.getDate("birth_day"))},
                { assertEquals(null, rs.getString("nick_name"))},
                { assertEquals(now, rs.getTimestamp("created_timestamp").toLocalDateTime())},
                { assertEquals(null, rs.getTimestamp("updated_timestamp"))}
        )

    }
}