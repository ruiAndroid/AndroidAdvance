package com.rui.baselibrary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Time: 2020/5/9
 * Author: jianrui
 * Description: file 工具类
 */
public class FileUtils {

    /**
     * 复制文件
     */
    public static void copyFile(File srcFile,File destFile){
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            if(!destFile.exists()){
                    destFile.createNewFile();
            }

            inChannel=new FileInputStream(srcFile).getChannel();
            outChannel=new FileInputStream(destFile).getChannel();

            inChannel.transferTo(0,inChannel.size(),outChannel);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inChannel!=null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel!=null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
