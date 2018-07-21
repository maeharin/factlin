package com.maeharin.factlin.core.kclassbuilder

interface DialectConverter {
    fun convertDefaultValue(orig: String, type: KType): String?
}

class PostgresDialectConverter: DialectConverter {
    override fun convertDefaultValue(orig: String, type: KType): String? {
        return when(type) {
            // todo text
            // 'engineer'::character varying
            KType.STRING -> orig
            else -> {
                when {
                    // todo
                    // 'G'::mpaa_rating
                    orig == "now()" -> getDefaultTimeValueFromKType(type)
                    orig == "('now'::text)::date" -> getDefaultTimeValueFromKType(type)
                    orig.startsWith("nextval(") -> "0"
                    else -> orig
                }
            }
        }
    }
}

class MariadbDialectConverter: DialectConverter {
    override fun convertDefaultValue(orig: String, type: KType): String? {
        // todo text
        // 'engineer'
        return when {
            orig == "NULL" -> null
            orig == "current_timestamp()" -> getDefaultTimeValueFromKType(type)
            else -> orig
        }
    }
}

fun getDefaultTimeValueFromKType(type: KType): String {
    return when(type) {
        KType.LOCAL_DATE -> "LocalDate.now()"
        KType.LOCAL_TIME -> "LocalTime.now()"
        KType.LOCAL_DATE_TIME -> "LocalDateTime.now()"
        else -> throw Exception("invalid KType [${type}] for default timestamp value")
    }
}

