package com.example.headspring;

import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;


public class ObjectFactory {

    private  ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type){
        final T t = type. getDeclaredConstructor().newInstance();

        configurators.forEach( configurator -> configurator.configure(t,context));
        return t;
    }
}
