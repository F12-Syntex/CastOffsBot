package com.castoffs.database.tod;

import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class TodWrapper {

    private TruthOrDare truthOrDare;

    private Stack<String> truths;
    private Stack<String> dares;

    public TodWrapper(TruthOrDare truthOrDare) {
        this.truthOrDare = truthOrDare;

        this.truths = truthOrDare.getTruths();
        this.dares = truthOrDare.getDares();

        Collections.shuffle(truths);
        Collections.shuffle(dares);
    }

    public TruthOrDare getTruthOrDare() {
        return this.truthOrDare;
    }

    public String getRandomAny(){
        if(ThreadLocalRandom.current().nextBoolean()){
            return this.getRandomTruth();
        }
        return this.getRandomDare();   
    }

    public String getRandomTruth() {

        if(this.truths.isEmpty()){
            this.truths = truthOrDare.getTruths();
            Collections.shuffle(truths);
        }

        String truth = this.truths.pop();
        return truth;
    }

    public String getRandomDare() {

        if(this.dares.isEmpty()){
            this.dares = truthOrDare.getDares();
            Collections.shuffle(dares);
        }

        String dare = this.dares.pop();
        return dare;
    }
    
}
