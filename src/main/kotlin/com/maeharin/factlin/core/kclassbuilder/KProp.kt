package com.maeharin.factlin.core.kclassbuilder

data class KProp(
        val tableName: String,
        val columnName: String,
        val type: KType,
        private val typeName: String,
        private val defaultValue: Any?,
        val isNullable: Boolean,
        val isPrimaryKey: Boolean,
        val comment: String?
) {
    fun name(): String {
        // todo optional to camel case
        //return columnName.toCamelCase()
        return columnName
    }

    /**
     * build default value
     * todo customizable
     */
    fun defaultValue(): Any {
        val dv = if (defaultValue != null) {
            // todo optional to use database defaultValue
            type.defaultValue
        } else {
            if (isNullable) {
                "null"
            } else {
                type.defaultValue
            }
        }

        return when {
            dv == "null" -> dv
            type == KType.STRING -> "\"${dv}\""
            type == KType.BIG_DECIMAL -> "${dv}.toBigDecimal()"
            else -> dv
        }
    }
}

