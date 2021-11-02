package com.example.headspring;

public class Main {
    public static void main(String[] args) {
        final Room room = new Room();
        final CoronaDisinfect coronaDisinfect = new CoronaDisinfect();
        coronaDisinfect.start(room);
    }
}
