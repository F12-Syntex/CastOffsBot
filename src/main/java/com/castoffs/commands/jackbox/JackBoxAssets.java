package com.castoffs.commands.jackbox;

import java.io.File;

import com.castoffs.utils.FileUtils;

public class JackBoxAssets {

    private final JackBoxCommands jackbox;
    private final File gameFolder;

    public JackBoxAssets(JackBoxCommands jackbox){
        this.jackbox = jackbox;

        File parentFolder = new File("CastOffs");
        File assets = new File(parentFolder, "assets");
        File jackboxFolder = new File(assets, "JackBox");
        this.gameFolder = new File(jackboxFolder, this.jackbox.getGameName());
    }

    public File getBackground(){
        return FileUtils.locateFile(gameFolder, "background");
    }
    
}
