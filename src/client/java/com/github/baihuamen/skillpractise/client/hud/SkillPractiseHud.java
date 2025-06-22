package com.github.baihuamen.skillpractise.client.hud;

import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStroke;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStrokeType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;

@Environment(EnvType.CLIENT)
public class SkillPractiseHud {
    private TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    private KeyChartHud keyChartHud;

    public MultiValueDebugSampleLogImpl keyChartLog = new MultiValueDebugSampleLogImpl(1);

    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
                layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS,
                        Identifier.of("skillpractise", "skillpractise/skillpractisehud"),
                        (context, tickCounter) -> {
                            render(context);
                        }));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) return;

            if (KeyStroke.updateMap.get(KeyStrokeType.S).isRelease) {
                keyChartLog.push(KeyStroke.updateMap.get(KeyStrokeType.S).interval);
                KeyStroke.updateMap.get(KeyStrokeType.S).interval = 0;
                KeyStroke.updateMap.get(KeyStrokeType.S).isRelease = false;
            }
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            this.keyChartHud = new KeyChartHud(textRenderer, keyChartLog);
            this.keyChartHud = new KeyChartHud(client.textRenderer, keyChartLog);

            for (int i = 0; i < 240; i++) {
                keyChartLog.push(0);
            }
        });
    }

    private void render(DrawContext drawContext) {
        this.keyChartHud.render(drawContext, 0, 100);
    }
}
