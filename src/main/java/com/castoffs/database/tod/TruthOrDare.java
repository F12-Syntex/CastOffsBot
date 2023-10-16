package com.castoffs.database.tod;

import java.util.Stack;
import java.util.stream.Collectors;

public class TruthOrDare {

    private TodWrapper wrapper;

    private Stack<String> truths;
    private Stack<String> dares;

    public TruthOrDare() {
        this.truths = new Stack<>();
        this.dares = new Stack<>();

        this.wrapper = new TodWrapper(this);

        this.truths.add("What is your biggest fear?");
        this.truths.add("What is your biggest fantasy?");
    }

    public void addTruth(String truth) {
        this.truths.add(truth);
    }

    public void addDare(String dare) {
        this.dares.add(dare);
    }

    /**
     * gets a random truth, where the weight of each truth depends on how many times it has been used
     * @return
     */
    public Stack<String> getTruths() {
        return this.truths.stream().distinct().collect(Collectors.toCollection(Stack::new));
    }

    public Stack<String> getDares() {
        return this.dares.stream().distinct().collect(Collectors.toCollection(Stack::new));
    }

    public TodWrapper getWrapper() {
        return this.wrapper;
    }
    
}
