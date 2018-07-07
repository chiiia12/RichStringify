package com.chiiia12.tostring.user;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PersionBuilderUnitTest {
    @Test
    public void whenBuildPersonWithStringify() {
        //person test
        Person person = new Person();
        person.age = 34;
        person.name = "aaa";
        assertThat(Stringify.toString(person), is("age: 34\n"));
    }

    @Test
    public void whenBuildAnimalWithStringify() {
        //animal test
        Animal animal = new Animal();
        animal.age = 3;
        animal.name = "hoge";
        assertThat(Stringify.toString(animal), is("名前: hoge\nage: 3\n"));
    }
}
