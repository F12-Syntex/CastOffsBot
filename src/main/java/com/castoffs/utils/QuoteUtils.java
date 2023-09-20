package com.castoffs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.NotNull;

/**
 * The `QuoteUtils` class is a utility class for managing and retrieving a collection of inspirational
 * quotes. It provides a static method to fetch a random quote from the predefined list of quotes.
 */
public class QuoteUtils {

    private static List<String> quotes = new ArrayList<>();

    // Initialize the list of quotes with inspirational quotes attributed to Steve Jobs.
    static {
        quotes.add("Innovation distinguishes between a leader and a follower. - Steve Jobs");
        quotes.add("Your time is limited, don't waste it living someone else's life. - Steve Jobs");
        quotes.add("The only way to do great work is to love what you do. - Steve Jobs");
        quotes.add("If you haven't found it yet, keep looking. Don't settle. - Steve Jobs");
        quotes.add("Have the courage to follow your heart and intuition. They somehow already know what you truly want to become. - Steve Jobs");
        quotes.add("Stay hungry, stay foolish. - Steve Jobs");
        quotes.add("The people who are crazy enough to think they can change the world are the ones who do. - Steve Jobs");
        quotes.add("Don't let the noise of others' opinions drown out your own inner voice. - Steve Jobs");
        quotes.add("If today were the last day of your life, would you want to do what you are about to do today? - Steve Jobs");
        quotes.add("Sometimes life is going to hit you in the head with a brick. Don't lose faith. - Steve Jobs");
        quotes.add("I want to put a ding in the universe. - Steve Jobs");
        quotes.add("I think if you do something and it turns out pretty good, then you should go do something else wonderful, not dwell on it for too long. Just figure out what's next. - Steve Jobs");
        quotes.add("I'm convinced that about half of what separates the successful entrepreneurs from the non-successful ones is pure perseverance. - Steve Jobs");
        quotes.add("I'm as proud of many of the things we haven't done as the things we have done. - Steve Jobs");
        quotes.add("I'm the only person I know that's lost a quarter of a billion dollars in one year.... It's very character-building. - Steve Jobs");
        quotes.add("I'm an optimist in the sense that I believe humans are noble and honorable, and some of them are really smart. I have a very optimistic view of individuals. - Steve Jobs");
        quotes.add("I'm convinced that what separates the successful entrepreneurs from the non-successful ones is pure perseverance. - Steve Jobs");
        quotes.add("I'm an optimist in the sense that I believe humans are noble and honorable, and some of them are really smart. I have a very optimistic view of individuals. - Steve Jobs");
        quotes.add("I'm convinced that about half of what separates the successful entrepreneurs from the non-successful ones is pure perseverance. - Steve Jobs");
        quotes.add("I'm convinced that what separates the successful entrepreneurs from the non-successful ones is pure perseverance. - Steve Jobs");
    }

    /**
     * Get a random inspirational quote from the list.
     *
     * @return A randomly selected inspirational quote.
     */
    @NotNull
    public static String getRandomQuote() {
        int index = ThreadLocalRandom.current().nextInt(quotes.size());
        return quotes.get(index);
    }
}
