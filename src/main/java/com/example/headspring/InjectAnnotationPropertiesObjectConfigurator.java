package com.example.headspring;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectAnnotationPropertiesObjectConfigurator implements ObjectConfigurator {

    private Map<String, String> properties;

    @SneakyThrows
    public InjectAnnotationPropertiesObjectConfigurator() {
        final String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("application.properties")).getPath();
        final Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        properties = lines
                .map(line -> line.split("="))
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object t) {

        for (Field field:
                t.getClass().getDeclaredFields()) {
            final InjectProperty injectProperty = field.getAnnotation(InjectProperty.class);
            String value;
            if (injectProperty != null) {
                if (injectProperty.value().isEmpty()) {
                    value = properties.get(field.getName());
                } else {
                    value = injectProperty.value();
                }
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
