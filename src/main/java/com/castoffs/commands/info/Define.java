package com.castoffs.commands.info;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.embeds.EmbedMaker;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.HtmlUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

@CommandMeta(alias = {"define"},
             description = "urbun dictionary definition of something.",
             category = Category.INFO,
             usage = {"define <user:text required>"},
             examples = {"define sacrosanct"})
public class Define extends Command{

    public Define(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        if(!event.isArgumentValidAtIndex(1)){
            throw new IllegalArgumentException("Please provide a word to define.");
        }

        EmbedMaker.runAsyncTask(event.getMessage(), event.getAuthor(), (message) -> {

            try{

                String word = event.getArgumentAtIndexAsString(1);

                String url = "https://unofficialurbandictionaryapi.com/api/search?term=" + word + "&strict=false&matchCase=false&limit=1&page=1&multiPage=false";
                String html = HtmlUtils.getHtml(url);

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(html, JsonObject.class);

                String term = jsonObject.get("term").getAsString();
                String directUrl = "https://www.urbandictionary.com/define.php?term=" + term;

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(jsonObject.get("term").getAsString(), directUrl);
                builder.setColor(Color.pink);
                
                JsonObject data = jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject();

                String definition = data.get("meaning").getAsString();
                String examples = data.get("example").getAsString();

                if(examples != null){
                    builder.addField("Examples", "`" + examples + "`", true);
                }


                builder.setDescription(definition);

                message.editMessageEmbeds(builder.build()).queue();

            }catch(Exception e){
                 EmbedBuilder error = EmbedMaker.ERROR(event.getAuthor(), "Couldn't find the definition for your query" , e.getLocalizedMessage());
                 message.editMessageEmbeds(error.build()).queue();
            }

        });

    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommands();
    }
    
}
