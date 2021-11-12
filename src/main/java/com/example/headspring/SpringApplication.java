package com.example.headspring;

import java.util.Map;

public class SpringApplication {

    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass){
        final JavaConfig config = new JavaConfig(packageToScan, ifc2ImplClass);
        final ApplicationContext context = new ApplicationContext(config);
        final ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
