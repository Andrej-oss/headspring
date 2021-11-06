package com.example.headspring;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory {

    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private static ObjectFactory objectFactory = new ObjectFactory();
    private Config config = new JavaConfig("com.example", new HashMap<>(Map.of(Policemen.class, AngryPolicemen.class)));

    @SneakyThrows
    private ObjectFactory() {
        for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
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

        configurators.forEach( configurator -> configurator.configure(t));
        return t;
    }
}
