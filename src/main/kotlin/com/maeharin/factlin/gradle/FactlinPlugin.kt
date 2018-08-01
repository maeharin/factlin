package com.maeharin.factlin.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project


open class FactlinPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("factlin", FactlinExtension::class.java)
        project.tasks.create("factlin", FactlinTask::class.java)
    }
}