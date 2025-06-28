package com.github.baihuamen.skillpractise.client.config.utils;

public class BooleanValue {
    public boolean value;
    public String screenConfig;
    public String name;
    public BooleanValue(String screenConfig, String name, boolean value){
        this.value = value;
        this.screenConfig = screenConfig;
        this.name = name;
    }

    public void invert() {
        this.value = !this.value;
    }
}
