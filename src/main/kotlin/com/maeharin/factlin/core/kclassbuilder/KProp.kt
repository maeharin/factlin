package com.maeharin.factlin.core.kclassbuilder

data class KProp(
        val tableName: String,
        val name: String,
        val columnName: String,
        val type: KType,
        private val typeName: String,
        private val defaultValue: String,
        val isNullable: Boolean,
        val isPrimaryKey: Boolean,
        val comment: String?
) {

    /**
     * for backward compatibility
     */
    fun name(): String {
        return name
    }

    /**
     * build default value
     * todo customizable
     */
    fun defaultValue(): String {
        return defaultValue
    }
}

