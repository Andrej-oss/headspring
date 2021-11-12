package com.example.headspring;


public class PolicemenImpl implements Policemen {

    @Autowired
    private Recommendator recommendator;

    public void makeGetOutPeople() {
        System.out.println("Pach pach!Get out mother fuckers!!!!");
        recommendator.recommend();
    }
}
