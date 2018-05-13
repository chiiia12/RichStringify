package com.chiiia12.tostring.user;

import com.chiiia12.tostring.processor.BuilderProperty;

public class Person {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    @BuilderProperty
    public void setAge(int age) {
        this.age = age;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
