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
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class CodeGenerator(
        val extension: FactlinExtension,
        val tables: List<Table>,
        val dialect: Dialect
) {
    private lateinit var dir: File
    private lateinit var template: Template

    fun generate() {
        // output config
        dir = File(extension.fixtureOutputDir)

        if (dir.isFile) throw FactlinException(ErrorMessage.MustBeDir)

        if (dir.exists()) {
            if (extension.cleanOutputDir) {
                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                println("cleanOutputDir...${dir}")
                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                val isSuccess = dir.deleteRecursively()
                if (!isSuccess) throw FactlinException(ErrorMessage.ErrorAtDeleteOutputDir)
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
            val kClass = KClassBuilder(table, dialect).build()
            _generateKlass(extension, kClass)
        }
    }

    private fun _generateKlass(extension: FactlinExtension, KClass: KClass) {

        val writer = BufferedWriter(
                OutputStreamWriter(
                        FileOutputStream("${dir}/${KClass.fileName()}"),
                        "utf-8"
                )
        )
        // debug
        //val writer = StringWriter()

        // generate!
        try {
            val viewModel = mapOf(
                    "packageName" to extension.fixturePackageName,
                    "kClass" to KClass
            )
            template.process(viewModel, writer)
            // debug
            //println(writer.toString())
        } finally {
            writer.close()
        }
    }
}

