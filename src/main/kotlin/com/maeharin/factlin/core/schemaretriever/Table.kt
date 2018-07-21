package com.maeharin.factlin.core.schemaretriever

data class Table(
        val name: String,
        val comment: String?,
        val schema: String?,
        val catalog: String?,
        val columns: List<Column>
)
