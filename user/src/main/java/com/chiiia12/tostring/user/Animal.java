package com.chiiia12.tostring.user;

import com.chiiia12.tostring.processor.ToStringLabel;

public class Animal {
    @ToStringLabel("名前")
    public String name;
    @ToStringLabel
    public int age;
}
