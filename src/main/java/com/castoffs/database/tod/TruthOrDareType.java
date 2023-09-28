package com.castoffs.database.tod;

import java.util.concurrent.ThreadLocalRandom;

public enum TruthOrDareType {
    TRUTH, DARE;

    public static TruthOrDareType any(){
        return TruthOrDareType.values()[ThreadLocalRandom.current().nextInt(TruthOrDareType.values().length)];
    }
}
