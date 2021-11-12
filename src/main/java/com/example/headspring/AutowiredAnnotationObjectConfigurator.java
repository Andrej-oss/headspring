package com.example.headspring;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class AutowiredAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        final Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)){
                field.setAccessible(true);
                final Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
