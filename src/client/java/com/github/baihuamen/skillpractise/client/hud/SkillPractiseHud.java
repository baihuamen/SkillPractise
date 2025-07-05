package com.github.baihuamen.skillpractise.client.hud;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.ClientStartedEvent;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.screen.SkillPractiseScreenConfig;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStroke;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStrokeType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

@Environment(EnvType.CLIENT)
public class SkillPractiseHud extends EventListener {

    private KeyChartHud forwardKeyChartHud;
    private KeyChartHud backKeyChartHud;
    private KeyChartHud leftKeyChartHud;
    private KeyChartHud rightKeyChartHud;

    public static MultiValueDebugSampleLogImpl forwardKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public static MultiValueDebugSampleLogImpl backKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public static MultiValueDebugSampleLogImpl leftKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public static MultiValueDebugSampleLogImpl rightKeyChartLog = new MultiValueDebugSampleLogImpl(1);

    private final static Class<?> tickEvent = registerEvent(TickEvent.class, event -> {
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

    private final Class<?> clientStartedEvent = registerEvent(ClientStartedEvent.class, event -> {

        this.backKeyChartHud = new KeyChartHud(mc.textRenderer, backKeyChartLog, true, "BackwardKey");
        this.forwardKeyChartHud = new KeyChartHud(mc.textRenderer, forwardKeyChartLog, true, "ForwardKey");
        this.leftKeyChartHud = new KeyChartHud(mc.textRenderer, leftKeyChartLog, true, "LeftKey");
        this.rightKeyChartHud = new KeyChartHud(mc.textRenderer, rightKeyChartLog, true, "RightKey");
        for (int i = 0; i < 240; i++) {
            backKeyChartLog.push(0);
            forwardKeyChartLog.push(0);
            leftKeyChartLog.push(0);
            rightKeyChartLog.push(0);
        }
    });

    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(
                IdentifiedLayer.MISC_OVERLAYS,
                Identifier.of("skillpractise", "skillpractise/skillpractisehud"),
                (context, tickCounter) -> {
                    render(context);
                }));
    }

    private void render(DrawContext drawContext) {
        backKeyChartHud.enabled = SkillPractiseScreenConfig.enabledBackKeyChart;
        forwardKeyChartHud.enabled = SkillPractiseScreenConfig.enabledForwardKeyChart;
        leftKeyChartHud.enabled = SkillPractiseScreenConfig.enabledLeftKeyChart;
        rightKeyChartHud.enabled = SkillPractiseScreenConfig.enabledRightKeyChart;
        backKeyChartHud.render(drawContext, 0, 100);
        forwardKeyChartHud.render(drawContext, 100, 100);
        leftKeyChartHud.render(drawContext, 200, 100);
        rightKeyChartHud.render(drawContext, 300, 100);
    }
}
