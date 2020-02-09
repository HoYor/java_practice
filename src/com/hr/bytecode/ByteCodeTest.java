package com.hr.bytecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;

public class ByteCodeTest {
    public static void main(String[] args) {
        Map<String, byte[]> compile = ByteCodeCompiler.compile("package com.hr.bytecode;\n" +
                "\n" +
                "public class ByteCodeSourceCode {\n" +
                "    public static String returnString(){\n" +
                "        return \"hello\";\n" +
                "    }\n" +
                "}\n");
        assert compile != null;
        ByteCodeClassLoader.getLoader().loadClassFromByteCodes(compile);
        Set<String> strings = compile.keySet();
        try {
            for (String className : strings) {
                Class cls = ByteCodeClassLoader.getLoader().findClass(className);
                Object o = cls.newInstance();
                Method returnString = cls.getMethod("returnString");
                Object invoke = returnString.invoke(o);
                System.out.println("动态代码执行结果："+invoke.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
