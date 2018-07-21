package com.maeharin.factlin.core.kclassbuilder

enum class KType(val shortName: String, val longName: String, val defaultValue: String) {
    BOOLEAN("Boolean", "Boolean", "false"),
    BYTE("Byte", "Byte", "0"),
    SHORT("Short", "Short", "0"),
    INT("Int", "Int", "0"),
    LONG("Long", "Long", "0"),
    DOUBLE("Double", "Double", "0.0"),
    FLOAT("Float", "Float", "0F"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal", "0"),
    STRING("String", "String", ""),
    LOCAL_DATE("LocalDate", "java.time.LocalDate", "LocalDate.now()"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime", "LocalTime.now()"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime", "LocalDateTime.now()")
}


