package com.castoffs.utils;

import java.io.File;

/**
 * The `DirectoryUtils` class provides a utility method for constructing a directory path within
 * a file system. It facilitates the creation of a `File` object representing a directory by
 * combining a root directory and an array of subdirectory names.
 */
public class DirectoryUtils {

    /**
     * Build a directory path using the provided root directory and an array of subdirectory names.
     *
     * @param root The root directory within which the subdirectories will be created.
     * @param dir  An array of subdirectory names to be appended to the root.
     * @return A `File` object representing the constructed directory path.
     */
    public static File directoryBuilder(File root, String... dir) {
        StringBuilder builder = new StringBuilder();

        // Concatenate the subdirectory names with the appropriate file separator
        for (String s : dir) {
            builder.append(s).append(File.separator);
        }

        // Create a new `File` object representing the constructed directory path
        return new File(root, builder.toString());
    }
}
