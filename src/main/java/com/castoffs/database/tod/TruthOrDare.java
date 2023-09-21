package com.castoffs.database.tod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TruthOrDare {

    private List<String> truths;
    private List<String> dares;

    public TruthOrDare() {
        this.truths = new ArrayList<>();
        this.dares = new ArrayList<>();

        this.truths.add("What is your biggest fear?");
        this.truths.add("What is your biggest fantasy?");
    }

    public void addTruth(String truth) {
        this.truths.add(truth);
    }

    public void addDare(String dare) {
        this.dares.add(dare);
    }

    public String getRandomTruth() {
        return this.truths.get(ThreadLocalRandom.current().nextInt(this.truths.size()));
    }

    public String getRandomDare() {
        return this.dares.get(ThreadLocalRandom.current().nextInt(this.dares.size()));
    }

    public List<String> getTruths() {
        return this.truths;
    }

    public List<String> getDares() {
        return this.dares;
    }
    
}
