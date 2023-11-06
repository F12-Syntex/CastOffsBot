package com.castoffs.database.jackbox;

import java.util.List;

import com.castoffs.commands.jackbox.Question;

public class Quiplash {

    private List<Question> questions;

    public Quiplash(){
        Question question1 = new Question("What's the best way to start your day?", List.of(
            "A cup of coffee and a good book",
            "A morning jog followed by a healthy breakfast",
            "Watching funny cat videos",
            "A long, relaxing shower"
        ));
        
        Question question2 = new Question("What's the secret to a happy life?", List.of(
            "Eating lots of chocolate",
            "Spending time with loved ones",
            "Traveling and exploring new places",
            "Dancing like nobody's watching"
        ));
        
        Question question3 = new Question("What's the most important quality in a friend?", List.of(
            "Loyalty",
            "Sense of humor",
            "Honesty",
            "Kindness"
        ));
        
        Question question4 = new Question("What's the best way to spend a lazy Sunday?", List.of(
            "Binge-watching your favorite TV show",
            "Taking a nap",
            "Reading a good book",
            "Having a picnic in the park"
        ));
        
        Question question5 = new Question("What's the worst superpower to have?", List.of(
            "Super-sweating",
            "Invisibility (but only when no one is looking)",
            "Being able to talk to plants",
            "Extreme ticklishness"
        ));
        
        Question question6 = new Question("What's the best thing about being a kid?", List.of(
            "No responsibilities",
            "Playing games all day",
            "Getting lots of hugs",
            "Imagination"
        ));
        
        Question question7 = new Question("What's the most important meal of the day?", List.of(
            "Breakfast",
            "Lunch",
            "Dinner",
            "All meals are equally important!"
        ));
        
        Question question8 = new Question("What's the best way to relax after a long day?", List.of(
            "Taking a hot bath",
            "Watching your favorite TV show",
            "Listening to music",
            "Meditating"
        ));
        
        Question question9 = new Question("What's the most underrated animal?", List.of(
            "Sloth",
            "Platypus",
            "Axolotl",
            "Quokka"
        ));
        
        Question question10 = new Question("What's the best way to spend a vacation?", List.of(
            "Exploring a new city",
            "Relaxing on a beach",
            "Going on an adventure",
            "Trying local cuisine"
        ));

        this.questions = List.of(question1, question2, question3, question4, question5, question6, question7, question8, question9, question10);
    }

    public List<Question> getQuestions(){
        return this.questions;
    }

    public Question getRandomQuestion(){
        return this.questions.get((int) (Math.random() * this.questions.size()));
    }

}
