package com.softech.translationapp

import java.io.File


object Method {
    fun load_Directory_Files(directory: File) {
        val fileList = directory.listFiles()
        if (fileList != null && fileList.size > 0) {
            for (i in fileList.indices) {
                if (fileList[i].isDirectory) {
                    load_Directory_Files(fileList[i])
                } else {
                    val name = fileList[i].name.toLowerCase()
                    for (extension in Constant.videoExtensions) {
                        //check the type of file
                        if (name.endsWith(extension)) {
                            Constant.allMediaList.add(fileList[i])
                            //when we found file
                            break
                        }
                    }
                }
            }
        }
    }
}