package com.example.headspring;

public class CommercialRecommender implements Recommendator {
    @InjectProperty
    private String alcohol;

    @Override
    public void recommend() {
        System.out.println("Recommend drink alcohol " + alcohol);
    }
}
