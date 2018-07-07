package com.chiiia12.richstringify.processor;

import javax.lang.model.element.Element;

public class ElementInfo {
    public ElementInfo(String variableName, String label, Element element) {
        this.variableName = variableName;
        this.label = label;
        this.element = element;
    }

    String variableName;
    String label;
    Element element;
}
