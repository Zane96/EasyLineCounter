package com.easylinecounter.linecounter

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 统计行数的task
 * Created by zane on 2017/2/7.
 */

public class CountLineTask extends DefaultTask {

    def String javaPath;
    def String resPath;
    def int lines = 0;

    //task的响应方法
    @TaskAction
    void countLine(){
//        if (!isPathValid(javaPath, resPath)) {
//            println "both of javaPath and resPath can't be null"
//            return
//        }
        if (javaPath != null && resPath != null) {
            def javaDir = new File(javaPath)
            def resDir = new File(resPath)
            travelAndCount(javaDir)
            travelAndCount(resDir)
            println "代码总行数是: " + lines
        }
        return;
    }

    private def isPathValid() {
        if (javaPath != null && resPath != null) {
            return true;
        }
        return false;
    }

    /**
     * 遍历文件树并且统计行数
     * @return
     */
    def travelAndCount(File dir) {
        dir.traverse { file ->
            if (!file.directory) {
                //dir是文件，开始统计行数
                if (file.name.endsWith(".java") || file.name.endsWith(".xml")) {
                    println file.name + " filename"
                    def list = file.collect{it}
                    lines += list.size()
                }
            }
        }
    }
}