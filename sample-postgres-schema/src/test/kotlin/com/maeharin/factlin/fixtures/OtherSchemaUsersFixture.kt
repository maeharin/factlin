package com.maeharin.factlin.fixtures

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class OtherSchemaUsersFixture (
    val id: Int = 0, 
    val name: String = "" 
)

fun DbSetupBuilder.insertOtherSchemaUsersFixture(f: OtherSchemaUsersFixture) {
    insertInto("other_schema.users") {
        mappedValues(
                "id" to f.id,
                "name" to f.name
        )
    }
}