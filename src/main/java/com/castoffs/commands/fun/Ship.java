package com.castoffs.commands.fun;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.utils.FileUpload;

@CommandMeta(alias = {"ship"},
             description = "ship two users together",
             category = Category.FUN,
             usage = {"ship <user1:user required> <user2:user optional>"},
             examples = {";ship syntex drei"},
             completed = false)
public class Ship extends Command{

    public Ship(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event){


        

        try{

            if(event.getMessage().getMentions().getUsers().size() < 1){
                throw new IllegalArgumentException("You need to mention a user.");
            }

            // Specify the width and height of the image
            int width = 1200;
            int height = 800;

            // Create a BufferedImage object
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Get the graphics object from the image
            Graphics2D graphics = image.createGraphics();

            // Set the background color
            graphics.setColor(new Color(0, 0, 0, 0));
            graphics.fillRect(0, 0, width, height);

            String img1 = event.getMessage().getMentions().getUsers().get(0).getAvatarUrl();
            String img2 = "";
            String backgroundImg = "https://img.freepik.com/free-vector/black-technology-background_23-2149209062.jpg?w=1380&t=st=1696794403~exp=1696795003~hmac=4d1548863e1244b994e240c55eb756839f229a01ec9e3a05a9ca88c1697470f5";
            String heartEmoji = "";

            // Load the background image from the URL
            ImageIcon backgroundImage = new ImageIcon(new URL(backgroundImg));

            // Draw the background image
            graphics.drawImage(backgroundImage.getImage(), 0, 0, width, height, null);

            if(event.getMessage().getMentions().getUsers().size() == 2){
                img2 = event.getMessage().getMentions().getUsers().get(1).getAvatarUrl();
            }else{
                img2 = img1;
            }

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

            // Draw the first image as a circle
            graphics.setClip(new Ellipse2D.Double(centerX1 - radius, centerY1 - radius, radius * 2, radius * 2));
            graphics.drawImage(image1.getImage(), centerX1 - radius, centerY1 - radius, radius * 2, radius * 2, null);

            // Draw the second image as a circle
            graphics.setClip(new Ellipse2D.Double(centerX2 - radius, centerY2 - radius, radius * 2, radius * 2));
            graphics.drawImage(image2.getImage(), centerX2 - radius, centerY2 - radius, radius * 2, radius * 2, null);

            // Dispose the graphics object
            graphics.dispose();

             // Write the image to a temporary file
            File tempFile = File.createTempFile("image", ".png");
            ImageIO.write(image, "PNG", tempFile);

            if(tempFile == null){
                return;
            }

            try (InputStream inputStream = new FileInputStream(tempFile)) {
                FileUpload data = FileUpload.fromData(tempFile, "ship.png");
                event.getMessage().replyFiles(data).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }


            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommands();
    }

    
}
