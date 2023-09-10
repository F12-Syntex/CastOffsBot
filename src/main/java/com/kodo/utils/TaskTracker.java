package com.kodo.utils;

public class TaskTracker {

    private int totalTasks;
    private int completedTasks;

    private long startTime;

    public synchronized long getEstimatedTimeRemaining() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        
        if (completedTasks == 0) {
            return Long.MAX_VALUE;
        }
        
        double averageTimePerTask = (double) elapsedTime / completedTasks;
        double remainingTime = averageTimePerTask * (totalTasks - completedTasks);
        
        return (long) remainingTime;
    }

    public synchronized String getEstimatedTimeRemainingAsPrettyString() {
        return TimeUtils.formatDuration(this.getEstimatedTimeRemaining() / 1000);
    }
    

    public TaskTracker(int totalTasks) {
        this.totalTasks = totalTasks;
        this.completedTasks = 0;
        this.startTime = System.currentTimeMillis();
    }

    public synchronized void incrementCompletedTasks() {
        this.completedTasks++;
    }

    public synchronized int getCompletedTasks() {
        return this.completedTasks;
    }

    public synchronized int getTotalTasks() {
        return this.totalTasks;
    }

    public synchronized long getStartTime() {
        return this.startTime;
    }

    public synchronized long getElapsedTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public synchronized double getPercentageComplete() {
        return ((double) this.completedTasks / (double) this.totalTasks);
    }

    public synchronized double getPercentageRemaining() {
        return 1 - getPercentageComplete();
    }


    
}
