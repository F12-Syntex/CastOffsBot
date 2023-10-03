package com.castoffs.database.tod;

import java.util.Collections;
import java.util.List;

public class TodWrapper {

    private TruthOrDare truthOrDare;

    private List<String> truths;
    private List<String> dares;

    public TodWrapper(TruthOrDare truthOrDare) {
        this.truthOrDare = truthOrDare;

        this.truths = truthOrDare.getTruths();
        this.dares = truthOrDare.getDares();

        this.reset();
    }

    public TruthOrDare getTruthOrDare() {
        return this.truthOrDare;
    }

    public void reset(){
        this.truths = truthOrDare.getTruths();
        this.dares = truthOrDare.getDares();

        Collections.shuffle(dares);
        Collections.shuffle(truths);
    }

    public String getRandomTruth() {

        if(this.truths.isEmpty()){
            this.reset();
        }

        String truth = this.truths.get(0);
        this.truths.remove(0);
        return truth;
    }

    public String getRandomDare() {

        if(this.dares.isEmpty()){
            this.reset();
        }

        String dare = this.dares.get(0);
        this.dares.remove(0);
        return dare;
    }
    
}
