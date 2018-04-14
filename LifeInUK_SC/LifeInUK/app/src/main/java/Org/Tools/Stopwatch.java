package Org.Tools;

/**
 * Created by Omer Habib on 12/13/2016.
 */

public class Stopwatch {
    private int second=0;
    private int minute=0;
    private long startTime = 0;
    private boolean running = false;
    private long currentTime = 0;

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public void pause() {
        this.running = false;
        currentTime = System.currentTimeMillis() - startTime;
    }
    public void resume() {
        this.running = true;
        this.startTime = System.currentTimeMillis() - currentTime;
    }

    //elaspsed time in milliseconds
    public long getElapsedTimeMili() {
        long elapsed = 0;
        if (running) {
            elapsed =((System.currentTimeMillis() - startTime)/100) % 1000 ;
        }
        return elapsed;
    }

    //elaspsed time in seconds
    public long getElapsedTimeSecs() {
        long elapsed = 0;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000) % 60;
        }
        return elapsed;
    }

    //elaspsed time in minutes
    public long getElapsedTimeMin() {
        long elapsed = 0;
        if (running) {
            elapsed = (((System.currentTimeMillis() - startTime) / 1000) / 60 ) % 60;
        }
        return elapsed;
    }

    //elaspsed time in hours
    public long getElapsedTimeHour() {
        long elapsed = 0;
        if (running) {
            elapsed = ((((System.currentTimeMillis() - startTime) / 1000) / 60 ) / 60);
        }
        return elapsed;
    }

    public String timeString() {
        String time= String.format("%1$02d : %2$02d : %3$02d",getElapsedTimeHour(),getElapsedTimeMin(),getElapsedTimeSecs());
        return time;
    }
}
