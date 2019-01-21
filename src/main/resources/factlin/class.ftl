<#-- below comment is for intellij codegenerator completion -->
<#-- @ftlvariable name="kClass" type="com.maeharin.factlin.core.kclassbuilder.KClass" -->
package ${packageName}

<#list kClass.imports() as import>
${import}
</#list>

data class ${kClass.name()} (
<#list kClass.props as prop>
    val ${prop.name}: ${prop.type.shortName}<#if prop.isNullable()>?</#if> = ${prop.defaultValue()}<#if prop?has_next>,</#if> <#if prop.comment??>// ${prop.comment}</#if>
</#list>
)

fun DbSetupBuilder.insert${kClass.name()}(f: ${kClass.name()}) {
    insertInto("${kClass.tableName}") {
        mappedValues(
            <#list kClass.props as prop>
                "${prop.columnName}" to f.${prop.name}<#if prop?has_next>,</#if>
            </#list>
        )
    }
}