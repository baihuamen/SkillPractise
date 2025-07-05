/*
 * Copyright (C) ${YEAR} github.com/baihuamen/SkillPractise
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * ---
 *
 * 版权所有 (C) ${YEAR} github.com/baihuamen/SkillPractise
 *
 * 本程序是自由软件，您可以自由地重新发布或修改它，但必须遵循
 * GNU通用公共许可证（版本3或更高版本）的条款。
 *
 * 本程序没有任何担保，包括适销性或特定用途适用性的暗示担保。
 * 详情请参阅GNU通用公共许可证。
 *
 * 您应该已经随本程序收到了一份GNU通用公共许可证的副本。如果没有，
 * 请访问 <https://www.gnu.org/licenses/> 查看。
 */

package com.github.baihuamen.skillpractise.client.features.mainfeatures.keycharthud;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.ClientStartedEvent;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.features.mainconfigscreen.ScreenManager;
import com.github.baihuamen.skillpractise.client.utils.hud.ingame.KeyChartHud;
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
public class KeyChartInGameHud extends EventListener {

    private KeyChartHud forwardKeyChartHud;
    private KeyChartHud backKeyChartHud;
    private KeyChartHud leftKeyChartHud;
    private KeyChartHud rightKeyChartHud;

    public static MultiValueDebugSampleLogImpl forwardKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public static MultiValueDebugSampleLogImpl backKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public static MultiValueDebugSampleLogImpl leftKeyChartLog = new MultiValueDebugSampleLogImpl(1);
    public static MultiValueDebugSampleLogImpl rightKeyChartLog = new MultiValueDebugSampleLogImpl(1);

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    private final Class<?> clientStartedEvent = registerEvent(ClientStartedEvent.class, event -> {
        this.backKeyChartHud = new KeyChartHud(mc().textRenderer, backKeyChartLog, true, "BackwardKey");
        this.forwardKeyChartHud = new KeyChartHud(mc().textRenderer, forwardKeyChartLog, true, "ForwardKey");
        this.leftKeyChartHud = new KeyChartHud(mc().textRenderer, leftKeyChartLog, true, "LeftKey");
        this.rightKeyChartHud = new KeyChartHud(mc().textRenderer, rightKeyChartLog, true, "RightKey");
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
                (context, tickCounter) -> render(context)));
    }

    private void render(DrawContext drawContext) {
        backKeyChartHud.enabled = ScreenManager.INSTANCE.getInstance(KeyChartHudConfigScreen.class).backKeyChartDisplay.value;
        forwardKeyChartHud.enabled = ScreenManager.INSTANCE.getInstance(KeyChartHudConfigScreen.class).forwardKeyChartDisplay.value;
        leftKeyChartHud.enabled = ScreenManager.INSTANCE.getInstance(KeyChartHudConfigScreen.class).leftKeyChartDisplay.value;
        rightKeyChartHud.enabled = ScreenManager.INSTANCE.getInstance(KeyChartHudConfigScreen.class).rightKeyChartDisplay.value;
        backKeyChartHud.render(drawContext, 0, 100);
        forwardKeyChartHud.render(drawContext, 100, 100);
        leftKeyChartHud.render(drawContext, 200, 100);
        rightKeyChartHud.render(drawContext, 300, 100);
    }
}
