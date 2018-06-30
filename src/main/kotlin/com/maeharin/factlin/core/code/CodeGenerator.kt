package com.maeharin.factlin.core.code

import com.maeharin.factlin.ErrorMessage
import com.maeharin.factlin.FactlinException
import com.maeharin.factlin.core.schema.Table
import com.maeharin.factlin.util.toCamelCase
import freemarker.template.Configuration
import java.io.*

class CodeGenerator(
        val table: Table
) {
    fun generate() {
        val klass = _buildKlass()

        println("generating klass ${klass.fileName()}....")

        val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).also {
            it.setClassForTemplateLoading(javaClass, "/factlin")
        }
        val template = config.getTemplate("class.ftl")

        // output writer
        val dirPath = "src/test/kotlin/factlin/fixtures"
        val dir = File(dirPath)
        if (dir.isFile) throw FactlinException(ErrorMessage.MustBeDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val writer = BufferedWriter(
                OutputStreamWriter(
                        FileOutputStream("${dir}/${klass.fileName()}"),
                        "utf-8"
                )
        )

        // generate!
        try {
            //val writer = OutputStreamWriter(System.out)
            val viewModel = mapOf("klass" to klass)
            template.process(viewModel, writer)
        } finally {
            writer.close()
        }
    }

    private fun _buildKlass(): Klass {
        return Klass(
                name = table.name,
                comment = table.comment ?: "",
                schema = table.schema,
                catalog = table.catalog,
                props = table.columns.map { columnMeta ->
                    Prop(
                            name = columnMeta.name,
                            type = columnMeta.type,
                            typeName = columnMeta.typeName,
                            defaultValue = columnMeta.defaultValue,
                            isNullable = columnMeta.isNullable,
                            isPrimaryKey = columnMeta.isPrimaryKey,
                            comment = columnMeta.comment
                    )
                }
        )
    }
}

data class Klass(
        private val name: String,
        val comment: String,
        val schema: String,
        private val catalog: String?,
        val props: List<Prop>
) {
    fun name(): String {
        return name.capitalize()
    }

    fun fileName(): String {
        return "${name()}.kt"
    }
}

data class Prop(
        private val name: String,
        private val type: Int,
        private val typeName: String,
        private val defaultValue: Any?,
        val isNullable: Boolean,
        val isPrimaryKey: Boolean,
        val comment: String?
) {
    fun name(): String {
        return name.toCamelCase()
    }

    fun type(): String {
        println("------------")
        println(typeName)
        println("------------")

        val typeStr = when (typeName) {
            else -> "[TODO: UNKOWN]"
        }

        val nullPostfix = if(isNullable) "?" else ""

        return "$typeStr$nullPostfix"
    }

    fun defaultValue(): Any {
        if (defaultValue != null) {
            return defaultValue
        }

        if (isNullable) {
            return "null"
        }

        return "[TODO: no default value. must implement]"
    }
}

