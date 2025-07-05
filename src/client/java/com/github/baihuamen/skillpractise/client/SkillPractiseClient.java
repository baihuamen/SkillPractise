package com.github.baihuamen.skillpractise.client;

import com.github.baihuamen.skillpractise.client.config.ConfigManager;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.OnInitializeClientEvent;
import com.github.baihuamen.skillpractise.client.event.EventManager;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.keycharthud.KeyChartInGameHud;
import com.github.baihuamen.skillpractise.client.features.mainconfigscreen.ScreenManager;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.BridgeSpeedCounter;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStroke;
import net.fabricmc.api.ClientModInitializer;
import org.jetbrains.annotations.Nullable;

public class SkillPractiseClient implements ClientModInitializer {

    @Nullable
    private KeyChartInGameHud keyChartInGameHud;
    private final KeyStroke keyStroke;

    public SkillPractiseClient() {
        this.keyChartInGameHud = null;
        this.keyStroke = new KeyStroke();
    }

    @Override
    public void onInitializeClient() {
        keyChartInGameHud = new KeyChartInGameHud();
        keyChartInGameHud.register();
        KeyStroke.register();
        BridgeSpeedCounter bridgeSpeedCounter = new BridgeSpeedCounter();
        ConfigManager.register();
        ScreenManager screenManager = new ScreenManager();
        ScreenManager.register();;
        EventManager eventManager2 = new EventManager();
        eventManager2.INSTANCE.callEvent(new OnInitializeClientEvent());
    }
}
