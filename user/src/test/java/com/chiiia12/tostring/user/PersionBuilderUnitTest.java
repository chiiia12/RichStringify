package com.chiiia12.tostring.user;

import org.junit.Test;

public class PersionBuilderUnitTest {
    @Test
    public void whenBuildPersonWithStringify_thenObjectHasPropertyValues() {
        //person test
        Person person = new Person();
        person.age = 34;
        person.name = "aaa";
        System.out.println("toString(): " + Stringify.toString(person));

        //animal test
        Animal animal = new Animal();
        animal.age = 3;
        animal.name = "hoge";
        System.out.println("toString(): " + Stringify.toString(animal));
    }
}
