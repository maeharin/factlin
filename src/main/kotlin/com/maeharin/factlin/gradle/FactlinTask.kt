package com.maeharin.factlin.gradle

import com.maeharin.factlin.core.FactlinCore
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.LoggerFactory

open class FactlinTask : DefaultTask() {
    var logger = LoggerFactory.getLogger(javaClass)

    @TaskAction
    fun factlinGenerate() {
        val factlinExtension = project.extensions.getByType(FactlinExtension::class.java)

        logger.info("=============================")
        logger.info("[project info]")
        logger.info("name: ${project.name}")
        logger.info("displayName: ${project.displayName}")
        logger.info("rootDir: ${project.rootDir}")
        logger.info("projectDir: ${project.projectDir}")
        logger.info("factlinExtension: $factlinExtension")
        logger.info("=============================")

        FactlinCore(factlinExtension).exec()
    }
}