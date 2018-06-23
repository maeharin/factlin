package com.maeharin.factlin.gradle

import com.maeharin.factlin.core.FactlinCore
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class FactlinTask: DefaultTask() {
    @TaskAction
    fun factlinGenerate() {
        val factlinExtension = getProject().extensions.getByType(FactlinExtension::class.java)
        FactlinCore(configPath = factlinExtension.configPath).exec()
    }
}