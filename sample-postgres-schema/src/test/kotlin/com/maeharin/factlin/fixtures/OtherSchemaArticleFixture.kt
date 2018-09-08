package com.maeharin.factlin.fixtures

import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class OtherSchemaArticleFixture (
    val id: Int = 0, 
    val title: String = "" 
)

fun DbSetupBuilder.insertOtherSchemaArticleFixture(f: OtherSchemaArticleFixture) {
    insertInto("other_schema.article") {
        mappedValues(
                "id" to f.id,
                "title" to f.title
        )
    }
}