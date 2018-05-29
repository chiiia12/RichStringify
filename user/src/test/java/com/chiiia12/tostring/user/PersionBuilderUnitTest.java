package com.chiiia12.tostring.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersionBuilderUnitTest {
    @Test
    public void whenBuildPersonWithBuilder_thenObjectHasPropertyValues() {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.setAge(25).setName("John").build();
        assertEquals(25, person.getAge());
        assertEquals("John", person.getName());
//        assertEquals("toString", personBuilder.toString());
        System.out.println("toString(): " + personBuilder.toString());
    }

    @Test
    public void whenBuildPersonWithStringify_thenObjectHasPropertyValues() {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.setAge(25).setName("John").build();
        PersonStringify personStringify = new PersonStringify(person);
        System.out.println("toString(): " + personStringify.toString());
    }

}
