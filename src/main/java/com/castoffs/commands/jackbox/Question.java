package com.castoffs.commands.jackbox;

import java.util.List;

public class Question{
        public String question;
        public List<String> answer;

        public Question(String question, List<String> answer){
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion(){
            return this.question;
        }

        public List<String> getAnswer(){
            return this.answer;
        }
    }
