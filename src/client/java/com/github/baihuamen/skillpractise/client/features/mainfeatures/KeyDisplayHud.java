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

package com.github.baihuamen.skillpractise.client.features.mainfeatures;

import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.DrawContextEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import com.github.baihuamen.skillpractise.client.utils.hud.ingame.BottomKeyDisplayHud;
import net.minecraft.util.Colors;

import java.util.List;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class KeyDisplayHud extends ScreenConfig {

    private final BottomKeyDisplayHud forwardKeyDisplayHud = new BottomKeyDisplayHud("W", Colors.BLACK, Colors.WHITE);
    private final BottomKeyDisplayHud backKeyDisplayHud = new BottomKeyDisplayHud("S", Colors.BLACK, Colors.WHITE);
    private final BottomKeyDisplayHud leftKeyDisplayHud = new BottomKeyDisplayHud("A", Colors.BLACK, Colors.WHITE);
    private final BottomKeyDisplayHud rightKeyDisplayHud = new BottomKeyDisplayHud("D", Colors.BLACK, Colors.WHITE);
    private final BottomKeyDisplayHud leftMouseDisplayHud = new BottomKeyDisplayHud("LEFT", Colors.BLACK, Colors.WHITE);
    private final BottomKeyDisplayHud rightMouseDisplayHud = new BottomKeyDisplayHud("RIGHT", Colors.BLACK,
            Colors.WHITE);
    private final BottomKeyDisplayHud spaceKeyDisplayHud = new BottomKeyDisplayHud("SPACE", Colors.BLACK, Colors.WHITE);

    private final BooleanValue enabled = Boolean("Enabled", true);
    private final BooleanValue textShadow = Boolean("TextShadow", false);

    public static boolean isLeftMousePressed = false;
    public static boolean isRightMousePressed = false;
    public static boolean isSpacePressed = false;
    public static boolean isLeftKeyPressed = false;
    public static boolean isRightKeyPressed = false;
    public static boolean isForwardKeyPressed = false;
    public static boolean isBackKeyPressed = false;

    private final List<BottomKeyDisplayHud> buttomList =
            List.of(forwardKeyDisplayHud,
                    backKeyDisplayHud,
                    leftKeyDisplayHud,
                    rightKeyDisplayHud,
                    leftMouseDisplayHud,
                    rightMouseDisplayHud,
                    spaceKeyDisplayHud
            );

    @SuppressWarnings("unused")
    private final Class<?> renderHandler = registerEvent(DrawContextEvent.class, drawContextEvent -> {
        if (!enabled.value) return;
        if (mc().inGameHud == null) return;
        forwardKeyDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 50, 25, 25, 25);
        backKeyDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 50, 50, 25, 25);
        leftKeyDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 75, 50, 25, 25);
        rightKeyDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 25, 50, 25, 25);
        leftMouseDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 75, 75, 37, 25);
        rightMouseDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 37, 75, 37, 25);
        spaceKeyDisplayHud.setBoarder(drawContextEvent.context.getScaledWindowWidth() - 75, 100, 75, 25);
        buttomList.forEach(bottomKeyDisplayHud -> bottomKeyDisplayHud.setTextShadow(textShadow.value));
        if (isForwardKeyPressed || mc().options.forwardKey.isPressed()) {
            forwardKeyDisplayHud.setAreaColor(Colors.WHITE);
            forwardKeyDisplayHud.setTextColor(Colors.BLACK);
            isForwardKeyPressed = false;
        } else {
            forwardKeyDisplayHud.setAreaColor(Colors.BLACK);
            forwardKeyDisplayHud.setTextColor(Colors.WHITE);
        }
        if (isBackKeyPressed || mc().options.backKey.isPressed()) {
            backKeyDisplayHud.setAreaColor(Colors.WHITE);
            backKeyDisplayHud.setTextColor(Colors.BLACK);
            isBackKeyPressed = false;
        } else {
            backKeyDisplayHud.setAreaColor(Colors.BLACK);
            backKeyDisplayHud.setTextColor(Colors.WHITE);
        }
        if (isLeftKeyPressed || mc().options.leftKey.isPressed()) {
            leftKeyDisplayHud.setAreaColor(Colors.WHITE);
            leftKeyDisplayHud.setTextColor(Colors.BLACK);
            isLeftKeyPressed = false;
        } else {
            leftKeyDisplayHud.setAreaColor(Colors.BLACK);
            leftKeyDisplayHud.setTextColor(Colors.WHITE);
        }
        if (isRightKeyPressed || mc().options.rightKey.isPressed()) {
            leftMouseDisplayHud.setAreaColor(Colors.WHITE);
            leftMouseDisplayHud.setTextColor(Colors.BLACK);
            isRightKeyPressed = false;
        } else {
            leftMouseDisplayHud.setAreaColor(Colors.BLACK);
            leftMouseDisplayHud.setTextColor(Colors.WHITE);
        }
        if (isLeftMousePressed || mc().options.attackKey.isPressed()) {
            leftMouseDisplayHud.setAreaColor(Colors.WHITE);
            leftMouseDisplayHud.setTextColor(Colors.BLACK);
            isLeftMousePressed = false;
        } else {
            leftMouseDisplayHud.setAreaColor(Colors.BLACK);
            leftMouseDisplayHud.setTextColor(Colors.WHITE);
        }
        if (isRightMousePressed || mc().options.useKey.isPressed()) {
            rightMouseDisplayHud.setAreaColor(Colors.WHITE);
            rightMouseDisplayHud.setTextColor(Colors.BLACK);
            isRightMousePressed = false;
        } else {
            rightMouseDisplayHud.setAreaColor(Colors.BLACK);
            rightMouseDisplayHud.setTextColor(Colors.WHITE);
        }
        if (isSpacePressed || mc().options.jumpKey.isPressed()) {
            spaceKeyDisplayHud.setAreaColor(Colors.WHITE);
            spaceKeyDisplayHud.setTextColor(Colors.BLACK);
            isSpacePressed = false;
        } else {
            spaceKeyDisplayHud.setAreaColor(Colors.BLACK);
            spaceKeyDisplayHud.setTextColor(Colors.WHITE);
        }
        buttomList.forEach(bottomKeyDisplayHud -> bottomKeyDisplayHud.render(drawContextEvent.context));
    });

    @Override
    public String name() {
        return "KeyDisplayHud";
    }
}



