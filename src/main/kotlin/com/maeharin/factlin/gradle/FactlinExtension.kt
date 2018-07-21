package com.maeharin.factlin.gradle

open class FactlinExtension {
    var dbUrl = ""
    var dbUser = ""
    var dbPassword = ""
    var dbDialect = ""
    var fixtureOutputDir = "src/test/kotlin/factlin/fixtures"
    var fixturePackageName = "factlin.fixtures"
    var fixtureTemplatePath: String? = null
}