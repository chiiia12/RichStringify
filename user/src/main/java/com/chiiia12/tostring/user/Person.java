package com.chiiia12.tostring.user;

import com.chiiia12.tostring.processor.BuilderProperty;
import com.chiiia12.tostring.processor.ToStringLabel;

public class Person {
    @ToStringLabel
    public int age;
    public String name;

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

    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }
}
