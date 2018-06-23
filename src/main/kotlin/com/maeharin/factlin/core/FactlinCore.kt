package com.maeharin.factlin.core

data class FactlinCore(
        val configPath: String
) {
    fun exec() {
        println("------------------------------------")
        println("[factlin generate] configPath: ${configPath}")
        println("------------------------------------")
    }
}