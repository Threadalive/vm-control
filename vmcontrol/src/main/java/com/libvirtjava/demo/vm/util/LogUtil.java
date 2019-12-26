package com.libvirtjava.demo.vm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author pan
 * @create 2019-12-26-16:59
 */
public class LogUtil {
    /**
     * 读取日志文件以string形式返回
     * @param fileName 日志文件绝对路径
     * @return
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        StringBuilder stringBuilder=new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
                stringBuilder.append(tempString);
                stringBuilder.append("\r\n");
                line++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }
}
