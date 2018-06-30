<#-- below comment is for intellij code completion -->
<#-- @ftlvariable name="klass" type="com.maeharin.factlin.core.code.Klass" -->

data class ${klass.name()} (
<#list klass.props as prop>
    val ${prop.name()}: ${prop.type()} = ${prop.defaultValue()}<#if prop?has_next>,</#if> <#if prop.comment??>// ${prop.comment}</#if>
</#list>
)
