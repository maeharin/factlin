package com.maeharin.factlin.util

fun String.toCamelCase(): String {
    return this.split("_").mapIndexed { i, s ->
        if (i == 0) s else s.capitalize()
    }.joinToString("")
}

