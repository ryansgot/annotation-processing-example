package com.amazon.example.ap;

import com.amazon.example.ap.annotations.Stringify;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.amazon.example.ap.annotations.*")    // <-- all Annotations in the com.amazon.example.ap.annotations package
@SupportedSourceVersion(SourceVersion.RELEASE_7)                    // <-- supports Java 7 and up
public class StringifyProcessor extends AbstractProcessor {

    // processingEnv allows you to get important things like messager and util classes (like Filer)

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Map.Entry<String, Set<TypeElement>> entry : packageNameToStringifiedElementsMap(roundEnv).entrySet()) {
            createStringifyClassForPackage(entry.getKey(), entry.getValue());
        }

        return true;    // <-- says this annotation processor consumed the annotations
    }

    private void createStringifyClassForPackage(String pacakgeName, Set<TypeElement> typeElements) {

        // initiates a builder for a class that can have code added to it--we're just going to add methods
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(ClassName.bestGuess(pacakgeName + ".Stringifier"))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        // Add a public static method for each type Element
        for (TypeElement typeElement : typeElements) {
            classBuilder.addMethod(MethodSpec.methodBuilder("stringify")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(String.class)
                    .addParameter(ClassName.bestGuess(typeElement.getQualifiedName().toString()), "in", Modifier.FINAL)
                    .addCode(stringBuildingCode(typeElement))
                    .build());
        }

        try {
            JavaFile.builder(pacakgeName, classBuilder.build())
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private CodeBlock stringBuildingCode(TypeElement typeElement) {
        String builderVarName = "builder";
        CodeBlock.Builder codeBuilder = CodeBlock.builder()
                .addStatement(
                        "$T $L = new $T($S)",
                        ClassName.get(StringBuilder.class),
                        builderVarName,
                        ClassName.get(StringBuilder.class),
                        typeElement.getSimpleName() + "{"
                );
        for (Element e : typeElement.getEnclosedElements()) {
            if (e.getKind() != ElementKind.FIELD || e.getModifiers().contains(Modifier.STATIC)) {
                continue;   // Don't want to put in non fields and static fields
            }
            codeBuilder.addStatement(
                    "$N.append($S).append($S).append($T.valueOf($N.$L)).append($S)",
                    builderVarName,
                    e.getSimpleName(),
                    "=",
                    ClassName.get(String.class),
                    "in",
                    e.getSimpleName(),
                    ", "
            );
        }
        return codeBuilder.addStatement(
                "return $L.delete($N.length() - 2, $N.length()).append('}').toString()",
                builderVarName,
                builderVarName,
                builderVarName
        ).build();
    }

    private Map<String, Set<TypeElement>> packageNameToStringifiedElementsMap(RoundEnvironment roundEnv) {
        Map<String, Set<TypeElement>> ret = new HashMap<>();

        // roundEnv allows you to get the elements annotated with the annotation you're interested in
        for (Element e : roundEnv.getElementsAnnotatedWith(Stringify.class)) {
            logElement(e, Stringify.class);
            if (e.getKind() != ElementKind.CLASS) {
                continue;
            }

            Set<TypeElement> stringifyAnnotatedElements = ret.get(packageNameOf(e));
            if (stringifyAnnotatedElements == null) {
                stringifyAnnotatedElements = new HashSet<>();
                ret.put(packageNameOf(e), stringifyAnnotatedElements);
            }
            if (isUniqueType((TypeElement) e, stringifyAnnotatedElements)) {
                stringifyAnnotatedElements.add((TypeElement) e);
            }
        }
        return ret;
    }

    private boolean isUniqueType(TypeElement inputTypeElement, Set<TypeElement> stringifyAnnotatedElements) {
        for (TypeElement existingTypeElement : stringifyAnnotatedElements) {
            if (inputTypeElement.getQualifiedName().equals(existingTypeElement.getQualifiedName())) {
                return false;
            }
        }
        return true;
    }

    private void logElement(Element e, Class<? extends Annotation> annotationCls) {
        // each element will have a kind (type, field, local variable, etc)
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found annotation of type " + annotationCls.getSimpleName() + " annotating " + e.getSimpleName() + ", an element of kind: " + e.getKind());
    }


    // search all enclosing elements until a pacakge is found and return the to-string of that element (the fully-qualified name)
    private String packageNameOf(Element e) {
        if (e.getKind() == ElementKind.PACKAGE) {
            return e.toString();
        }
        return packageNameOf(e.getEnclosingElement());
    }
}
