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

package com.github.baihuamen.skillpractise.client.utils.hud;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.DrawContextEvent;
import net.minecraft.util.Colors;

import java.util.ArrayList;
import java.util.List;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class TerminalInfo extends EventListener {


    public static List<String> infoList = new ArrayList<>();

    private static String currentText;
    public static long lastMillionSecond = 0;

    @SuppressWarnings("unused")
    private static final Class<?> worldRenderEvent = registerEvent( DrawContextEvent.class, drawContextEvent -> {
        if (infoList.isEmpty() && lastMillionSecond != 0) {
            drawContextEvent.context.drawText(mc().textRenderer, currentText,
                    drawContextEvent.context.getScaledWindowWidth() / 2 - mc().textRenderer.getWidth(currentText) / 2, 20, Colors.WHITE, false);
            if(System.currentTimeMillis() - lastMillionSecond > 1000){
                currentText = "";
                lastMillionSecond = 0;
            }
            return;
        }

        if(infoList.isEmpty()) return;
        drawContextEvent.context.drawText(mc().textRenderer, infoList.getFirst(),
                drawContextEvent.context.getScaledWindowWidth() / 2 - mc().textRenderer.getWidth(infoList.getFirst()) / 2, 20, Colors.WHITE, false);
        currentText = infoList.getFirst();

        infoList.removeFirst();

        lastMillionSecond = System.currentTimeMillis();
    });

    public static void displayLog(String text) {
        infoList.add(text);
    }

    public static void displayError(String text) {
        infoList.add(text);
    }
}
