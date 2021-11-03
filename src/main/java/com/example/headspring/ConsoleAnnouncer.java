package com.example.headspring;

public class ConsoleAnnouncer implements Announcer{

    private Recommendator recommendator = ObjectFactory.getObjectFactory().createObject(Recommendator.class);

    public void announce(String message) {
        recommendator.recommend();
        System.out.println(message);
    }
}
