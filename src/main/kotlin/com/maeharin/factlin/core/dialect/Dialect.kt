package com.maeharin.factlin.core.dialect

import java.sql.Types

interface Dialect {
    fun convertDefaultValue(orig: String, type: Int): String?
}

class PostgresDialect: Dialect {
    override fun convertDefaultValue(orig: String, type: Int): String? {
        return when {
            // todo
            // ('now'::text)::date
            // 'G'::mpaa_rating
            orig == "now()" -> getDefaultTimeValueFromSqlType(type)
            orig.startsWith("nextval(") -> "0"
            else -> orig
        }
    }
}

class MariadbDialect: Dialect {
    override fun convertDefaultValue(orig: String, type: Int): String? {
        return when {
            orig == "NULL" -> null
            orig == "current_timestamp()" -> getDefaultTimeValueFromSqlType(type)
            else -> orig
        }
    }
}

fun getDefaultTimeValueFromSqlType(type: Int): String {
    return when(type) {
        Types.DATE -> "LocalDate.now()"
        Types.TIME -> "LocalTime.now()"
        Types.TIMESTAMP -> "LocalDate.now()"
        Types.TIME_WITH_TIMEZONE -> "LocalTime.now()" // todo zoned?
        Types.TIMESTAMP_WITH_TIMEZONE -> "LocalTime.now()" // todo zoned?
        else -> throw Exception("invalid type for default value: now()")
    }
}

