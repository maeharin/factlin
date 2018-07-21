package com.maeharin.factlin.core.kclassbuilder

import com.maeharin.factlin.util.toCamelCase

data class KProp(
        val tableName: String,
        private val name: String,
        val type: KType,
        private val typeName: String,
        private val defaultValue: Any?,
        val isNullable: Boolean,
        val isPrimaryKey: Boolean,
        val comment: String?
) {
    fun name(): String {
        return name.toCamelCase()
    }

    /**
     * build default value
     * todo customizable
     */
    fun defaultValue(): Any {
        if (defaultValue != null) {
            return defaultValue
        }

        if (isNullable) {
            return "null"
        }

        return type.defaultValue
    }
}

