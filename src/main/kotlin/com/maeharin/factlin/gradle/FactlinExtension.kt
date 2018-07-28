package com.maeharin.factlin.gradle

open class FactlinExtension {
    var dbUrl = ""
    var dbUser = ""
    var dbPassword = ""
    var dbDialect = ""
    var fixtureOutputDir = "src/test/kotlin/com/maeharin/factlin/fixtures"
    var fixturePackageName = "com.maeharin.factlin.fixtures"
    var fixtureTemplatePath: String? = null
    var includeTables: List<String>? = null
    var excludeTables: List<String> = emptyList()
    var cleanOutputDir = false
}