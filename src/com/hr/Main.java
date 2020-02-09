package com.hr;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main extends Base implements Control{

//    反射
    private static void reflect() throws Exception{
        Student student = new Student();
        Class stu = student.getClass();

        Method[] methods = stu.getMethods();
        for (Method method:methods) {
            System.out.println("方法名："+method.getName());
            System.out.println("参数类型：");
            Class[] paramTypes = method.getParameterTypes();
            for (Class paramType:paramTypes) {
                System.out.println(paramType.getName()+",");
            }
            Class returnType = method.getReturnType();
            System.out.println("\n返回值类型："+returnType.getName());
        }

        Method setNameMethod = stu.getDeclaredMethod("setName", String.class);
        setNameMethod.setAccessible(true);
        setNameMethod.invoke(student,"调用方法修改的名字");
        System.out.println(student.getName());

        System.out.println(student.getName());
        Field nameField = stu.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student,"修改后的名字");
        System.out.println(student.getName());

        System.out.println(student.getGrade());
        Field gradeField = stu.getDeclaredField("grade");
        gradeField.setAccessible(true);
        gradeField.set(student,"修改后的年级");
        System.out.println(student.getGrade());

        System.out.println(Student.getClazz());
        Field clazzField = stu.getDeclaredField("clazz");
        clazzField.setAccessible(true);
        clazzField.set(student,"修改后的班级");
        System.out.println(Student.getClazz());

        System.out.println(Student.getSchool());
        Field schoolField = stu.getDeclaredField("school");
        schoolField.setAccessible(true);
        Field modifies = schoolField.getClass().getDeclaredField("modifiers");
        modifies.setAccessible(true);
        modifies.setInt(schoolField,schoolField.getModifiers() & ~Modifier.FINAL);
        schoolField.set(student,"修改后的学校");
        modifies.setInt(schoolField,schoolField.getModifiers() & ~Modifier.FINAL);
        System.out.println(Student.getSchool());

    }

//    动态代理
    private static void proxy(){
        RealObject realObject = new RealObject();
        realObject.up();
        realObject.down();
        Control proxy = (Control) Proxy.newProxyInstance(Control.class.getClassLoader(),
                new Class[]{Control.class},
                new DynamicProxyHandler(realObject));
        proxy.up();
        proxy.down();
    }

//    引用和对象
    static int a = 1;
    static char b = 'b';
    final static char finalB = b;
    static Integer packA = a;
    final static Integer finalA = packA;
    static Student student = new Student();
    final static Student finalStudent = student;
    private static void baseType(){
//        基本数据类型及其包装类都是直接存值在堆栈中
        b = 'c';
        System.out.println(finalB);
        a = 2;
        packA = 2;
        System.out.println(finalA);
//        对象引用存在堆栈中，值存在堆中，final的引用不可修改，但引用地址的值可以
        student.setName("修改值1");//修改地址值
        student = new Student("修改值2");//修改引用
        System.out.println(finalStudent.getName());
    }

    // 正则
    public static void re(){
        String text = "invite:0123456789abcdefgABCDEFG";
        Pattern pattern = Pattern.compile("invite:([a-zA-Z0-9]{24})");
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }else{
            System.out.println("不匹配");
        }
    }

    // 拼接Map
    public static void concatMap(){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("aa", 12);
        map1.put("bb", 32);
        Map<String, Object> map2 = new HashMap<>();
        map1.put("aa", 34);
        map1.put("dd", 45);
        map1.putAll(map2);
        for (String key : map1.keySet()) {
            print(key+":"+map1.get(key).toString()+"\n");
        }
    }

    public static void main(String[] args) {
        try {
//            concatMap();
//            reflect();
//            proxy();
//            baseType();
//            re();
//            arrayToList();
//            String s = "shfjs/dfhsjk/fhdjs.jpg";
//            System.out.println(s.substring(s.lastIndexOf("/")));
//            System.out.println(s.substring(0,s.lastIndexOf("/")));
//            Main main = new Main();
//            main.up();
//            main.left();
//            ExpandObject expandObject = (ExpandObject) new RealObject();
//            print(expandObject.getTag());
//            print(Main.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 数组转List
    private static void arrayToList() {
        String[] array = "a,e,dd,fe,ged".split(",");
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.add("bbb");
        System.out.println(list.size());
        System.out.println(list.toArray().length);
        System.out.println("1.0.2".split("\\.").length);
    }

    @Override
    public void up() {
        System.out.println("main-up");
//        Control.controlVar = "final var can't be modified";//interface里面的变量默认是static和final的
        System.out.println(Control.controlVar);
    }

    @Override
    public void down() {
        System.out.println("main-up");
    }

    private static void print(String s){
        System.out.println(s);
    }

    @Override
    void left() {
        System.out.println("main-left");
        right();
        System.out.println(baseVar);
        System.out.println(Base.baseStaticVar);
    }
}
