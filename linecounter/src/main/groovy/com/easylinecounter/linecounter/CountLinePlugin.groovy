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
    private static final String COUNT_PATH = 'countpath'
    private static final String TASK_NAME = "countline"

    @Override
    void apply(Project project) {
        project.extensions.create(COUNT_PATH, CountLineExtension)

        String rootDir = project.rootDir.absolutePath
        def task = project.tasks.create(TASK_NAME, CountLineTask)
        task.javaPath = (rootDir + JAVA_PATH + "/com/example/zane/easylinecounter")
        task.resPath = (rootDir + RES_PATH)

        project.android.applicationVariants.all{ variant ->
            task.dependsOn(variant.assemble)
            variant.assemble.finalizedBy(task)
        }
    }
}