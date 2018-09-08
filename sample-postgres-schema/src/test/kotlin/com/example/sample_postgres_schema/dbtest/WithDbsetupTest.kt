package com.example.sample_postgres_schema.dbtest

import com.maeharin.factlin.fixtures.OtherSchemaArticleFixture
import com.maeharin.factlin.fixtures.OtherSchemaUsersFixture
import com.maeharin.factlin.fixtures.insertOtherSchemaArticleFixture
import com.maeharin.factlin.fixtures.insertOtherSchemaUsersFixture
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class WithDbsetupTest {
    val url = System.getenv("FACTLIN_POSTGRES_DB_URL")
    val dest = DriverManagerDestination(url, "postgres", "")

    init {
        Class.forName("org.postgresql.Driver")
    }

    @Test
    fun `test other schema`() {
        dbSetup(dest) {
            deleteAllFrom(listOf("other_schema.users", "other_schema.article"))
            insertOtherSchemaUsersFixture(OtherSchemaUsersFixture(id = 1, name = "user1"))
            insertOtherSchemaUsersFixture(OtherSchemaUsersFixture(id = 2, name = "user2"))
            insertOtherSchemaArticleFixture(OtherSchemaArticleFixture(id = 1, title = "article1"))
            insertOtherSchemaArticleFixture(OtherSchemaArticleFixture(id = 2, title = "article2"))
        }.launch()
        val stmt = dest.connection.createStatement()

        val userRs = stmt.executeQuery("select * from other_schema.users order by id asc")
        assertTrue(userRs.next())
        assertAll("user1",
                { assertEquals(1, userRs.getInt("id")) },
                { assertEquals("user1", userRs.getString("name")) }
        )
        assertTrue(userRs.next())
        assertAll("user2",
                { assertEquals(2, userRs.getInt("id")) },
                { assertEquals("user2", userRs.getString("name")) }
        )

        val articleRs = stmt.executeQuery("select * from other_schema.article order by id asc")
        assertTrue(articleRs.next())
        assertAll("article1",
                { assertEquals(1, articleRs.getInt("id")) },
                { assertEquals("article1", articleRs.getString("title")) }
        )
        assertTrue(articleRs.next())
        assertAll("article2",
                { assertEquals(2, articleRs.getInt("id")) },
                { assertEquals("article2", articleRs.getString("title")) }
        )
    }
}