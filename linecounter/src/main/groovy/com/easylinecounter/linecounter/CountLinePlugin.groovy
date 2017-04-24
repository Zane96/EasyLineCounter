package com.easylinecounter.linecounter

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 统计行数的插件，引擎
 * Created by zane on 2017/2/7.
 */

class CountLinePlugin implements Plugin<Project> {

    private static final String JAVA_PATH = "/app/src/main/java"
    private static final String RES_PATH = "/app/src/main/res/layout"
    private static final String COUNT_PATH = "countpath"
    private static final String VALUE_TASK_NAME = "getvalue"
    private static final String TASK_NAME = "countline"

    @Override
    void apply(Project project) {
        project.extensions.create(COUNT_PATH, CountLineExtension)

        String rootDir = project.rootDir.absolutePath
        def countTask = project.tasks.create(VALUE_TASK_NAME, CountLineTask)

        def valueTask = project.task(TASK_NAME) << {
            countTask.javaPath =  (rootDir + JAVA_PATH)
            countTask.layoutPath = (rootDir + RES_PATH)

            def rawFilepaths = project[COUNT_PATH].filepaths
            def finalFilepaths = []
            rawFilepaths.each{path ->
                path = rootDir + path
                finalFilepaths << path
            }
            countTask.filepaths = finalFilepaths
        }

        valueTask.finalizedBy(countTask)

        project.getTasks().each {it ->
            if ("assemble".equals(it.getName())) {
                it.finalizedBy(valueTask)
            }
        }
    }
}