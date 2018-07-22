package com.maeharin.factlin.core.kclassbuilder

import com.maeharin.factlin.core.Dialect
import com.maeharin.factlin.core.schemaretriever.Column
import com.maeharin.factlin.core.schemaretriever.Table
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class KClassBuilderTest {
    @Test
    fun test_build_with_postgres_dialect() {
        val table = Table(
                name = "users",
                comment = "user table",
                schema = "public",
                catalog = null,
                columns = listOf(
                        Column(name = "id", type =4, typeName = "serial", defaultValue = "nextval('users_id_seq'::regclass)", isNullable = false, isPrimaryKey = true, comment = "primary key"),
                        Column(name = "name", type =12, typeName = "varchar", defaultValue = null, isNullable = false, isPrimaryKey = false, comment = "name"),
                        Column(name = "job", type =12, typeName = "varchar", defaultValue = "'engineer'::character varying", isNullable = false, isPrimaryKey = false, comment = "job name"),
                        Column(name = "status", type =12, typeName = "varchar", defaultValue = "'ACTIVE'::character varying", isNullable = false, isPrimaryKey = false, comment = "activate status"),
                        Column(name = "age", type =4, typeName = "int4", defaultValue = "30", isNullable = false, isPrimaryKey = false, comment = "age"),
                        Column(name = "nick_name", type =12, typeName = "varchar", defaultValue = null, isNullable = true, isPrimaryKey = false, comment = "nick name")
                )
        )

        val kClass = KClassBuilder(table, Dialect.POSTGRES).build()

        assertAll(
                "assert proper kClass",
                { assertEquals("UsersFixture", kClass.name()) },
                { assertEquals("UsersFixture.kt", kClass.fileName()) },
                { assertEquals("users", kClass.tableName ) },
                { assertEquals("user table", kClass.comment ) },
                { assertEquals("public", kClass.schema ) },
                {
                    assertIterableEquals(setOf(
                            "import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder",
                            "import com.ninja_squad.dbsetup_kotlin.mappedValues"
                    ), kClass.imports())
                },
                {
                    val p = kClass.props[0]
                    assertEquals("id", p.columnName)
                    assertEquals(KType.INT, p.type)
                    assertEquals(false, p.isNullable)
                    assertEquals(true, p.isPrimaryKey)
                    assertEquals("primary key", p.comment)
                    assertEquals("id", p.name())
                    assertEquals("0", p.defaultValue())
                },
                {
                    val p = kClass.props[1]
                    assertEquals("name", p.columnName)
                    assertEquals(KType.STRING, p.type)
                    assertEquals(false, p.isNullable)
                    assertEquals(false, p.isPrimaryKey)
                    assertEquals("name", p.comment)
                    assertEquals("name", p.name())
                    assertEquals("\"\"", p.defaultValue())
                },
                {
                    val p = kClass.props[2]
                    assertEquals("job", p.columnName)
                    assertEquals(KType.STRING, p.type)
                    assertEquals(false, p.isNullable)
                    assertEquals(false, p.isPrimaryKey)
                    assertEquals("job name", p.comment)
                    assertEquals("job", p.name())
                    // todo fix this
                    assertEquals("\"'engineer'::character varying\"", p.defaultValue())
                },
                {
                    val p = kClass.props[3]
                    assertEquals("status", p.columnName)
                    assertEquals(KType.STRING, p.type)
                    assertEquals(false, p.isNullable)
                    assertEquals(false, p.isPrimaryKey)
                    assertEquals("activate status", p.comment)
                    assertEquals("status", p.name())
                    // todo fix this
                    assertEquals("\"'ACTIVE'::character varying\"", p.defaultValue())
                },
                {
                    val p = kClass.props[4]
                    assertEquals("age", p.columnName)
                    assertEquals(KType.INT, p.type)
                    assertEquals(false, p.isNullable)
                    assertEquals(false, p.isPrimaryKey)
                    assertEquals("age", p.comment)
                    assertEquals("age", p.name())
                    assertEquals("30", p.defaultValue())
                },
                {
                    val p = kClass.props[5]
                    assertEquals("nick_name", p.columnName)
                    assertEquals(KType.STRING, p.type)
                    assertEquals(true, p.isNullable)
                    assertEquals(false, p.isPrimaryKey)
                    assertEquals("nick name", p.comment)
                    assertEquals("nick_name", p.name())
                    assertEquals("null", p.defaultValue())
                }
        )
    }
}