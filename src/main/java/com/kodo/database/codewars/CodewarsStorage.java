package com.kodo.database.codewars;

import java.io.File;

import com.kodo.database.Storage;
import com.kodo.handler.Dependencies;
import com.kodo.utils.DirectoryUtils;

public class CodewarsStorage extends Storage{

    private CodewarsChallenges challenges;

    public CodewarsStorage(File directory, Dependencies dependencies) {
        super(directory, dependencies);
    }

    @Override
    public void load() {
        this.challenges = new CodewarsChallenges(DirectoryUtils.directoryBuilder(directory, "challenges.json"), dependencies);
        this.challenges.startup();
    }

    public CodewarsChallenges getChallenges() {
        return challenges;
    }
    
}
