package com.hr;

public abstract class Base {
    public String baseVar = "define abstract var" ;
    public static String baseStaticVar = "define abstract static var" ;
    abstract void left();
    void right(){
        System.out.println("base-right");
    }
}
