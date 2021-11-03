package com.example.headspring;

import org. reflections.Reflections;

import java.util.*;
import java.util.function.Function;

public class JavaConfig implements Config {

    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        this.ifc2ImplClass = ifc2ImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            final Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            System.out.println(Arrays.asList(classes).toString());
            if (classes.size() != 1) throw new RuntimeException(ifc + "have more then 0 implementations");
            return classes.iterator().next();
        });
    }
}
