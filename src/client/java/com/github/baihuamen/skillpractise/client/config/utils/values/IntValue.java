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

package com.github.baihuamen.skillpractise.client.config.utils.values;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.ClientStartedEvent;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.utils.hud.gui.NumberInputFieldWidget;
import com.github.baihuamen.skillpractise.client.utils.typesetter.Typesetter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class IntValue extends EventListener implements Value{

    public int value;
    public String screenConfig;
    public String name;

    private NumberInputFieldWidget numberInputFieldWidget;

    @SuppressWarnings("unused")
    private final Class<?> tickHandler = registerEvent(TickEvent.class, event -> {
        if(mc().player == null) return;
        value = numberInputFieldWidget.getValue();
    });

    @SuppressWarnings("unused")
    private final Class<?> clientStartedHandler = registerEvent(ClientStartedEvent.class, event -> {

        numberInputFieldWidget = new NumberInputFieldWidget(mc().textRenderer, 0, 0, 100, 20, Text.of(""));
        numberInputFieldWidget.setMaxLength(9);
    });
    public IntValue(String screenConfig, String name, int value){
        this.value = value;
        this.screenConfig = screenConfig;
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void render(DrawContext context, String screenName, Screen screen) {
        context.drawTextWithShadow(mc().textRenderer, Text.translatable("skillpractise.features." + screenName.toLowerCase() + "." + name.toLowerCase()), screen.width / 10, Typesetter.getY(screenName,name), 0xFFFFFF);

    }

    @Override
    public <T extends Element & Drawable & Selectable> T achieveComponent(String name, Screen screen) {
        numberInputFieldWidget.setPosition(screen.width * 6/10, Typesetter.getY(name,name()));
        numberInputFieldWidget.setDimensions(screen.width * 3/10, 20);
        return (T) numberInputFieldWidget;
    }

    @Override
    public boolean isCurrentValue(JsonElement jsonElement) {
        try {
            jsonElement.getAsInt();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public JsonElement getCurrentValue() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public Value castToThisValue(String screen, String name, JsonElement valueInFile) {
        return new IntValue(screen,name,valueInFile.getAsInt());
    }
}
