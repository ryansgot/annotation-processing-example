package com.amazon.example.ap;

import com.squareup.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class StringifyEmitter {

    private final String packageName;
    private final Set<ClassSummary> classSummaries;

    public StringifyEmitter(String packageName, Set<ClassSummary> classSummaries) {
        this.packageName = packageName;
        this.classSummaries = classSummaries == null ? Collections.emptySet() : classSummaries;
    }

    public void emit(ProcessingEnvironment processingEnv) {

        // initiates a builder for a class that can have code added to it--we're just going to add methods
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(ClassName.bestGuess(packageName + ".Stringifier"))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        for (ClassSummary classSummary : classSummaries) {
            if (!classSummary.hasNonDefaultDateFormat()) {
                continue;
            }
            classBuilder.addField(FieldSpec.builder(SimpleDateFormat.class, dateFormatFieldNameOf(classSummary))
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .initializer("new $T($S)", ClassName.get(SimpleDateFormat.class), classSummary.getDateFormat())
                    .build());
        }
        for (ClassSummary classSummary : classSummaries) {
            classBuilder.addMethod(MethodSpec.methodBuilder("stringify")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(String.class)
                    .addParameter(ClassName.bestGuess(classSummary.getClassName()), "in", Modifier.FINAL)
                    .addCode(stringBuildingCode(classSummary))
                    .build());
        }

        try {
            JavaFile.builder(packageName, classBuilder.build())
                    .indent("    ")
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private CodeBlock stringBuildingCode(ClassSummary classSummary) {
        String builderVarName = "builder";
        CodeBlock.Builder codeBuilder = CodeBlock.builder()
                .addStatement("$T $L = new $T($S)",
                        ClassName.get(StringBuilder.class),
                        builderVarName,
                        ClassName.get(StringBuilder.class),
                        classSummary.getClassSimpleName() + "{");
        for (Map.Entry<String, TypeMirror> entry : classSummary.getFieldNameTypeMap().entrySet()) {
            codeBuilder.add(appendBlock(builderVarName,
                    entry.getKey(),
                    entry.getValue(),
                    dateFormatFieldNameOf(classSummary)));
        }
        return codeBuilder.addStatement("return $L.delete($N.length() - 2, $N.length()).append('}').toString()",
                builderVarName,
                builderVarName,
                builderVarName).build();
    }

    private CodeBlock appendBlock(String builderVarName, String varName, TypeMirror varType, String dateFormat) {
        if (varType.toString().equals(Date.class.getName())) {
            return CodeBlock.builder()
                    .addStatement("$N.append($S).append($S).append($N.format($N.$L)).append($S)",
                            builderVarName,
                            varName,
                            "=",
                            dateFormat,
                            "in",
                            varName,
                            ", ").build();
        }
        return CodeBlock.builder()
                .addStatement("$N.append($S).append($S).append($T.valueOf($N.$L)).append($S)",
                        builderVarName,
                        varName,
                        "=",
                        ClassName.get(String.class),
                        "in",
                        varName,
                        ", ").build();
    }

    private String dateFormatFieldNameOf(ClassSummary classSummary) {
        return Character.toLowerCase(classSummary.getClassSimpleName().charAt(0))
                + classSummary.getClassSimpleName().substring(1) + "DateFormat";
    }
}
