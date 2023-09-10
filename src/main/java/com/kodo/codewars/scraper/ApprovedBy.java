package com.kodo.codewars.scraper;

import java.lang.reflect.Field;

public class ApprovedBy {
    private String username;
    private String url;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Get the declared fields of the class
        Field[] fields = getClass().getDeclaredFields();

        // Iterate over each field
        for (Field field : fields) {
            // Set the field as accessible (in case it is private)
            field.setAccessible(true);

            try {
                // Get the name and value of the field
                String name = field.getName();
                Object value = field.get(this);

                // Append the name and value to the StringBuilder
                sb.append(name).append(": ").append(value).append(", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Remove the trailing comma and space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        // Return the final string representation
        return sb.toString();
    }
}