package com.maeharin.factlin.core.codegenerator

import com.maeharin.factlin.ErrorMessage
import com.maeharin.factlin.FactlinException
import com.maeharin.factlin.core.kclassbuilder.DialectConverter
import com.maeharin.factlin.core.kclassbuilder.KClass
import com.maeharin.factlin.gradle.FactlinExtension
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class CodeGenerator(
        val extension: FactlinExtension,
        val KClass: KClass
) {
    lateinit var dialectConverter: DialectConverter

    fun generate() {
        // template config
        val template: Template = if (extension.fixtureTemplatePath != null) {
            val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
            config.getTemplate(extension.fixtureTemplatePath)
        } else {
            val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).also {
                it.setClassForTemplateLoading(javaClass, "/factlin")
            }
            config.getTemplate("class.ftl")
        }

        // output config
        val dir = File(extension.fixtureOutputDir)
        if (dir.isFile) throw FactlinException(ErrorMessage.MustBeDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
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

