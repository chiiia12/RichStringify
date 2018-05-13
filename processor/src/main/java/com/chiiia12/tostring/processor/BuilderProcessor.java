package com.chiiia12.tostring.processor;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.chiiia12.processor.BuilderProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
