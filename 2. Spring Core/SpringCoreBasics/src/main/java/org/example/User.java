package org.example;

public class User {
    private final int age;
    private final String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    int getAge(){
        return age;
    }

    String getName() {
        return name;
    }
}
