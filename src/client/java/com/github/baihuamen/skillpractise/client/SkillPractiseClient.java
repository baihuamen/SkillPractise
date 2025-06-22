package com.github.baihuamen.skillpractise.client;

import com.github.baihuamen.skillpractise.client.hud.SkillPractiseHud;
import com.github.baihuamen.skillpractise.client.keystroke.KeyStroke;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SkillPractiseClient implements ClientModInitializer {
    private final SkillPractiseHud skillPractiseHud;
    private final KeyStroke keyStroke;


    public SkillPractiseClient() {
        this.skillPractiseHud = new SkillPractiseHud();
        this.keyStroke = new KeyStroke();
    }
    @Override
    public void onInitializeClient() {
        skillPractiseHud.register();
        KeyStroke.register();
    }
}
