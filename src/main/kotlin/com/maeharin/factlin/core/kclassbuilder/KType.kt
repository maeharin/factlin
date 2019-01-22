package com.maeharin.factlin.core.kclassbuilder

enum class KType(val shortName: String, val longName: String) {
    BOOLEAN("Boolean", "Boolean"),
    BYTE("Byte", "Byte"),
    SHORT("Short", "Short"),
    INT("Int", "Int"),
    LONG("Long", "Long"),
    DOUBLE("Double", "Double"),
    FLOAT("Float", "Float"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),
    STRING("String", "String"),
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime")
}
