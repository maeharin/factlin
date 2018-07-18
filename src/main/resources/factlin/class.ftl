<#-- below comment is for intellij code completion -->
<#-- @ftlvariable name="klass" type="com.maeharin.factlin.core.code.Klass" -->
<#list klass.imports() as import>
${import}
</#list>

data class ${klass.name()} (
<#list klass.props as prop>
    val ${prop.name()}: ${prop.type().shortName}<#if prop.isNullable()>?</#if> = ${prop.defaultValue()}<#if prop?has_next>,</#if> <#if prop.comment??>// ${prop.comment}</#if>
</#list>
)
