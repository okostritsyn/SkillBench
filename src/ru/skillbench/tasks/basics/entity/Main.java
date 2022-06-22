package ru.skillbench.tasks.basics.entity;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Employee currEmpl = new EmployeeImpl();
        Employee currManager = new EmployeeImpl();
        currEmpl.setManager(currManager);
        System.out.println(currEmpl);
        System.out.println(currEmpl.getTopManager());
    }
}
