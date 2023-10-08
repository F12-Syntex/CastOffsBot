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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

@CommandMeta(alias = {"8ball"},
             description = "Run a peice of text on 8ball, returns a random response to the text",
             category = Category.FUN,
             usage = {"8ball <message:text optional>"},
             examples = {"8ball hi"})
public class EightBall extends Command{

    private final String[] responses = {
        "It is certain.",
        "It is decidedly so.",
        "Without a doubt.",
        "Yes - definitely.",
        "You may rely on it.",
        "As I see it, yes.",
        "Most likely.",
        "Outlook good.",
        "Yes.",
        "Signs point to yes.",
        "Reply hazy, try again.",
        "Ask again later.",
        "Better not tell you now.",
        "Cannot predict now.",
        "Concentrate and ask again.",
        "Don't count on it.",
        "My reply is no.",
        "My sources say no.",
        "Outlook not so good.",
        "Very doubtful."
    };

    public EightBall(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        EmbedBuilder EmbedBuilder = new EmbedBuilder();
        EmbedBuilder.setColor(Color.pink);
        EmbedBuilder.setDescription("#\n" + responses[ThreadLocalRandom.current().nextInt(responses.length)]);
        event.getChannel().sendMessageEmbeds(EmbedBuilder.build()).queue();
    }
    

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommands();
    }
    
}
