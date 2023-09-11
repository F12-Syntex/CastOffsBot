package com.kodo.embeds;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class CodewarsChallengeEmbed {

    private PagedEmbed embedBuilder;
    private List<Button> buttons = new ArrayList<>();

    public CodewarsChallengeEmbed(PagedEmbed embedBuilder, List<Button> buttons){
        this.embedBuilder = embedBuilder;
        this.buttons = buttons;
    }

    public void addButtons(Button... buttons){
        for(Button i : buttons){
            this.buttons.add(i);
        }
    }

    public void addButtons(List<Button> buttons){
        this.buttons.addAll(buttons);
    }

    public PagedEmbed getEmbedBuilder(){
        return this.embedBuilder;
    }

    public List<Button> getButtons(){
        return this.buttons;
    }

    public void setEmbedBuilder(PagedEmbed embedBuilder){
        this.embedBuilder = embedBuilder;
    }

    public void setButtons(List<Button> buttons){
        this.buttons = buttons;
    }

    public void setButtons(Button... buttons){
        this.buttons.clear();
        for(Button i : buttons){
            this.buttons.add(i);
        }
    }
    
}
