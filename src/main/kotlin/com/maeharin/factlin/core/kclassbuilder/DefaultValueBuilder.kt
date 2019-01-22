package com.maeharin.factlin.core.kclassbuilder

// todo customizeable
class DefaultValueBuilder(
    private val tableName: String,
    private val columnName: String,
    private val dbDefaultValue: String?, // we don't use this currently
    private val type: KType,
    private val isNullable: Boolean
) {
    fun build(): String {
        return if (isNullable) {
            "null"
        } else {
            _defaultValue(type)
        }
    }

    fun _defaultValue(type: KType): String {
        return when (type) {
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
