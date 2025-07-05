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

package com.github.baihuamen.skillpractise.client.utils.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

@Environment(EnvType.CLIENT)
public class RendererUtils {

    public static void renderText(@NotNull DrawContext context, String text, int x, int y, int color, boolean shadow) {
        context.drawText(mc().textRenderer, Text.of(text), x, y, color, shadow);
    }

    public static void renderNumber(DrawContext context, double number, int length, int x, int y, int color, boolean shadow) {
        renderNumber(context, number, "", length, x, y, color, shadow);
    }

    public static void renderNumber(DrawContext context, double number, String prefix, int length, int x, int y, int color, boolean shadow) {
        if (String.valueOf(number).length() <= length) {
            renderText(context, prefix + number, x, y, color, shadow);
        } else if (String.valueOf(number).length() > length) {
            renderText(context, prefix + String.valueOf(number).substring(0, length), x, y, color, shadow);
        }
    }
}
