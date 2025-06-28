package com.github.baihuamen.skillpractise.client.features;

import com.github.baihuamen.skillpractise.client.screen.ScreenConfig;
import com.github.baihuamen.skillpractise.client.event.Event;
import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.ClientStartedEvent;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager extends EventListener {


    private final Map<Class<? extends ScreenConfig>,ScreenConfig> screensMap= new HashMap<>();
    private final Class<? extends Event> registerScreens = registerEvent(ClientStartedEvent.class,() -> {

    });


    public ScreenManager() {
        screensMap.put(TestScreen.class,new TestScreen());

        screensMap.forEach((screenConfigClass, screenConfig) -> {
            screenConfig.register();
        });
    }
}
