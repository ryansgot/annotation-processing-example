package com.amazon.example.ap;

import com.amazon.example.ap.annotations.Stringify;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.*;

import static com.amazon.example.ap.ElementUtil.declaredFieldsExcludingModifiers;

public class ClassSummary {

    private final String className;
    private final String dateFormat;
    private final Map<String, TypeMirror> fieldNameTypeMap;

    public ClassSummary(TypeElement typeElement) {
        this(typeElement.getQualifiedName().toString(),
                typeElement.getAnnotation(Stringify.class).dateFormat(),
                declaredFieldsExcludingModifiers(typeElement, Modifier.PRIVATE, Modifier.STATIC));
    }

    /*pacakge*/ ClassSummary(String className, String dateFormat, Map<String, TypeMirror> fieldNameTypeMap) {
        this.className = className;
        this.dateFormat = dateFormat;
        this.fieldNameTypeMap = new HashMap<>(fieldNameTypeMap == null ? Collections.emptyMap() : fieldNameTypeMap);
    }

    public String getClassName() {
        return className;
    }

    public String getClassSimpleName() {
        return className.substring(className.lastIndexOf('.') + 1);
    }

    public String getDateFormat() {
        return hasNonDefaultDateFormat() ? dateFormat : null;
    }

    public boolean hasNonDefaultDateFormat() {
        return dateFormat != null && !dateFormat.isEmpty();
    }

    public Map<String, TypeMirror> getFieldNameTypeMap() {
        return fieldNameTypeMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSummary that = (ClassSummary) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(dateFormat, that.dateFormat) &&
                Objects.equals(fieldNameTypeMap, that.fieldNameTypeMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, dateFormat, fieldNameTypeMap);
    }
}
