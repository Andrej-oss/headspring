package com.example.headspring;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        final T t = type.getDeclaredConstructor().newInstance();

        configurators.forEach( configurator -> configurator.configure(t,context));
        invokeInit(type, t);
        return t;
    }

    private <T> void invokeInit(Class<T> type, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : type.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)){
                method.invoke(t);
            }
        }
    }
}
