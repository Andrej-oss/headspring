package com.example.headspring;


import javax.annotation.PostConstruct;

public class PolicemenImpl implements Policemen {

    @Autowired
    private Recommendator recommendator;

    @PostConstruct
    public void init(){
        System.out.println(recommendator);
    }

    public PolicemenImpl() {
        System.out.println(recommendator);
    }

    public void makeGetOutPeople() {
        System.out.println("Pach pach!Get out mother fuckers!!!!");
        System.out.println(recommendator.getClass());
        recommendator.recommend();
    }
}
