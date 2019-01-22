package com.maeharin.factlin.core.kclassbuilder

import com.maeharin.factlin.ext.toCamelCase

data class KClass(
    val tableName: String,
    private val name: String,
    val comment: String,
    val schema: String?,
    private val catalog: String?,
    val props: List<KProp>
) {
    fun name(): String {
        return "${name.toCamelCase()}Fixture"
    }

    fun fileName(): String {
        return "${name()}.kt"
    }

    fun imports(): Set<String> {
        return props.map { prop ->
            if (prop.type.longName.contains(".")) {
                "import ${prop.type.longName}"
            } else {
                null
            }
        }.filterNotNull()
                .toSet()
                .plus("import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder")
                .plus("import com.ninja_squad.dbsetup_kotlin.mappedValues")
    }
}
