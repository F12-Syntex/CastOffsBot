package com.kodo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    /*
     * reads a file and returns the content as a string
     * if the there is an error reading the file, the return value will be an empty string
     * @param filePath the path to the file
     */
    public static String readFile(File filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Return empty string on error
        }

        return content.toString();
    }

    /*
     * writes a string to a file
     * @param filePath the path to the file
     * @param content the content to write to the file
     */
    public static void writeFile(File filePath, String content) {
        try {
            java.nio.file.Files.write(filePath.toPath(), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
