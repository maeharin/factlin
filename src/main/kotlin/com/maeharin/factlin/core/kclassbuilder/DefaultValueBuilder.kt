package com.maeharin.factlin.core.kclassbuilder

// todo customizeable
class DefaultValueBuilder(
        val tableName: String,
        val columnName: String,
        val dbDefaultValue: String?, // we don't use this currently
        val type: KType,
        val isNullable: Boolean
) {
    fun build(): String {
        return if (isNullable) {
            "null"
        } else {
            _defaultValue(type)
        }
    }

    fun _defaultValue(type: KType): String {
        return when(type) {
            KType.BOOLEAN -> "false"
            KType.BYTE -> "0"
            KType.SHORT -> "0"
            KType.INT -> "0"
            KType.LONG -> "0"
            KType.DOUBLE -> "0.0"
            KType.FLOAT -> "0F"
            KType.BIG_DECIMAL -> "0.toBigDecimal()"
            KType.STRING -> "\"\""
            KType.LOCAL_DATE -> "LocalDate.now()"
            KType.LOCAL_TIME -> "LocalTime.now()"
            KType.LOCAL_DATE_TIME -> "LocalDateTime.now()"
        }
    }
}


//class PostgresDefaultValueBuilder: DefaultValueBuilder {
//    override fun build(orig: String, type: KType): String? {
//        return when(type) {
//            // todo text
//            // 'engineer'::character varying
//            KType.STRING -> orig
//            else -> {
//                when {
//                    // todo
//                    // 'G'::mpaa_rating
//                    orig == "now()" -> getDefaultTimeValueFromKType(type)
//                    orig == "('now'::text)::date" -> getDefaultTimeValueFromKType(type)
//                    orig.startsWith("nextval(") -> "0"
//                    else -> orig
//                }
//            }
//        }
//    }
//}
//
//class MariadbDefaultValueBuilder: DefaultValueBuilder {
//    override fun build(orig: String, type: KType): String? {
//        // todo text
//        // 'engineer'
//        return when {
//            orig == "NULL" -> null
//            orig == "current_timestamp()" -> getDefaultTimeValueFromKType(type)
//            else -> orig
//        }
//    }
//}
//
//fun getDefaultTimeValueFromKType(type: KType): String {
//    return when(type) {
//        KType.LOCAL_DATE -> "LocalDate.now()"
//        KType.LOCAL_TIME -> "LocalTime.now()"
//        KType.LOCAL_DATE_TIME -> "LocalDateTime.now()"
//        else -> throw Exception("invalid KType [${type}] for default timestamp value")
//    }
//}

