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

package com.github.baihuamen.skillpractise.client.utils.hud.ingame;

import net.minecraft.client.gui.DrawContext;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class BottomKeyDisplayHud {

    public String text;
    public int areaColor;
    public int textColor;
    public boolean textShadow = true;

    public int x;
    public int y;
    public int width;
    public int height;

    public BottomKeyDisplayHud(String text,int areaColor,int textColor){
        this.text = text;
        this.areaColor = areaColor;
        this.textColor = textColor;
    }

    public BottomKeyDisplayHud(String text,int areaColor,int textColor,int x,int y,int width,int height){
        this.text = text;
        this.areaColor = areaColor;
        this.textColor = textColor;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setText(String text){
        this.text =text;
    }

    public void setAreaColor(int areaColor){
        this.areaColor = areaColor;
    }

    public void setTextColor(int textColor){
        this.textColor =textColor;
    }

    public void setBoarder(int x,int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setTextShadow(boolean textShadow){
        this.textShadow = textShadow;
    }

    public void invertColor(){
        areaColor = Integer.MAX_VALUE - areaColor;
        textColor = Integer.MAX_VALUE - textColor;
    }

    public void render(DrawContext context){
        context.fill(x, y, x + width ,y + height, areaColor);
        if(textShadow) {
            context.drawCenteredTextWithShadow(mc().textRenderer, text, x + width / 2, y + height / 2, textColor);
        }
        else {
            context.drawText(mc().textRenderer, text, x - mc().textRenderer.getWidth(text) / 2 + width / 2, y + height / 2, textColor, false);
        }
    }

}
