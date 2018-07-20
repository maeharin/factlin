package com.maeharin.factlin.core.schema

data class Column(
        val name: String,
        val type: Int,
        val typeName: String,
        val defaultValue: String?,
        val isNullable: Boolean,
        val isPrimaryKey: Boolean,
        val comment: String?
)
