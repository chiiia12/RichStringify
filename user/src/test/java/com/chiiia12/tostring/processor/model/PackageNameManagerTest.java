package com.chiiia12.tostring.processor.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class PackageNameManagerTest {
    private PackageNameManager packageNameManager;

    @Before
    public void setUp() throws Exception {
        packageNameManager = new PackageNameManager();
    }

    @Test
    public void testCreatePackageName_eqaualPaclageName() {
        //given
        String className1 = "com.chiiia12.tostring.user.Animal";
        String className2 = "com.chiiia12.tostring.user.Animal";
        Map<String, List<Pair<String, String>>> map = new HashMap<>();
        map.put(className1, new ArrayList<>());
        map.put(className2, new ArrayList<>());
        //when
        String result = packageNameManager.createPackageName(map);
        //then
        assertThat(result, is("com.chiiia12.tostring.user"));
    }
}