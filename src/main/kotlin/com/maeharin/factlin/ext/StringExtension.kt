package com.maeharin.factlin.ext

fun String.toCamelCase(isCapitalizeFirstChar: Boolean = true): String {
    return this.split("_").mapIndexed { i, s ->
        if (isCapitalizeFirstChar) {
            s.capitalize()
        } else {
            if (i == 0) s else s.capitalize()
        }
    }.joinToString("")
}

