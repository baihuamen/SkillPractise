package com.github.baihuamen.skillpractise.client.utils.keystroke;

public class KeyState {
    public boolean isRelease;
    public int interval;

    public KeyState(boolean isRelease, int interval){
        this.isRelease = isRelease;
        this.interval = interval;
    }
    public void setReleased(boolean isReleased){
        this.isRelease = isReleased;
    }

    public void addInterval(){
        this.interval++;
    }
}
