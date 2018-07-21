package com.maeharin.factlin.core.codegenerator

import com.maeharin.factlin.ErrorMessage
import com.maeharin.factlin.FactlinException
import com.maeharin.factlin.core.kclassbuilder.DialectConverter
import com.maeharin.factlin.core.kclassbuilder.KClass
import freemarker.template.Configuration
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class CodeGenerator(
        val KClass: KClass
) {
    lateinit var dialectConverter: DialectConverter

    fun generate() {
        // template config
        // todo customize template path
        val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).also {
            it.setClassForTemplateLoading(javaClass, "/factlin")
        }
        val template = config.getTemplate("class.ftl")

        // output config
        // todo customize output dir
        val dirPath = "src/test/kotlin/factlin/fixtures"
        val packageName = "factlin.fixtures"
        val dir = File(dirPath)
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
                    "packageName" to packageName,
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

