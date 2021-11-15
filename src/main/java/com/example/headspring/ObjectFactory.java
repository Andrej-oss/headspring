package com.example.headspring;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class ObjectFactory {

    private  ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type){
        T t = type.getDeclaredConstructor().newInstance();

        T finalT = t;
        configurators.forEach(configurator -> configurator.configure(finalT, context));
        invokeInit(type, t);

        t = wrapWithProxyIfNeeded(type, t);

        return t;
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> type, T t) {
        for (ProxyConfigurator proxy:
             proxyConfigurators) {
            t = (T) proxy.replaceWithProxyIfNeeded(t, type);
        }
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
