package com.example.headspring;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedAnnotationProxyConfigurator implements ProxyConfigurator{

    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
        if (implClass.getInterfaces().length == 0){
            return Enhancer.create(implClass, (InvocationHandler) (o, method, args) -> {
                return getInvocationLogic(t, method, args);
            });
        }
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            return  Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> {
                return getInvocationLogic(t, method, args);
            });
        } else {
            return t;
        }
    }

    private Object getInvocationLogic(Object t, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        System.out.println("What are you doing ugly sheet");
        return method.invoke(t, args);
    }
}
