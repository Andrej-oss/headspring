package com.example.headspring;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication
                .run("com.example.headspring", new HashMap<>(Map.of(Policemen.class, PolicemenImpl.class)) {
        });
        final CoronaDisinfect coronaDisinfect = applicationContext.getObject(CoronaDisinfect.class);
        coronaDisinfect.start(new Room());
    }
}
