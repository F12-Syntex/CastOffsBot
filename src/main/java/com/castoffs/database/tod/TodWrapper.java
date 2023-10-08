package com.castoffs.database.tod;

import java.util.Collections;
import java.util.List;

public class TodWrapper {

    private TruthOrDare truthOrDare;

    private List<String> truths;
    private List<String> dares;

    private int truthCalls = 0;
    private int dareCalls = 0;

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

    public String getRandomTruth() {
        String truth = this.truths.get(0);
        this.truths.remove(0);
        this.truths.add(truth);
        truthCalls++;

        if(truthCalls == truths.size()){
            Collections.shuffle(truths);
            truthCalls = 0;
        }

        return truth;
    }

    public String getRandomDare() {
        String dare = this.dares.get(0);
        this.dares.remove(0);
        this.dares.add(dare);
        dareCalls++;

        if(dareCalls == dares.size()){
            Collections.shuffle(dares);
            dareCalls = 0;
        }

        return dare;
    }
    
}
