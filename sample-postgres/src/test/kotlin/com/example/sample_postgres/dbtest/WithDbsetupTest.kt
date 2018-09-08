package com.example.sample_postgres.dbtest

import com.example.sample_postgres.fixtures.OtherSchemaUsersFixture
import com.example.sample_postgres.fixtures.UsersFixture
import com.example.sample_postgres.fixtures.insertOtherSchemaUsersFixture
import com.example.sample_postgres.fixtures.insertUsersFixture
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.time.LocalDateTime

class WithDbsetupTest{
    val url = System.getenv("FACTLIN_POSTGRES_DB_URL")
    val dest = DriverManagerDestination(url, "postgres", "")

    init {
        Class.forName("org.postgresql.Driver")
    }

    // todo dbsetup is not supported postgres custom type.
    // film.rating is using postgres custom type
    // https://github.com/Ninja-Squad/DbSetup/issues/55
    //@Test
    //fun testInsertFilme() {
    //    dbSetup(dest) {
    //        deleteAllFrom(listOf("film"))
    //        insertFilm(Film())
    //    }.launch()
    //    val stmt = dest.connection.createStatement()
    //    val rs = stmt.executeQuery("select * from film")
    //    assertTrue(rs.next())
    //    assertAll("film assersion",
    //            { assertEquals(null, rs.getInt("film_id"))}
    //    )
    //}

    @Test
    fun testInsertUser() {
        val now = LocalDateTime.parse("2018-01-01T23:59:59")
        val today = LocalDate.parse("2018-01-01")

        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture(id = 1, name = "user1", birth_day = today, created_timestamp = now))
            insertUsersFixture(UsersFixture(id = 2, name = "user2", birth_day = today, is_admin = true, created_timestamp = now))
        }.launch()
        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users order by id asc")

        assertTrue(rs.next())
        assertAll("users assertions",
                { assertEquals(1, rs.getInt("id"))},
                { assertEquals("user1", rs.getString("name"))},
                { assertEquals("engineer", rs.getString("job"))},
                { assertEquals("ACTIVE", rs.getString("status"))},
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
                { assertEquals("engineer", rs.getString("job"))},
                { assertEquals("ACTIVE", rs.getString("status"))},
                { assertEquals(0, rs.getInt("age"))},
                { assertEquals(0.toBigDecimal(), rs.getBigDecimal("score"))},
                { assertEquals(true, rs.getBoolean("is_admin"))},
                { assertEquals(java.sql.Date.valueOf(today), rs.getDate("birth_day"))},
                { assertEquals(null, rs.getString("nick_name"))},
                { assertEquals(now, rs.getTimestamp("created_timestamp").toLocalDateTime())},
                { assertEquals(null, rs.getTimestamp("updated_timestamp"))}
        )
    }

    @Test
    fun `test other schema`() {
        dbSetup(dest) {
            deleteAllFrom(listOf("other_schema.users"))
            insertOtherSchemaUsersFixture(OtherSchemaUsersFixture(id = 1, name = "user1"))
            insertOtherSchemaUsersFixture(OtherSchemaUsersFixture(id = 2, name = "user2"))
        }.launch()
        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from other_schema.users order by id asc")

        assertTrue(rs.next())
        assertAll("user1",
                { assertEquals(1, rs.getInt("id")) },
                { assertEquals("user1", rs.getString("name")) }
        )

        assertTrue(rs.next())
        assertAll("user2",
                { assertEquals(2, rs.getInt("id")) },
                { assertEquals("user2", rs.getString("name")) }
        )
    }
}