package com.maeharin.factlin.core.codegenerator

import com.maeharin.factlin.ErrorMessage
import com.maeharin.factlin.FactlinException
import com.maeharin.factlin.core.Dialect
import com.maeharin.factlin.core.kclassbuilder.KClass
import com.maeharin.factlin.core.kclassbuilder.KClassBuilder
import com.maeharin.factlin.core.schemaretriever.Table
import com.maeharin.factlin.gradle.FactlinExtension
import freemarker.template.Configuration
import freemarker.template.Template
import org.slf4j.LoggerFactory
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class CodeGenerator(
    private val extension: FactlinExtension,
    private val tables: List<Table>,
    private val dialect: Dialect
) {
    private lateinit var dir: File
    private lateinit var template: Template
    private val logger = LoggerFactory.getLogger(javaClass)

    fun generate() {
        // output config
        dir = File(extension.fixtureOutputDir)

        if (dir.isFile) throw FactlinException(ErrorMessage.MustBeDir)

        if (dir.exists()) {
            if (extension.cleanOutputDir) {
                logger.warn("cleanOutputDir...$dir")
                if (dir.deleteRecursively()) {
                    dir.mkdir()
                } else {
                    throw FactlinException(ErrorMessage.ErrorAtDeleteOutputDir)
                }
            }
        } else {
            dir.mkdirs()
        }

        // template config
        template = if (extension.fixtureTemplatePath != null) {
            val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
            config.getTemplate(extension.fixtureTemplatePath)
        } else {
            val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).also {
                it.setClassForTemplateLoading(javaClass, "/factlin")
            }
            config.getTemplate("class.ftl")
        }

        tables.forEach { table ->
            val kClass = KClassBuilder(
                    table = table,
                    dialect = dialect,
                    customDefaultValues = extension.customDefaultValues,
                    customTypeMapper = extension.customTypeMapper,
                    useCamelCase = extension.useCamelCase
            ).build()

            _generateCode(kClass)
        }
    }

    private fun _generateCode(KClass: KClass) {

        val writer = BufferedWriter(
                OutputStreamWriter(
                        FileOutputStream("$dir/${KClass.fileName()}"),
                        "utf-8"
                )
        )

        // generate!
        try {
            val viewModel = mapOf(
                    "packageName" to extension.fixturePackageName,
                    "kClass" to KClass
            )
            template.process(viewModel, writer)
        } finally {
            writer.close()
        }
    }
}
