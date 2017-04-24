package com.easylinecounter.linecounter

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 统计行数的task
 * Created by zane on 2017/2/7.
 */

public class CountLineTask extends DefaultTask {

    def javaPath
    def layoutPath
    //list
    def filepaths
    def javaLines = 0
    def resLines = 0

    //task的响应方法
    @TaskAction
    void countLine() {
        if (!isPathValid(javaPath, layoutPath)) {
            throw new IllegalArgumentException("both of java and res path can't be null")
        }

        if (filepaths != null) {
            filepaths.each{path ->
                travelAndCount(new File(path))
            }
        }

        def javaDir = new File(javaPath)
        def resDir = new File(layoutPath)
        travelAndCount(javaDir)
        travelAndCount(resDir)
        printSummary()
    }

    /**
     * 判断路径是否ok
     * @param javaPath
     * @param resPath
     * @return
     */
    def isPathValid(String javaPath, String resPath) {
        if (javaPath != null && resPath != null) {
            return true;
        }
        return false;
    }

    /**
     * 打印结果
     */
    def printSummary(){
        println 'Java文件的代码行数： ' + javaLines
        println 'Res文件的代码行数： ' + resLines
        println '文件的代码总行数： ' + (javaLines + resLines)
    }

    /**
     * 遍历文件树并且统计行数
     * @return
     */
    def travelAndCount(File dir) {
        if (dir.directory) {
            dir.traverse { file ->
                if (!file.directory) {
                    addNum(file)
                }
            }
        } else {
            addNum(dir)
        }
    }

    /**
     * 统计行数
     * @param file
     * @param num
     * @return
     */
    def addNum(File file) {
        def list = file.collect { it }
        def num = list.size()
        if (file.name.endsWith(".java")) {
            javaLines += num
        } else if (file.name.endsWith(".xml")) {
            resLines += num
        }
        println file.name + " " + num + "行"
    }
}