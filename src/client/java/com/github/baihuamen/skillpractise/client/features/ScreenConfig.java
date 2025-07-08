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

package com.github.baihuamen.skillpractise.client.features;

import com.github.baihuamen.skillpractise.client.config.ConfigManager;
import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.config.utils.values.IntValue;
import com.github.baihuamen.skillpractise.client.config.utils.values.Value;
import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.features.mainconfigscreen.ScreenManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

@Environment(EnvType.CLIENT)
public abstract class ScreenConfig extends EventListener {

    public Screen screen;

    public abstract String name();

    public Map<String, Value> configMap = new HashMap<>();

    protected String featureRootLanguageKey = "skillpractise.features.";

    protected String translate(String name){
        return Text.translatable(featureRootLanguageKey + name().toLowerCase() +  "." + name.toLowerCase()).getString();
    }

    protected Text translateText(String name){
        return Text.translatable(featureRootLanguageKey + name().toLowerCase() +  "." + name.toLowerCase());
    }

    public ScreenConfig() {
        screen = new Screen(Text.of(name())) {
            @Override
            protected void init() {
                for (Map.Entry<String, Value> entry : configMap.entrySet()) {
                    var configValue = entry.getValue();
                    var component = configValue.achieveComponent(name(), screen);
                    addDrawableChild(component);
                }
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, float delta) {
                super.render(context, mouseX, mouseY, delta);
                for (Map.Entry<String, Value> entry : configMap.entrySet()) {
                    var configValue = entry.getValue();
                    configValue.render(context, name(), screen);
                }
            }

            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                 if(keyCode == GLFW.GLFW_KEY_ESCAPE){
                    mc().setScreen(ScreenManager.INSTANCE.screen);
                    return true;
                }
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        };
    }

    public BooleanValue Boolean(String name, boolean value) {
        BooleanValue booleanValue = new BooleanValue(this.name(), name, value);
        BooleanValue configValue = ConfigManager.config(this.name(), name, booleanValue);
        configMap.put(name, configValue);
        return configValue;
    }

    public IntValue Int(String name, int value) {
        IntValue intValue = new IntValue(this.name(), name, value);
        IntValue configValue = ConfigManager.config(this.name(), name, intValue);
        configMap.put(name, configValue);
        return configValue;
    }

    public void register() {
    }

    public void initialize() {
        HudLayerRegistrationCallback.EVENT.register(
                layeredDrawer -> layeredDrawer.attachLayerAfter(
                        IdentifiedLayer.MISC_OVERLAYS,
                        Identifier.of(name().toLowerCase(), "skillpractise/skillpractisehud/"),
                        this::onRenderEvent
                )
        );
        register();
    }

    public void onRenderEvent(DrawContext context, RenderTickCounter tickCounter) {
    }
}
