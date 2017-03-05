package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by xinchang on 2017/3/5.
 */

public class FileUtils {
    public static void print(String text)
    {
        File file=new File("/Users/xinchang/Desktop/file.txt");
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter=new FileWriter(file.getAbsoluteFile(),true);
            fileWriter.write(text+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
