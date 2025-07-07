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

package com.github.baihuamen.skillpractise.client.features.mainconfigscreen;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.BridgeSpeedCounter;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.CPSCounterHud;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.InteractBlockTips;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.NightVisionRender;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.keycharthud.DisableCreativeFly;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.keycharthud.KeyChartHudConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

@Environment(EnvType.CLIENT)
public class ScreenManager extends EventListener {


    private Screen screen;
    private final Map<Class<? extends ScreenConfig>, ScreenConfig> screensMap = new HashMap<>();


    private static KeyBinding openKey;

    @SuppressWarnings("unused")
    private final Class<?> tickHandler = registerEvent(TickEvent.class, event -> {
        if (openKey.wasPressed()) {
            mc().setScreen(screen);
        }
    });

    public static ScreenManager INSTANCE;

    public static void register() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("skillpractise.screenmanager.openkey",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "SkillPractise"));
    }

    public <T extends ScreenConfig> T getInstance(Class<T> screenConfigClass) {
        return (T) screensMap.get(screenConfigClass);
    }

    public ScreenManager() {
        INSTANCE = this;
        screensMap.put(KeyChartHudConfigScreen.class, new KeyChartHudConfigScreen());
        screensMap.put(BridgeSpeedCounter.class, new BridgeSpeedCounter());
        screensMap.put(CPSCounterHud.class, new CPSCounterHud());
        screensMap.put(InteractBlockTips.class, new InteractBlockTips());
        screensMap.put(NightVisionRender.class, new NightVisionRender());
        screensMap.put(DisableCreativeFly.class,new DisableCreativeFly());
        screensMap.forEach((screenConfigClass, screenConfig) -> screenConfig.initialize());
        screen = new Screen(Text.of("SkillPractise")) {
            @Override
            protected void init() {
                super.init();
                int indexX = 0;
                int indexY = 0;
                for (Map.Entry<Class<? extends ScreenConfig>, ScreenConfig> screenConfig : screensMap.entrySet()) {
                    ButtonWidget buttonWidget = ButtonWidget.builder(Text.translatable("skillpractise.screen."  + screenConfig.getValue()
                                    .name()), onPress -> client.setScreen(screenConfig.getValue().screen))
                            .dimensions((indexX + 1) * this.width / 5 + 10, (indexY + 1) * this.height / 5 + 10, this.width / 6, this.height / 9)
                            .build();
                    addDrawableChild(buttonWidget);
                    indexX++;
                    if (indexX >= 4) {
                        indexX = 0;
                        indexY++;
                    }
                    if (indexY >= 4) {
                        break;
                    }
                }
            }
        };
    }
}
