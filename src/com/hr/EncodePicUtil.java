package com.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EncodePicUtil {

    public static void encodePic(File file){
        if(!file.exists()){
            return;
        }else{
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[Integer.MAX_VALUE];
                inputStream.read(bytes,0,16);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
