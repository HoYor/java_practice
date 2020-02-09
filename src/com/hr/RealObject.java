package com.hr;

public class RealObject implements Control {
    @Override
    public void up() {
        System.out.println("realObject-up");
    }

    @Override
    public void down() {
        System.out.println("realObject-down");
    }
}
