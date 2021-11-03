package com.example.headspring;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory {

    private static ObjectFactory objectFactory = new ObjectFactory();
    private Config config = new JavaConfig("com.example", new HashMap<>(Map.of(Policemen.class, AngryPolicemen.class)));

    private ObjectFactory() {
    }
    public static ObjectFactory getObjectFactory(){
        return objectFactory;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type){
        Class<? extends T> implClass = type;
        if (type.isInterface()){
            implClass = config.getImplClass(type);
            System.out.println(implClass);
        }
        final T t = implClass.getDeclaredConstructor().newInstance();

        final Field[] fields = implClass.getDeclaredFields();
        for (Field field:
             fields) {
            final InjectProperty injectProperty = field.getAnnotation(InjectProperty.class);
            final String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("application.properties")).getPath();
            final Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
            final Map<String, String> properties = lines
                                                      .map(line -> line.split("="))
                                                      .collect(toMap(arr -> arr[0], arr -> arr[1]));
            String value;
            if (injectProperty != null){
                if (injectProperty.value().isEmpty()){
                    value = properties.get(field.getName());
                } else {
                    value = injectProperty.value();
                }
                field.setAccessible(true);
                field.set(t, value);
            }
        }

        return t;
    }
}
