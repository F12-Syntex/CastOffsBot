package com.castoffs.handler;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The `ListUtils` class provides utility methods for working with collections, particularly for
 * retrieving random elements from a given collection. It includes a single static method to
 * accomplish this task.
 */
public class ListUtils {

    /**
     * Retrieve a random element from the provided collection.
     *
     * @param collection The collection from which to retrieve a random element.
     * @param <T>        The type of elements in the collection.
     * @return A random element from the collection.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRandomElement(Collection<T> collection) {
        // Generate a random index within the bounds of the collection's size
        int index = ThreadLocalRandom.current().nextInt(collection.size());

        // Convert the collection to an array of the same type and return the element at the random index
        return collection.toArray((T[]) new Object[collection.size()])[index];
    }
}
