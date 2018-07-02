package com.maeharin.factlin.core.code

import com.maeharin.factlin.ErrorMessage
import com.maeharin.factlin.FactlinException
import com.maeharin.factlin.core.schema.Table
import com.maeharin.factlin.util.toCamelCase
import freemarker.template.Configuration
import java.io.*
import java.sql.Types

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
        // debug
        //val writer = StringWriter()

        // generate!
        try {
            val viewModel = mapOf("klass" to klass)
            template.process(viewModel, writer)
            // debug
            //println(writer.toString())
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

    /**
     * build prop's Kotlin type
     */
    fun type(): String {
        // todo customizable
        return when (type) {
            // java.sql.Types => Kotlin type
            // see: https://docs.oracle.com/cd/E16338_01/java.112/b56281/datacc.htm#BHCJBJCC
            Types.BIT -> "Boolean"
            Types.TINYINT -> "Byte"
            Types.SMALLINT -> "Short"
            Types.INTEGER -> "Int"
            Types.BIGINT -> "Long"
            Types.FLOAT -> "Double"
            Types.REAL -> "Float"
            Types.DOUBLE -> "Double"
            Types.NUMERIC -> "java.math.BigDecimal"
            Types.DECIMAL -> "java.math.BigDecimal"
            Types.CHAR -> "String"
            Types.VARCHAR -> "String"
            Types.LONGVARCHAR -> "String"
            Types.DATE -> "java.time.LocalDate"
            Types.TIME -> "java.time.LocalTime"
            Types.TIMESTAMP -> "java.time.LocalDateTime"
            Types.BINARY -> "Byte"
            Types.VARBINARY -> "Byte"
            Types.LONGVARBINARY -> "Byte"
            //Types.NULL -> ""
            //Types.OTHER -> ""
            //Types.JAVA_OBJECT -> ""
            //Types.DISTINCT -> ""
            Types.STRUCT -> "java.sql.Struct"
            Types.ARRAY -> "java.sql.Array"
            Types.BLOB -> "java.sql.Blob"
            Types.CLOB -> "java.sql.Clob"
            Types.REF -> "java.sql.Ref"
            //Types.DATALINK -> ""
            Types.BOOLEAN -> "Boolean"
            Types.ROWID -> "java.sql.RowId"
            Types.NCHAR -> "String"
            Types.NVARCHAR -> "String"
            Types.LONGNVARCHAR -> "String"
            //Types.NCLOB -> ""
            //Types.SQLXML -> ""
            //Types.REF_CURSOR -> ""
            Types.TIME_WITH_TIMEZONE -> "java.time.LocalTime"
            Types.TIMESTAMP_WITH_TIMEZONE -> "java.time.LocalDateTime"
            else -> "[UNKOWN]"
        }
    }

    /**
     * build default value
     * todo customizable
     */
    fun defaultValue(): Any {
        if (defaultValue != null) {
            // todo: other pattern
            return when(defaultValue) {
                is String -> when {
                    defaultValue.startsWith("nextval(") -> "0"
                    defaultValue == "now()" -> when(type()) {
                        "java.time.LocalDate" -> "LocalDate.now()"
                        "java.time.LocalTime" -> "LocalTime.now()"
                        "java.time.LocalDateTime" -> "LocalDateTime.now()"
                        else -> "LocalDateTime.now()"
                    }
                    else -> defaultValue
                }
                else -> defaultValue
            }
        }

        if (isNullable) {
            return "null"
        }

        return when(type()) {
            "String" -> "\"\""
            "Int" -> "0"
            "Boolean" -> "false"
            "java.time.LocalDate" -> "LocalDate.now()"
            "java.time.LocalTime" -> "LocalTime.now()"
            "java.time.LocalDateTime" -> "LocalDateTime.now()"
            else -> ""
        }
    }
}

