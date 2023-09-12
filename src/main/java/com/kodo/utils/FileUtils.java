package com.kodo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

    /*
     * reads a file and returns the content as a string
     * if the there is an error reading the file, the return value will be an empty string
     * optmised for large files
     * @param filePath the path to the file
     */
    public static String readFile(File filePath) {
        StringBuilder content = new StringBuilder();
        char[] buffer = new char[4096]; // Use a smaller buffer size
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                content.append(buffer, 0, charsRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Return empty string on error
        }
    
        return content.toString();
    }
    

    /*
     * writes a string to a file
     * optmised for large files
     * @param filePath the path to the file
     * @param content the content to write to the file
     */
    public static void writeFile(File filePath, String content) {
        int chunkSize = 4096; // Specify the chunk size
        byte[] bytes = content.getBytes();
        int offset = 0;

        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            while (offset < bytes.length) {
                int length = Math.min(chunkSize, bytes.length - offset);
                outputStream.write(bytes, offset, length);
                offset += length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
