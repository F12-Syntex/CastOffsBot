package com.castoffs.commands.jackbox;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.castoffs.bot.Castoffs;
import com.castoffs.utils.ImageUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.MessageEditAction;
import net.dv8tion.jda.api.utils.FileUpload;

public class Lobby extends ListenerAdapter{

    private TextChannel channel;
    private User owner;
    private final String gameCode;
    private JackBoxCommands jackbox;
    private JackBoxAssets assets;

    private List<User> players;

    private Message lobbyMessage;

    private final int MAX_PLAYERS;

    public Lobby(JackBoxCommands jackbox, TextChannel channel, User owner, int maxPlayers){
        this.channel = channel;
        this.owner = owner;
        this.gameCode = UUID.randomUUID().toString().substring(0, 6);
        this.players = new LinkedList<>();
        this.jackbox = jackbox;
        this.assets = new JackBoxAssets(jackbox);
        Castoffs.getInstance().getDiscord().addEventListener(this);

        this.MAX_PLAYERS = maxPlayers;
    }

    public void addPlayer(User player){
        this.players.add(player);
        this.renderLobby();
    }

    public void queue(){
        this.players.add(owner);
        this.renderLobby();
    }

    private void renderLobby(){

        File lobbyImage = generateLobbyImage();

        if(lobbyImage == null){
            return;
        }

        File finalLobbyImage = lobbyImage;

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("JackBox - " + this.jackbox.getGameName());
        embed.setFooter("gamecode: " + this.gameCode + " | players: " + this.players.size() + "/" + MAX_PLAYERS, null);
        embed.setTimestamp(Instant.now());
        embed.setImage("attachment://" + gameCode + ".png");
        embed.setColor(Color.pink);

        for(User user : this.players){
            embed.appendDescription(user.getAsMention() + System.lineSeparator());
        }
        
        try (InputStream inputStream = new FileInputStream(finalLobbyImage)) {
            
            
            FileUpload data = FileUpload.fromData(finalLobbyImage, gameCode + ".png");

            Button[] buttons = {
                Button.of(ButtonStyle.PRIMARY, this.gameCode + ":join", "Join", Emoji.fromUnicode("U+25B6")),
                Button.of(ButtonStyle.SECONDARY, this.gameCode + ":start", "Start")
            };

            if(this.lobbyMessage == null){
                MessageCreateAction action = this.channel.sendMessageEmbeds(embed.build());
                action.addFiles(data);
                action.addActionRow(buttons);
                this.lobbyMessage = action.complete();
            }else{
                MessageEditAction action = this.lobbyMessage.editMessageEmbeds(embed.build());
                action.setFiles(data);
                action.setActionRow(buttons);
                this.lobbyMessage = action.complete();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        lobbyImage.delete();
    }

    private void renderPlayers(Graphics2D graphics, int width, int height) {

        // Create a rectangle with an offset of 10px and a width of width/2. This will be where the players are rendered
        int boundsX = 10;
        int boundsY = 10;
    
        // Render the players each inside a subbox of width and 200px for the height
        int yOffset = boundsY; // Initial y-offset
        int imageHeight = 120; // Height of each image
    
        int boundsWidth = imageHeight;

        int thickness = 2;

        //render a box for each remaining player
        //for every 4 elements add a new row
        for(int i = 0; i < MAX_PLAYERS; i++){
            
            graphics.setColor(new Color(0, 0, 0, 0.5f));
            graphics.fillRect(boundsX, yOffset, boundsWidth, imageHeight);

            //draw the user if exists
            if(i < players.size()){
                Image userImage = ImageUtils.toImage(players.get(i).getAvatarUrl());
        
                if (userImage == null) {
                    continue;
                }
        
                graphics.drawImage(userImage, boundsX, yOffset, imageHeight, imageHeight, null);

                //draw border around image with a thickness of THICKNESS
                graphics.setColor(Color.pink);
                graphics.fillRect(boundsX, yOffset, boundsWidth, thickness); //top
                graphics.fillRect(boundsX, yOffset, thickness, imageHeight); //left
                graphics.fillRect(boundsX, yOffset + imageHeight - thickness, boundsWidth, thickness); //bottom
                graphics.fillRect(boundsX + boundsWidth - thickness, yOffset, thickness, imageHeight); //right
                

            }else{
                //render a plus sign with a thikness of THICKNESS and a size of half the image height
                int plusSize = imageHeight / 2;
                int plusThickness = thickness * 2;

                graphics.setColor(new Color(1, 1, 1, 0.2f));
                graphics.fillRect(boundsX + (boundsWidth / 2) - (plusThickness / 2), yOffset + (imageHeight / 2) - (plusSize / 2), plusThickness, plusSize);
                graphics.fillRect(boundsX + (boundsWidth / 2) - (plusSize / 2), yOffset + (imageHeight / 2) - (plusThickness / 2), plusSize, plusThickness);
            }

            if(i == 3){
                yOffset = boundsY;
                boundsX += boundsWidth + boundsX;
            } else {
                yOffset += imageHeight + boundsY;
            }

        }

    }   
    
    

    private File generateLobbyImage(){
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
            File background = this.assets.getBackground();
            System.out.println(background + " " + background.exists());
            if(background != null){
                BufferedImage backgroundImg = ImageIO.read(background);
                graphics.drawImage(backgroundImg, 0, 0, width, height, null);
            }

            this.renderPlayers(graphics, width, height);

            // Dispose the graphics object
            graphics.dispose();

             // Write the image to a temporary file
            File tempFile = File.createTempFile(gameCode, ".png");
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

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event){

        String componentId = event.getComponentId();

        System.out.println(componentId);

        if(!componentId.contains(":")){
            return;
        }

        String[] id = componentId.split(":");
        String gameCode = id[0];
        String action = id[1];

        if(!gameCode.equals(this.gameCode)){
            return;
        }

        if(action.equals("join")){

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("JackBox - " + this.jackbox.getGameName());
            embed.setTimestamp(Instant.now());


            if(this.players.size() >= MAX_PLAYERS){
                embed.setColor(Color.red);
                embed.setDescription("Lobby is full!");
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                return;
            }

            if(this.players.contains(event.getUser())){
                embed.setColor(Color.red);
                embed.setDescription("You are already in the lobby!");
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                return;
            }

            this.addPlayer(event.getUser());
            embed.setColor(Color.green);
            embed.setDescription("You have joined the lobby!");
            embed.setFooter("gamecode: " + this.gameCode + " | players: " + this.players.size() + "/" + MAX_PLAYERS, null);
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
        }

    }


    
}
