package com.maeharin.factlin.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project


open class FactlinPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("factlinGen", FactlinExtension::class.java)
        project.tasks.create("factlinGen", FactlinTask::class.java)
    }
}