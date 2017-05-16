package com.amazon.example.ap;

import com.amazon.example.ap.annotations.Stringify;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import static com.amazon.example.ap.ElementUtil.packageNameOf;

@SupportedAnnotationTypes("com.amazon.example.ap.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class StringifyProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        classSummariesByPackageName(roundEnv)
                .entrySet()
                .forEach(e -> new StringifyEmitter(e.getKey(), e.getValue()).emit(processingEnv));

        return true;
    }

    private Map<String, Set<ClassSummary>> classSummariesByPackageName(RoundEnvironment roundEnv) {
        Map<String, Set<ClassSummary>> ret = new HashMap<>();

        // roundEnv allows you to get the elements annotated with the annotation you're interested in
        for (Element e : roundEnv.getElementsAnnotatedWith(Stringify.class)) {
            if (e.getKind() != ElementKind.CLASS) {
                continue;
            }

            Set<ClassSummary> existing = ret.computeIfAbsent(packageNameOf(e), k -> new HashSet<>());
            existing.add(new ClassSummary((TypeElement) e));
        }
        return ret;
    }
}
