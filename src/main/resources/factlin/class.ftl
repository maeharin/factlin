<#-- below comment is for intellij code completion -->
<#-- @ftlvariable name="kClass" type="com.maeharin.factlin.core.kclassbuilder.KClass" -->
<#list kClass.imports() as import>
${import}
</#list>

data class ${kClass.name()} (
<#list kClass.props as prop>
    val ${prop.name()}: ${prop.type.shortName}<#if prop.isNullable()>?</#if> = ${prop.defaultValue()}<#if prop?has_next>,</#if> <#if prop.comment??>// ${prop.comment}</#if>
</#list>
)
