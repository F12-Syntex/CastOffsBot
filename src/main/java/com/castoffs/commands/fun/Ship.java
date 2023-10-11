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
import com.castoffs.handler.Dependencies;

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

        User user1 = users.get(0);
        User user2 = null;

        if(users.size() > 1){
            user2 = users.get(1);
        }else{
            user2 = user1;
        }

        File tempFile = getFile(user1, user2, score);

        try (InputStream inputStream = new FileInputStream(tempFile)) {
            FileUpload data = FileUpload.fromData(tempFile, "ship.png");
            event.getMessage().replyFiles(data).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempFile.delete();
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
            String heartEmoji = "https://www.nicepng.com/png/full/174-1746878_ios-emoji-emoji-iphone-ios-heart-hearts-spin.png";

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
            graphics.setFont(new Font("Arial", Font.BOLD, 52));
            graphics.drawString(score + "%", width - (int)(2.5*margin), height - (int)(margin*0.75) - heigthOfProgressBar/2);

            // Dispose the graphics object
            graphics.dispose();

             // Write the image to a temporary file
            File tempFile = File.createTempFile("image", ".png");
            ImageIO.write(image, "PNG", tempFile);

            if(tempFile == null){
                return null;
            }

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }

    
}
