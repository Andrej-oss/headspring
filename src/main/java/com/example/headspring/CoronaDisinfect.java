package com.example.headspring;

public class CoronaDisinfect {

    @Autowired
    private Announcer announcer;
    @Autowired
    private Policemen policemen;


    public void start(Room room){
        announcer.announce("Lets start disinfection. Everybody get out");
        policemen.makeGetOutPeople();
        disinfect();
        announcer.announce("Can enter to the room");

    }

    private void disinfect(){
        System.out.println("Corona get fucking out!Stay away! And virus falling to hell");
    }
}
