package com.kodo.codewars.scraper;

import java.awt.Color;
import java.lang.reflect.Field;

public class Rank {
    private int id;
    private String name;
    private String color;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public int getDifficulty(){
        return this.id*-1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color getColorEnum(){

        if(this.color == null){
            return Color.gray;
        }

        switch(this.color){
            case "white":
                return Color.white;
            case "yellow":
                return Color.yellow;
            case "blue":
                return Color.cyan;
            case "purple":
                return Color.magenta;
            case "red":
                return Color.red;
            case "orange":
                return Color.orange;
            case "green":
                return Color.green;
            case "black":
                return Color.black;
            default:
                return Color.gray;
        }
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