package com.github.baihuamen.skillpractise;

import com.github.baihuamen.skillpractise.command.ServerCommandManager;
import com.github.baihuamen.skillpractise.event.ServerEventManager;
import net.fabricmc.api.ModInitializer;

public class Skillpractise implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerEventManager serverEventManager = new ServerEventManager();
        ServerCommandManager serverCommandManager = new ServerCommandManager();
    }
}
