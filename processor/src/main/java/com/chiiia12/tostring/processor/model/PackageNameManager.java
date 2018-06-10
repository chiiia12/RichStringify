package com.chiiia12.tostring.processor.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public class PackageNameManager {

    public String createPackageName(Map<String, List<Pair<String, String>>> map) {
        String[] packageName = null;
        for (String cn : map.keySet()) {
            if (packageName == null) {
                String[] split = cn.split("\\.");
                packageName = Arrays.copyOfRange(split, 0, split.length - 1);
                continue;
            }
            String[] className = cn.split("\\.");
            if (packageName == className) {
                continue;
            }
            for (int i = 0; i < packageName.length; i++) {
                if (!packageName[i].equals(className[i])) {
                    return String.join(".", Arrays.copyOfRange(packageName, 0, i - 1));
                }
            }
        }
        return String.join(".", packageName);
    }
}
