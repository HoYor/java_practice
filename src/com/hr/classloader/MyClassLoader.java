package com.hr.classloader;

import com.sun.istack.internal.NotNull;

import java.io.*;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = findClassData(name);
        return this.defineClass(name, data, 0 , data.length);
    }

    @NotNull
    private byte[] findClassData(String name) {
        String classPath = name.replace(".", "/");
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(classPath+".class"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int d;
            while ((d = fileInputStream.read()) != -1){
                byteArrayOutputStream.write(d);
            }
            fileInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
