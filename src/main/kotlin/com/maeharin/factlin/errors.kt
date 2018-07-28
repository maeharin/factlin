package com.maeharin.factlin

class FactlinException(
        errorMessage: ErrorMessage,
        ex: Exception? = null
): RuntimeException(errorMessage.message, ex)

enum class ErrorMessage(val message: String) {
    MustBeDir("must be directory"),
    ErrorAtDeleteOutputDir("error when deleting output dir"),
}
