package com.example.sample_postgres.dbtest

import com.example.sample_postgres.fixtures.UsersFixture
import com.example.sample_postgres.fixtures.insertUsersFixture
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class WithDbsetupTest{
    val dest = DriverManagerDestination("jdbc:postgresql://192.168.99.100/factlin", "postgres", "")

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
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture())
        }.launch()
        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users")
        assertTrue(rs.next())
        assertAll("users assertions",
                { assertEquals("", rs.getString("name"))},
                { assertEquals("engineer", rs.getString("job"))},
                { assertEquals("ACTIVE", rs.getString("status"))}
        )
    }
}