package com.chiiia12.tostring.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
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

        //add toString method
        MethodSpec toStringMethod = MethodSpec.methodBuilder("toString").addModifiers(Modifier.PUBLIC).returns(String.class).addCode("return \"hoge\";\n").build();

        TypeSpec typeSpec = TypeSpec.classBuilder("Stringify").addModifiers(Modifier.PUBLIC).addMethod(toStringMethod).build();
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        javaFile.writeTo(processingEnv.getFiler());


//        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
//            if (packageName != null) {
//                out.print("package ");
//                out.print(packageName);
//                out.println(";");
//                out.println();
//            }
//            out.print("public class ");
//            out.print(builderSimpleClassName);
//            out.println(" {");
//            out.println();
//
//            out.print("    private ");
//            out.print(simpleClassName);
//            out.print(" object;");
//            out.println();
//
////            out.print("    private ");
////            out.print("char separator = \'=\';");
////            out.println();
//
//            out.print("    public ");
//            out.println(builderSimpleClassName + "(" + simpleClassName + " obj) {");
//            out.print(" this.object = obj;");
//            out.print("}");
//
//
//            out.print("    public ");
//            out.print("String");
//            out.println(" toString() {");
//            StringBuilder sb = new StringBuilder();
//            setterMap.entrySet().forEach(setter -> {
//                sb.append(setter.getValue());
//                sb.append(": \"+object.");
//                sb.append(setter.getValue());
//            });
//            out.println("        return \"" + sb.toString() + ";");
//            out.println("    }");
//            out.println();
//
//            out.println("}");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
