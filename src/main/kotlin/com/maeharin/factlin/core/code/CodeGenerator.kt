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
        println("types: ${klass.props.map { it.type() }}")
        println("imports: ${klass.imports()}")

        // template config
        // todo customize template path
        val config = Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).also {
            it.setClassForTemplateLoading(javaClass, "/factlin")
        }
        val template = config.getTemplate("class.ftl")

        // output config
        // todo customize output dir
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
                            tableName = table.name,
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
        val schema: String?,
        private val catalog: String?,
        val props: List<Prop>
) {
    fun name(): String {
        return name.capitalize()
    }

    fun fileName(): String {
        return "${name()}.kt"
    }

    fun imports(): Set<String> {
        return props.map { prop ->
            val type = prop.type()

            if (type.longName.contains(".")) {
                "import ${type.longName}"
            } else {
                null
            }
        }.filterNotNull().toSet()
    }
}

enum class KType(val shortName: String, val longName: String, val defaultValue: String) {
    BOOLEAN("Boolean", "Boolean", "false"),
    BYTE("Byte", "Byte", "0"),
    SHORT("Short", "Short", "0"),
    INT("Int", "Int", "0"),
    LONG("Long", "Long", "0"),
    DOUBLE("Double", "Double", "0.0"),
    FLOAT("Float", "Float", "0F"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal", "0.toBigDecimal()"),
    STRING("String", "String", "\"\""),
    LOCAL_DATE("LocalDate", "java.time.LocalDate", "LocalDate.now()"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime", "LocalTime.now()"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime", "LocalDateTime.now()"),
    UNKNOWN("[UNKNOWN]", "[UNKNOWN]", "[UNKNOWN]"),
}


data class Prop(
        val tableName: String,
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
    fun type(): KType {
        // todo customizable
        return when (type) {
            // java.sql.Types => Kotlin type
            // see: https://docs.oracle.com/cd/E16338_01/java.112/b56281/datacc.htm#BHCJBJCC
            Types.BIT -> KType.BOOLEAN
            Types.TINYINT -> KType.BYTE
            Types.SMALLINT -> KType.SHORT
            Types.INTEGER -> KType.INT
            Types.BIGINT -> KType.LONG
            Types.FLOAT -> KType.DOUBLE
            Types.REAL -> KType.FLOAT
            Types.DOUBLE -> KType.DOUBLE
            Types.NUMERIC -> KType.BIG_DECIMAL
            Types.DECIMAL -> KType.BIG_DECIMAL
            Types.CHAR -> KType.STRING
            Types.VARCHAR -> KType.STRING
            Types.LONGVARCHAR -> KType.STRING
            Types.DATE -> KType.LOCAL_DATE
            Types.TIME -> KType.LOCAL_TIME
            Types.TIMESTAMP -> KType.LOCAL_DATE_TIME
            Types.BINARY -> KType.BYTE
            Types.VARBINARY -> KType.BYTE
            Types.LONGVARBINARY -> KType.BYTE
            Types.BOOLEAN -> KType.BOOLEAN
            Types.NCHAR -> KType.STRING
            Types.NVARCHAR -> KType.STRING
            Types.LONGNVARCHAR -> KType.STRING
            Types.TIME_WITH_TIMEZONE -> KType.LOCAL_TIME
            Types.TIMESTAMP_WITH_TIMEZONE -> KType.LOCAL_DATE_TIME
            else -> {
                //Types.NULL
                //Types.OTHER
                //Types.JAVA_OBJECT
                //Types.DISTINCT
                //Types.STRUCT
                //Types.ARRAY
                //Types.BLOB
                //Types.CLOB
                //Types.REF
                //Types.DATALINK
                //Types.ROWID
                //Types.NCLOB
                //Types.SQLXML
                //Types.REF_CURSOR
                println("---------------------")
                println("unkown. [${tableName}.${name}] type: ${type}")
                println("---------------------")
                KType.UNKNOWN
            }
        }
    }

    /**
     * build default value
     * todo customizable
     */
    fun defaultValue(): Any {
        if (defaultValue != null) {
            // todo: other pattern
            // ('now'::text)::date
            // 'G'::mpaa_rating
            return when(defaultValue) {
                is String -> when {
                    defaultValue.startsWith("nextval(") -> "0"
                    defaultValue == "now()" -> when(type()) {
                        KType.LOCAL_DATE -> "LocalDate.now()"
                        KType.LOCAL_TIME -> "LocalTime.now()"
                        KType.LOCAL_DATE_TIME -> "LocalDateTime.now()"
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

        return type().defaultValue
    }
}

