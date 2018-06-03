package com.chiiia12.tostring.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

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

            String className = ((TypeElement) setters.get(0).getEnclosingElement()).
                    getQualifiedName().toString();
            Set<VariableElement> fields = ElementFilter.fieldsIn(annotatedElements);
            Map<String, String> setterMap = new HashMap<>();
            for (VariableElement field : fields) {
                TypeMirror fieldType = field.asType();
                String fullTypeClassName = fieldType.toString();
                setterMap.put(fullTypeClassName, field.getSimpleName().toString());
            }
            try {
                writeBuilderFile(className, setterMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void writeBuilderFile(String className, Map<String, String> setterMap) throws IOException {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Stringify";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

        //add field
        FieldSpec objectField = FieldSpec.builder(Object.class, "object").build();
        //add constructor
        ParameterSpec param = ParameterSpec.builder(Object.class, "param").build();
        MethodSpec constructor = MethodSpec.constructorBuilder().addParameter(param).addCode("object = param;\n").build();
        //add toString method
        MethodSpec toStringMethod = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC).returns(String.class)
                .addCode(String.format("if(object instanceof %s) {\n ", simpleClassName))
                .addCode(String.format("%s %s= (%s)object;\n", simpleClassName, simpleClassName.toLowerCase(), simpleClassName))
                .addCode("return \"")
                .addCode(buildMessage(setterMap))
                .addCode("}\n")
                .addCode("return null;\n")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("Stringify")
                .addModifiers(Modifier.PUBLIC)
                .addField(objectField)
                .addMethod(constructor)
                .addMethod(toStringMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        javaFile.writeTo(processingEnv.getFiler());
    }

    private String buildMessage(Map<String, String> setterMap) {
        StringBuilder sb = new StringBuilder();
        setterMap.entrySet().forEach(setter -> {
            sb.append(setter.getValue());
            sb.append(": \"+person.");
            sb.append(setter.getValue());
        });
        sb.append(";\n");
        return sb.toString();
    }
}
