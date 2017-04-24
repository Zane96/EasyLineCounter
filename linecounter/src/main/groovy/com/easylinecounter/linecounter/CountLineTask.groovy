package com.easylinecounter.linecounter

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 统计行数的task
 * Created by zane on 2017/2/7.
 */

public class CountLineTask extends DefaultTask {

    def javaPath;
    def resPath;
    def javaLines = 0;
    def resLines = 0;

    //task的响应方法
    @TaskAction
    void countLine() {
        if (!isPathValid(javaPath, resPath)) {
            throw new IllegalArgumentException("both of java and res path can't be null")
        }

        def javaDir = new File(javaPath)
        def resDir = new File(resPath)
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
        dir.traverse { file ->
            if (!file.directory) {
                //dir是文件，开始统计行数
                println file.name + " filename"
                def list = file.collect{it}
                if (file.name.endsWith(".java")) {
                    javaLines += list.size()
                } else if (file.name.endsWith(".xml")) {
                    resLines += list.size()
                }
            }
        }
    }
}