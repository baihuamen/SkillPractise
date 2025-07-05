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

import com.github.baihuamen.skillpractise.client.utils.typesetter.Typesetter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class BooleanValue implements Value{
    public boolean value;
    public String screenConfig;
    public String name;
    public BooleanValue(String screenConfig, String name, boolean value){
        this.value = value;
        this.screenConfig = screenConfig;
        this.name = name;
    }

    public void invert() {
        this.value = !this.value;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void render(DrawContext context,String screenName,Screen screen) {
        context.drawTextWithShadow(mc().textRenderer, Text.of(name), screen.width / 10,Typesetter.getY(screenName,name), 0xFFFFFF);
    }

    @Override
    public <T extends Element & Drawable & Selectable> T achieveComponent(String name,Screen screen) {


        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of(String.valueOf(value)), button -> {
            invert();
            button.setMessage(Text.of(String.valueOf(value)));
        }).dimensions(screen.width * 6 / 10, Typesetter.getY(name,name()), screen.width * 3 / 10, 40).build();
        return (T) buttonWidget;
    }

    @Override
    public boolean isCurrentValue(JsonElement jsonElement) {
        try {
            jsonElement.getAsBoolean();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JsonElement getCurrentValue() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public Value castToThisValue(String screen, String name,JsonElement valueInFile) {
        return new BooleanValue(screen,name,valueInFile.getAsBoolean());
    }
}
