package com.chiiia12.tostring.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import javafx.util.Pair;

@SupportedAnnotationTypes("com.chiiia12.tostring.processor.ToStringLabel")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class ToStringLabelProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            Map<Boolean, List<Element>> annotatedField = annotatedElements.stream().collect(Collectors.partitioningBy(element -> true));
            List<Element> setters = annotatedField.get(true);

            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("Stringify");
            typeBuilder.addModifiers(Modifier.PUBLIC);
            Map<String, List<Pair<String, String>>> map = new HashMap<>();
            for (Element e : setters) {
                String className = ((TypeElement) e.getEnclosingElement()).getQualifiedName().toString();
                ToStringLabel toStringLabel = e.getAnnotation(ToStringLabel.class);
                String label = toStringLabel.value();
                if (!map.containsKey(className)) {
                    map.put(className, new ArrayList<>());
                }
                map.get(className).add(new Pair(e.getSimpleName().toString(), label.isEmpty() ? e.getSimpleName().toString() : label));
            }
            MethodSpec toString = buildToStringMethod(map);

            //add separator field
            FieldSpec objectField = FieldSpec.builder(String.class, "separator").addModifiers(Modifier.STATIC).addModifiers(Modifier.FINAL).initializer("\"=\"").build();


            //class build
            typeBuilder.addField(objectField).addMethod(toString);
            TypeSpec typeSpec = typeBuilder.build();

            //TODO get package name from dinamic
            JavaFile javaFile = JavaFile.builder("com.chiiia12.tostring.user", typeSpec).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    private MethodSpec buildToStringMethod
            (Map<String, List<Pair<String, String>>> setterMap) {
        ParameterSpec param = ParameterSpec.builder(Object.class, "object").build();
        MethodSpec.Builder toStringMethodBuilder = MethodSpec.methodBuilder("toString");
        toStringMethodBuilder.addModifiers(Modifier.PUBLIC).addModifiers(Modifier.STATIC).addParameter(param).returns(String.class);

        for (Map.Entry<String, List<Pair<String, String>>> entry : setterMap.entrySet()) {
            int lastDot = entry.getKey().lastIndexOf('.');
            String simpleClassName = entry.getKey().substring(lastDot + 1);

            //add toString method
            toStringMethodBuilder.addCode(String.format("if(object instanceof %s) {\n ", simpleClassName))
                    .addCode(String.format("%s %s= (%s)object;\n", simpleClassName, simpleClassName.toLowerCase(), simpleClassName))
                    .addCode("return ")
                    .addCode(buildMessage(simpleClassName, entry))
                    .addCode("}\n ");
        }
        return toStringMethodBuilder.addCode("return null;\n").build();


    }

    private String buildMessage(String
                                        simpleClassName, Map.Entry<String, List<Pair<String, String>>> entry) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        entry.getValue().forEach(item -> {
            sb.append(item.getValue());
            sb.append(String.format(": \"+%s.", simpleClassName.toLowerCase()));
            sb.append(item.getKey());
            sb.append("+\"\\n");
        });
        sb.append("\";\n");
        return sb.toString();
    }
}
