package com.hr;

public class Student {
    private String name = "王一鸣";
    private final String grade = null == null ? "3年级" : "";
    private static String clazz = "网红12班";
    private final static String school = null == null ? "上海一中" : "";

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public static String getClazz() {
        return clazz;
    }

    public static void setClazz(String clazz) {
        Student.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public static String getSchool() {
        return school;
    }
}
