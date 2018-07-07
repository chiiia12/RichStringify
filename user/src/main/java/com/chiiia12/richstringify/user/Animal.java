package com.chiiia12.richstringify.user;

import com.chiiia12.richstringify.processor.ToStringLabel;

public class Animal {
    @ToStringLabel("名前")
    public String name;
    @ToStringLabel
    public int age;
}
