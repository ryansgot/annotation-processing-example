package com.amazon.example.ap;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class ElementUtil {

    public static Map<String, TypeMirror> declaredFieldsExcludingModifiers(final TypeElement typeElement, final Modifier... excludedModifiers) {
        return typeElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD && !hasOne(typeElement, excludedModifiers))
                .collect(toMap(e -> e.getSimpleName().toString(), Element::asType));
    }

    public static String packageNameOf(Element e) {
        if (e.getKind() == ElementKind.PACKAGE) {
            return e.toString();
        }
        return packageNameOf(e.getEnclosingElement());
    }

    private static boolean hasOne(Element element, Modifier... checkedModifiers) {
        final Set<Modifier> modifiers = element.getModifiers();
        return checkedModifiers == null
                || checkedModifiers.length == 0
                || Arrays.stream(checkedModifiers).anyMatch(modifiers::contains);
    }
}
