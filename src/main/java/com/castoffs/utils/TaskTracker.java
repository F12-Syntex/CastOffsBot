package com.castoffs.utils;

/**
 * The `TaskTracker` class is a utility class designed to track the progress of tasks
 * and estimate the time remaining for their completion. It provides methods for incrementing
 * completed tasks, calculating the estimated time remaining, and presenting the remaining time
 * in a user-friendly format.
 */
public class TaskTracker {

    private int totalTasks;         // Total number of tasks to be completed
    private int completedTasks;     // Number of tasks that have been completed
    private long startTime;         // Timestamp when tracking started

    /**
     * Initialize a new `TaskTracker` with the total number of tasks to be tracked.
     *
     * @param totalTasks The total number of tasks to be tracked.
     */
    public TaskTracker(int totalTasks) {
        this.totalTasks = totalTasks;
        this.completedTasks = 0;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Increment the count of completed tasks.
     */
    public synchronized void incrementCompletedTasks() {
        this.completedTasks++;
    }

    /**
     * Get the count of completed tasks.
     *
     * @return The number of tasks that have been completed.
     */
    public synchronized int getCompletedTasks() {
        return this.completedTasks;
    }

    /**
     * Get the total number of tasks to be completed.
     *
     * @return The total number of tasks.
     */
    public synchronized int getTotalTasks() {
        return this.totalTasks;
    }

    /**
     * Get the timestamp when tracking started.
     *
     * @return The start time of tracking in milliseconds.
     */
    public synchronized long getStartTime() {
        return this.startTime;
    }

    /**
     * Get the elapsed time since tracking started.
     *
     * @return The elapsed time in milliseconds.
     */
    public synchronized long getElapsedTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    /**
     * Calculate the percentage of completed tasks.
     *
     * @return The percentage of completed tasks as a double value between 0 and 1.
     */
    public synchronized double getPercentageComplete() {
        return ((double) this.completedTasks / (double) this.totalTasks);
    }

    /**
     * Calculate the percentage of remaining tasks.
     *
     * @return The percentage of remaining tasks as a double value between 0 and 1.
     */
    public synchronized double getPercentageRemaining() {
        return 1 - getPercentageComplete();
    }

    /**
     * Calculate the estimated time remaining for task completion.
     * This estimation is based on the average time per completed task.
     *
     * @return The estimated time remaining in milliseconds.
     */
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

    /**
     * Get the estimated time remaining as a formatted string.
     *
     * @return A user-friendly string representation of the estimated time remaining.
     */
    public synchronized String getEstimatedTimeRemainingAsPrettyString() {
        return TimeUtils.formatDuration(this.getEstimatedTimeRemaining() / 1000);
    }
}
