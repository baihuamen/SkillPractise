package com.github.baihuamen.skillpractise.client.hud;

import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStroke;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStrokeType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;

@Environment(EnvType.CLIENT)
public class SkillPractiseHud {
    private KeyChartHud forwardKeyChartHud;
    private KeyChartHud backKeyChartHud;
    private KeyChartHud leftKeyChartHud;
    private KeyChartHud rightKeyChartHud;

    public MultiValueDebugSampleLogImpl forwardKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public MultiValueDebugSampleLogImpl backKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public MultiValueDebugSampleLogImpl leftKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public MultiValueDebugSampleLogImpl rightKeyChartLog = new MultiValueDebugSampleLogImpl(1);

    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, Identifier.of("skillpractise", "skillpractise/skillpractisehud"), (context, tickCounter) -> {
            render(context);
        }));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) return;

            if (KeyStroke.updateMap.get(KeyStrokeType.W).isRelease) {
                forwardKeyChartLog.push(KeyStroke.updateMap.get(KeyStrokeType.W).interval);
                KeyStroke.updateMap.get(KeyStrokeType.W).interval = 0;
                KeyStroke.updateMap.get(KeyStrokeType.W).isRelease = false;
            }
            if (KeyStroke.updateMap.get(KeyStrokeType.S).isRelease) {
                backKeyChartLog.push(KeyStroke.updateMap.get(KeyStrokeType.S).interval);
                KeyStroke.updateMap.get(KeyStrokeType.S).interval = 0;
                KeyStroke.updateMap.get(KeyStrokeType.S).isRelease = false;
            }
            if (KeyStroke.updateMap.get(KeyStrokeType.A).isRelease) {
                leftKeyChartLog.push(KeyStroke.updateMap.get(KeyStrokeType.A).interval);
                KeyStroke.updateMap.get(KeyStrokeType.A).interval = 0;
                KeyStroke.updateMap.get(KeyStrokeType.A).isRelease = false;
            }
            if (KeyStroke.updateMap.get(KeyStrokeType.D).isRelease) {
                rightKeyChartLog.push(KeyStroke.updateMap.get(KeyStrokeType.D).interval);
                KeyStroke.updateMap.get(KeyStrokeType.D).interval = 0;
                KeyStroke.updateMap.get(KeyStrokeType.D).isRelease = false;
            }
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {

            this.backKeyChartHud = new KeyChartHud(client.textRenderer, backKeyChartLog, true, "BackwardKey");
            this.forwardKeyChartHud = new KeyChartHud(client.textRenderer, forwardKeyChartLog, true, "ForwardKey");
            this.leftKeyChartHud = new KeyChartHud(client.textRenderer, leftKeyChartLog, true, "LeftKey");
            this.rightKeyChartHud = new KeyChartHud(client.textRenderer, rightKeyChartLog, true, "RightKey");

            backKeyChartHud.enabled = true;
            forwardKeyChartHud.enabled = true;
            leftKeyChartHud.enabled = true;
            rightKeyChartHud.enabled = true;

            for (int i = 0; i < 240; i++) {
                backKeyChartLog.push(0);
                forwardKeyChartLog.push(0);
                leftKeyChartLog.push(0);
                rightKeyChartLog.push(0);
            }
        });
    }

    private void render(DrawContext drawContext) {
        backKeyChartHud.render(drawContext, 0, 100);
        forwardKeyChartHud.render(drawContext, 100, 100);
        leftKeyChartHud.render(drawContext, 200, 100);
        rightKeyChartHud.render(drawContext, 300, 100);
    }
}
