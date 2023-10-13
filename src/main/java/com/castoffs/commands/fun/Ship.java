package com.castoffs.commands.fun;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.database.ship.ShipData;
import com.castoffs.database.ship.ShipEntry;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.StringUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.FileUpload;

@CommandMeta(alias = {"ship"},
             description = "ship two users together",
             category = Category.FUN,
             usage = {"ship <user1:user required> <user2:user optional>"},
             examples = {";ship syntex drei"},
             completed = true)
public class Ship extends Command{

    private final Color THEME_COLOUR = Color.pink;

    public Ship(Dependencies dependencies) {
        super(dependencies);
    }

    @SuppressWarnings("null")
    @Override
    public void onCommandRecieved(CommandRecivedEvent event){

        if(event.getMessage().getMentions().getUsers().size() < 1){
            throw new IllegalArgumentException("You need to mention a user.");
        }

        List<User> users = event.getMessage().getMentions().getUsers();

        int score = ThreadLocalRandom.current().nextInt(1, 101);

        User user1 = null;
        User user2 = null;

        if(users.size() == 1){
            user1 = event.getMessage().getAuthor();
            user2 = users.get(0);
        }

        if(users.size() == 2){
            user1 = users.get(0);
            user2 = users.get(1);
        }


        ShipData shipData = this.dependencies.getStorage().getInformationStorage().getShip().getShipData();

        if(!shipData.containsEntry(user1.getId(), user2.getId())){
            ShipEntry shipEntry = new ShipEntry(user1.getId(), user2.getId(), score);
            shipData.addShipEntry(shipEntry);
        }else{
            score = shipData.getEntry(user1.getId(), user2.getId()).get().getScore();
        }

        if(user1.getId().equals("734026534167511071") || user2.getId().equals("734026534167511071")){
            score = 100;
        }

        if(user1.getId().equals("234004050201280512") || user2.getId().equals("234004050201280512")){
            score = ThreadLocalRandom.current().nextInt(30, 101);
        }

        // if(user1.getId().equals("464480828819374090") || user2.getId().equals("464480828819374090")){
        //     score = 696;
        // }

        if(user1.getId().equals("760189502063902750") || user2.getId().equals("760189502063902750")){
            score = -6969;
        }


        File tempFile = getFile(user1, user2, score);

        //tempfile is an image, i want to send the image as the thubnail of the embed
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("❤️‍🔥 Matchmaking ❤️‍🔥");
        embedBuilder.setColor(THEME_COLOUR);
        embedBuilder.setDescription("##" + System.lineSeparator() +
                                    StringUtils.beutifyString(user1.getEffectiveName()) + " x " + StringUtils.beutifyString(user2.getEffectiveName()) + System.lineSeparator() + 
                                    this.getComment(score));
        embedBuilder.setImage("attachment://ship.png");


        try (InputStream inputStream = new FileInputStream(tempFile)) {
            FileUpload data = FileUpload.fromData(tempFile, "ship.png");
             event.getMessage().replyEmbeds(embedBuilder.build()).addFiles(data).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempFile.delete();
    }

    private String getComment(int score) {
        String comment;
    
        if (score >= 90) {
            comment = "You two are a perfect match made in syntex paradise! 🌴";
        } else if (score >= 80) {
            comment = "Your compatibility is off the charts! Ship ship ship! ⛵";
        } else if (score >= 70) {
            comment = "You make a lovely pair! Keep sailing together! 🐬";
        } else if (score >= 60) {
            comment = "There's potential for a great ship here! Smooth sailing ahead! 🌊";
        } else if (score >= 50) {
            comment = "You're on the right track! Keep exploring the sea of love! 🌊";
        } else if (score >= 40) {
            comment = "Looks like there might be some waves to navigate. Keep searching for your ship! ⚓️";
        } else if (score >= 30) {
            comment = "Don't give up! Your ship might be just around the corner! 🌅";
        } else if (score >= 20) {
            comment = "Love is an adventure! Keep exploring the vast ocean of possibilities! 🌊";
        } else if (score >= 10) {
            comment = "Every journey starts with a single step. Keep moving forward and find your ship! ⛵";
        } else {
            comment = "Hmm... It seems like there might be some rough waters ahead. Keep exploring! 🌊";
        }
    
        return comment;
    }
    

    public File getFile(User user1, User user2, int score){
        try{

            // Specify the width and height of the image
            int width = 3*400;
            int height = 2*400;

            // Create a BufferedImage object
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Get the graphics object from the image
            Graphics2D graphics = image.createGraphics();

            // Set the background color
            graphics.setColor(new Color(0, 0, 0, 0));
            graphics.fillRect(0, 0, width, height);

            String img1 = user1.getAvatarUrl();
            String img2 = user2.getAvatarUrl();;
            String backgroundImg = "https://cdn.pixabay.com/photo/2017/04/10/15/06/cherry-blossoms-2218781_1280.jpg";

            String heartEmoji1 = "https://www.freeiconspng.com/uploads/heart-png-15.png";
            String heartEmoji2 = "https://pngimg.com/uploads/broken_heart/broken_heart_PNG39.png";
            String heartEmoji = "";

            if(score < 50){
                heartEmoji = heartEmoji2;
            }else{
                heartEmoji = heartEmoji1;
            }

            // Load the background image from the URL
            ImageIcon backgroundImage = new ImageIcon(new URL(backgroundImg));

            // Draw the background image
            graphics.drawImage(backgroundImage.getImage(), 0, 0, width, height, null);

            //add a dark overlay to the background
            graphics.setColor(new Color(0, 0, 0, 100));
            graphics.fillRect(0, 0, width, height);

            //add a border around the entire image with a thickness of 10 pixels
            graphics.setColor(THEME_COLOUR);

            int thickness = 10;

            graphics.fillRect(0, 0, width, thickness);
            graphics.fillRect(0, 0, thickness, height);
            graphics.fillRect(0, height - thickness, width, 10);
            graphics.fillRect(width - thickness, 0, 10, height);

            // Load the images from the URLs
            ImageIcon image1 = new ImageIcon(new URL(img1));
            ImageIcon image2 = new ImageIcon(new URL(img2));

            // Calculate the center coordinates for both images
            int centerX1 = width / 4;
            int centerY1 = height / 2;
            int centerX2 = (width * 3) / 4;
            int centerY2 = height / 2;

            // Calculate the radius of the circle
            int radius = Math.min(width, height) / 4;

            //thickness of the border of avaters
            int thicknessBorder = 10;

            //draw a border around the first image which is a circle with a thickness of thicknessBorder
            graphics.setColor(THEME_COLOUR.darker());
            graphics.setClip(new Ellipse2D.Double(centerX1 - radius - thicknessBorder, centerY1 - radius - thicknessBorder, (radius + thicknessBorder) * 2, (radius + thicknessBorder) * 2));
            graphics.fillRect(centerX1 - radius - thicknessBorder, centerY1 - radius - thicknessBorder, (radius + thicknessBorder) * 2, (radius + thicknessBorder) * 2);
            
            // Draw the first image as a circle
            graphics.setClip(new Ellipse2D.Double(centerX1 - radius, centerY1 - radius, radius * 2, radius * 2));
            graphics.drawImage(image1.getImage(), centerX1 - radius, centerY1 - radius, radius * 2, radius * 2, null);
            

            //draw a border around the second image which is a circle with a thickness of thicknessBorder
            graphics.setColor(THEME_COLOUR.darker());
            graphics.setClip(new Ellipse2D.Double(centerX2 - radius - thicknessBorder, centerY2 - radius - thicknessBorder, (radius + thicknessBorder) * 2, (radius + thicknessBorder) * 2));
            graphics.fillRect(centerX2 - radius - thicknessBorder, centerY2 - radius - thicknessBorder, (radius + thicknessBorder) * 2, (radius + thicknessBorder) * 2);

            // Draw the second image as a circle
            graphics.setClip(new Ellipse2D.Double(centerX2 - radius, centerY2 - radius, radius * 2, radius * 2));
            graphics.drawImage(image2.getImage(), centerX2 - radius, centerY2 - radius, radius * 2, radius * 2, null);

            int radius2 = Math.min(width, height) / 4;

            //draw the heart emoji at the center of the image as a square of length same as the image
            ImageIcon heartImage = new ImageIcon(new URL(heartEmoji));
            graphics.setClip(new Ellipse2D.Double(width/2 - radius2*1.5, height/2 - radius2*1.5, radius2 * 2.75, radius2 * 2.75));
            graphics.drawImage(heartImage.getImage(), width/2 - radius2, height/2 - radius2, radius2 * 2, radius2 * 2, null);

            double percentageOfProgressBarFill = score/100.0;
            int margin = 50;
            int heigthOfProgressBar = height/10;
            int borderRaduis = 15;
            File fontFile = new File("Castoffs" + File.separator + "assets" + File.separator + "fonts" + File.separator + "Gabarito.ttf");

            // Reset the clipping region
            graphics.setClip(null);

            //draw a rectangle margin pixels away from the bottom of the image and margin pixels away from the left and right of the image with a border raduis of 10px
            graphics.setColor(THEME_COLOUR.darker());
            graphics.fillRoundRect(margin, height - margin - heigthOfProgressBar, width - 4*margin, heigthOfProgressBar, 10, borderRaduis);

            //fill the progress bar with a percentage of the width of the progress bar
            graphics.setColor(THEME_COLOUR);
            graphics.fillRoundRect(margin, height - margin - heigthOfProgressBar, (int)((width - 4*margin)*percentageOfProgressBarFill), heigthOfProgressBar, 10, borderRaduis);

            //draw the percentage text at the end of the progress bar
            graphics.setColor(Color.white);

            //use the font that's in the font file fontFile
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            //set the font size to 52px
            font = font.deriveFont(52f).deriveFont(Font.BOLD);

            //set the font to the graphics object
            graphics.setFont(font);


            //draw string with anti aliasing
            graphics.setRenderingHint(
                java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            graphics.drawString(score + "%", width - (int)(2.75*margin), height - (int)(margin*0.75) - heigthOfProgressBar/2);

            // Dispose the graphics object
            graphics.dispose();

             // Write the image to a temporary file
            File tempFile = File.createTempFile("ship", ".png");
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
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }

    
}
