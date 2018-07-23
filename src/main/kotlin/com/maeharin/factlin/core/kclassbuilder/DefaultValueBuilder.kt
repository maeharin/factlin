package com.maeharin.factlin.core.kclassbuilder

interface DefaultValueBuilder {
    fun build(orig: String, type: KType): String?
}

class PostgresDefaultValueBuilder: DefaultValueBuilder {
    override fun build(orig: String, type: KType): String? {
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

class MariadbDefaultValueBuilder: DefaultValueBuilder {
    override fun build(orig: String, type: KType): String? {
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

