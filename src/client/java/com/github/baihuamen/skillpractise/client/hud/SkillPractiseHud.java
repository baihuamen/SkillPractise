package com.github.baihuamen.skillpractise.client.hud;

import com.github.baihuamen.skillpractise.client.keystroke.KeyStroke;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;

public class SkillPractiseHud {

    private final MinecraftClient client;
    private final TextRenderer textRenderer;
    private final KeyChartHud keyChartHud;

    public MultiValueDebugSampleLogImpl keyChartLog = new MultiValueDebugSampleLogImpl(1);

    public SkillPractiseHud() {
        this.client = MinecraftClient.getInstance();
        this.textRenderer = client.textRenderer;
        this.keyChartHud = new KeyChartHud(textRenderer, keyChartLog);
        for(int i = 0; i < 240; i++){
            keyChartLog.push(10);
        }
    }

    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
                layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS,
                        Identifier.of("skillpractise", "skillpractise/skillpractisehud"),
                        (context, tickCounter) -> {
                            render(context);
        }));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if(client.player == null) return;

            if(KeyStroke.updateMap.get("s") == true){
              keyChartLog.push(KeyStroke.sInterval);
             KeyStroke.sInterval = 0;
             KeyStroke.updateMap.put("s",false);
         }
        });


    }


    private void render(DrawContext drawContext) {
            keyChartHud.render(drawContext, 0, 100);
    }
}
