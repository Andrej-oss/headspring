package com.example.headspring;

//@Singleton
@Deprecated
public class CommercialRecommender implements Recommendator {
    @InjectProperty
    private String alcohol;

    public void init() {
        System.out.println("Commercial recommendator was created");
    }

    @Override
    public void recommend() {
        System.out.println("Recommend drink alcohol " + alcohol);
    }
}
