package com.example.headspring;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ConsoleAnnouncer implements Announcer{

    @Autowired
    private Recommendator recommendator;

    public void announce(String message) {
        recommendator.recommend();
        System.out.println(message);
    }
}
