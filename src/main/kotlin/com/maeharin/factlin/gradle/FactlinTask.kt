package com.maeharin.factlin.gradle

import com.maeharin.factlin.core.FactlinCore
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.LoggerFactory

open class FactlinTask: DefaultTask() {
    var logger = LoggerFactory.getLogger(javaClass)

    @TaskAction
    fun factlinGenerate() {
        val factlinExtension = project.extensions.getByType(FactlinExtension::class.java)

        println("=============================")
        println("[project info]")
        println("name: ${project.name}")
        println("displayName: ${project.displayName}")
        println("rootDir: ${project.rootDir}")
        println("projectDir: ${project.projectDir}")
        println("=============================")

        FactlinCore(factlinExtension).exec()
    }
}