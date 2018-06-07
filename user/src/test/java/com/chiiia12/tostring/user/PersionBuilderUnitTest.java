package com.chiiia12.tostring.user;

import org.junit.Test;

public class PersionBuilderUnitTest {
    @Test
    public void whenBuildPersonWithStringify_thenObjectHasPropertyValues() {
        //person test
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.setAge(25).setName("John").build();
        System.out.println("toString(): " + new Stringify(person).toString());
        System.out.println("toString(): " + Stringify.toString(person));

        //animal test
        Animal animal = new Animal();
        animal.age = 3;
        animal.name = "hoge";
        Stringify personStringify = new Stringify(animal);
        System.out.println("toString(): " + personStringify.toString());
        System.out.println("toString(): " + Stringify.toString(animal));
    }
}
