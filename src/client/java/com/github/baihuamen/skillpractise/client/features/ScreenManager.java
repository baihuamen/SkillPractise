package com.github.baihuamen.skillpractise.client.features;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.hud.BridgeSpeedCounter;
import com.github.baihuamen.skillpractise.client.screen.ScreenConfig;
import com.github.baihuamen.skillpractise.client.screen.SkillPractiseScreen;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager extends EventListener {

    private final Map<Class<? extends ScreenConfig>, ScreenConfig> screensMap = new HashMap<>();

    public ScreenManager() {
        screensMap.put(SkillPractiseScreen.class, new SkillPractiseScreen());
        screensMap.put(BridgeSpeedCounter.class, new BridgeSpeedCounter());
        screensMap.forEach((screenConfigClass, screenConfig) -> {
            screenConfig.register();
        });
    }
}
