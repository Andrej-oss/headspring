package com.example.headspring;

public class ConsoleAnnouncer implements Announcer{
    public void announce(String message) {
        System.out.println(message);
    }
}
