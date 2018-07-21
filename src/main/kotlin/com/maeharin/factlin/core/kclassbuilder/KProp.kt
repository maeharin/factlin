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
        val dv = if (defaultValue != null) {
            defaultValue
        } else {
            if (isNullable) {
                "null"
            } else {
                type.defaultValue
            }
        }

        return when {
            dv == "null" -> dv
            type == KType.STRING -> "\"$dv\""
            else -> dv
        }
    }
}

