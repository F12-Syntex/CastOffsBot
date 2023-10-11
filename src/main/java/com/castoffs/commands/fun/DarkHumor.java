package com.castoffs.commands.fun;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.HtmlUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

@CommandMeta(alias = {"dh", "darkhumor"},
             description = "Dark Humor",
             category = Category.FUN,
             nsfw = true,
             usage = {"dh"},
             cooldown = 10 * 1000,
             examples = {"dh"})
public class DarkHumor extends Command{

    public DarkHumor(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        Message message = event.getMessage();
        this.sendJokeToMessage(message);
    }

    public void sendJokeToMessage(Message message){

        String api = "https://v2.jokeapi.dev/joke/Dark?blacklistFlags=religious&type=twopart&amount=10";
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(HtmlUtils.getHtml(api), JsonObject.class);
        JsonArray jokes = data.get("jokes").getAsJsonArray();

        JsonObject jsonObject = jokes.get(ThreadLocalRandom.current().nextInt(jokes.size())).getAsJsonObject();

        boolean error = data.get("error").getAsBoolean();

        if(error){
            this.sendJokeToMessage(message);
            return;
        }

        System.out.println(jokes);
        System.out.println(jsonObject);
        String setup = jsonObject.get("setup").getAsString();
        String delivery = jsonObject.get("delivery").getAsString();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.pink);

        builder.appendDescription("#\n" + setup);
        builder.appendDescription("\n##\n||" + delivery + "||");

        // CommandButton button = CommandButton.primary("Next joke", (interaction) -> {
        //     Message msg = interaction.getMessage();
        //     this.sendJokeToMessage(msg);
        //     interaction.deferEdit().queue();
        // });

       message.replyEmbeds(builder.build()).queue();
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
