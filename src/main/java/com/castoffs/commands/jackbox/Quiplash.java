package com.castoffs.commands.jackbox;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.castoffs.commands.Category;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.FileUpload;

@CommandMeta(alias = {"quiplash"},
             description = "JackBox minigame - Quiplash",
             category = Category.JACKBOX,
             usage = {"quiplash <game code:text required> <start:text optional>",
                      "quiplash <game code:text required> <stop:text optional>"},
             cooldown = 60 * 1000,
             examples = {"quiplash"})
public class Quiplash extends JackBoxCommands{

    private List<Question> questions;

    public Quiplash(Dependencies dependencies) {
        super(dependencies, "Quiplash");
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        Lobby queuedLobby = new Lobby(this, event.getGuildChannel().asTextChannel(), event.getAuthor(), 8, 1);
        queuedLobby.queue();

        queuedLobby.onLobbyReady(this::showIntroduction);
    }

    public void showIntroduction(Lobby lobby){
        Introduction introduction = new Introduction(lobby);
        introduction.start();

        introduction.setOnReady(this::startGame);
    }

    
    private File generateWaitingImage(Lobby lobby){
        try{
            // Specify the width and height of the image
            int width = (int)(1920 / 2);
            int height = (int)(1080 / 2);

            // Create a BufferedImage object
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Get the graphics object from the image
            Graphics2D graphics = image.createGraphics();

            // Set the background color
            graphics.setColor(Color.pink);
            graphics.fillRect(0, 0, width, height);

            //render background
            File background = lobby.getAssets().getBackground();
            if(background != null){
                BufferedImage backgroundImg = ImageIO.read(background);
                graphics.drawImage(backgroundImg, 0, 0, width, height, null);
            }

            this.renderWaitingScreen(lobby);

            // Dispose the graphics object
            graphics.dispose();

             // Write the image to a temporary file
            File tempFile = File.createTempFile(lobby.getGameCode(), ".png");
            ImageIO.write(image, "PNG", tempFile);

            if(tempFile == null){
                return null;
            }

            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void renderWaitingScreen(Lobby lobby){


    }

    @SuppressWarnings("null")
    private void loadWaitingScreen(Lobby lobby) {
        Message message = lobby.getLobbyMessage();
        File image = this.generateWaitingImage(lobby);
    
        try (InputStream inputStream = new FileInputStream(image)) {
            FileUpload data = FileUpload.fromData(image, lobby.getGameCode() + ".png");
    
            // Remove existing content from the message
            message.editMessageAttachments(data).setComponents().setEmbeds().queue();
    
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(Lobby lobby){
        this.sendQuestion(lobby);
        
        this.loadWaitingScreen(lobby);
    }

    public void sendQuestion(Lobby lobby){
        
        //group the players into pairs
        Collections.shuffle(lobby.getPlayers());
        List<List<User>> pairs = lobby.getPlayers().stream().collect(Collectors.groupingBy(s -> (lobby.getPlayers().indexOf(s) / 2))).values().stream().collect(Collectors.toList());

        //send each pair a question
        for(List<User> pair : pairs){

            for(User user : pair){
                // this.sendQuestionToUser(user, question);
            }
        }
    }

    private void sendQuestionToUser(User user, Question question){

    }   
    
}
