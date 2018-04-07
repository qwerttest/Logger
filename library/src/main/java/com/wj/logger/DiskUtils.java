package com.wj.logger;

import java.io.File;

/**
 * Created by wangjian on 2018/3/20.
 */

public class DiskUtils {

    /**
     * @param path 目录
     *             @param maxSize 限制的大小
     * 如果文件夹超出规定大小，则进行删除，删除老文件
     * */
    public static void checkSize(String path, long maxSize){
        File logFolder = new File(path);
        if(logFolder.exists()){
            if(logFolder.isDirectory()){
                while (getFolderSize(logFolder) > maxSize){
                    File oldFile = null;
                    for (File file : logFolder.listFiles()){
                        if(null == oldFile){
                            oldFile = file;
                        }
                        if(file.lastModified() <= oldFile.lastModified()){
                            oldFile = file;
                        }
                    }
                    oldFile.delete();//如果是目录，要先删除目录中所有文件，才能删除成功
                    if(oldFile.isDirectory()){
                        break;
                    }
                }

            }
        }
    }

    public static long getFolderSize(File folder){
        long folderSize = 0;
        File[] files = folder.listFiles();
        for (File file : files){
            if(file.isDirectory()){
                folderSize += getFolderSize(file);
                continue;
            } else{
                folderSize += file.length();
            }
        }
        return folderSize;
    }
}
