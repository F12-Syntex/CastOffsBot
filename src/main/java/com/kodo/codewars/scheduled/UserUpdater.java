package com.kodo.codewars.scheduled;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import com.kodo.database.users.UserConfiguration;
import com.kodo.database.users.UserStorage;
import com.kodo.handler.Dependencies;
import com.kodo.utils.TimeUtils;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class UserUpdater implements EventListener{

    private final int THREAD_COUNT = 1;
    private ExecutorService executorService;
    private Dependencies dependencies;

    private long lastUpdate = System.currentTimeMillis();

    private final long UPDATE_INTERVAL = 5;
    private final TimeUnit UPDATE_INTERVAL_UNIT = TimeUnit.MINUTES;

    public UserUpdater(Dependencies dependencies) {
        this.executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        this.dependencies = dependencies;

        this.dependencies.getDiscord().addEventListener(this);
    }

    private boolean shouldUpdate(){
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastUpdate;

        return difference >= UPDATE_INTERVAL_UNIT.toMillis(UPDATE_INTERVAL);
    }

    public String getTimeTillUpdate(){
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastUpdate;

        long timeLeft = UPDATE_INTERVAL_UNIT.toMillis(UPDATE_INTERVAL) - difference;

        return TimeUtils.formatDuration(timeLeft / 1000);
    }
    
    @Override
    public void onEvent(@Nonnull GenericEvent event) {

        if(!shouldUpdate()){
            return;
        }

        UserStorage storage = this.dependencies.getStorage().getUserStorage();
        
        Collection<UserConfiguration> users = storage.getUsers();

        for(UserConfiguration user : users){
            executorService.submit(() -> {
                try {
                    user.update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
