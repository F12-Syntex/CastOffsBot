package com.kodo.handler;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class ListUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getRandomElement(Collection<T> collection) {
        int index = ThreadLocalRandom.current().nextInt(collection.size());
        return collection.toArray((T[]) new Object[collection.size()])[index];
    }
    
}
