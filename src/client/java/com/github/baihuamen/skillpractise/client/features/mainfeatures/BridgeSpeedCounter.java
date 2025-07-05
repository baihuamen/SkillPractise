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
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;
import static com.github.baihuamen.skillpractise.client.utils.render.RendererUtils.renderNumber;

public class BridgeSpeedCounter extends ScreenConfig {

    public static double speedPerSecond;
    public static double speedPerSecondGround;
    public static double speedInTwiceJump;
    public static double speedFlash;

    private boolean wasGrounding;
    private int startJumpTick;
    private Vec3d startJumpPosition;
    private Vec3d lastSecondPositon;

    private int tickCounter = 0;
    private long checkMileSecond = 0;

    private final BooleanValue isDisplaySpeedPerSecond = Boolean("DisplayerSpeedPerSecond", true);
    private final BooleanValue isDisplaySpeedPerSecondGround = Boolean("DisplaySpeedPerSecondGround", true);
    private final BooleanValue isDisplaySpeedInTwiceJump = Boolean("DisplaySpeedInTwiceJump", true);
    private final BooleanValue isDisplaySpeedFlash = Boolean("DisplaySpeedFlash", true);

    @Override
    public String name() {
        return "BridgeSpeedCounter";
    }

    @Override
    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, Identifier.of("bridgespeecounter", "skillpractise/skillpractisehud"), (context, tickCounter) -> {
            if (mc().player == null) return;
            render(context);
        }));
    }

    @SuppressWarnings("unused")
    private final Class<?> tickHandler = registerEvent(TickEvent.class, event -> {
        tickCounter++;
        if (mc().player == null) return;
        calculateSpeedInTwiceJump();
        calculateSpeedPerSecond();
        calculateSpeedFlash();
        wasGrounding = mc().player.isOnGround();
    });

    private static void calculateSpeedFlash() {
        Vec3d currentPos = mc().player.getPos();
        Vec3d prevPos = new Vec3d(mc().player.prevX, mc().player.prevY, mc().player.prevZ);
        speedFlash = currentPos.distanceTo(prevPos) / 0.05;
    }

    private void calculateSpeedPerSecond() {
        if (System.currentTimeMillis() - checkMileSecond >= 1000) {
            if (lastSecondPositon != null) {
                speedPerSecond = mc().player.getPos().distanceTo(lastSecondPositon);
                speedPerSecondGround = Math.sqrt(Math.pow(mc().player.getPos().x - lastSecondPositon.x, 2) + Math.pow(mc().player.getPos().z - lastSecondPositon.z, 2));
            }
            lastSecondPositon = mc().player.getPos();

            checkMileSecond = System.currentTimeMillis();
        }
    }

    private void calculateSpeedInTwiceJump() {
        if (wasGrounding != mc().player.isOnGround() && !mc().player.isOnGround()) {
            startJumpPosition = mc().player.getPos();
            startJumpTick = tickCounter;
        }
        if (wasGrounding != mc().player.isOnGround() && mc().player.isOnGround()) {
            if (startJumpPosition != null) {
                speedInTwiceJump = (mc().player.getPos()
                        .distanceTo(startJumpPosition)) / ((tickCounter - startJumpTick) * 0.05);
            }
        }
    }


    private void render(DrawContext context) {
        if (isDisplaySpeedFlash.value) {
            renderNumber(context, speedFlash, translate("speedFlash") + ":", 5, 0, 10, 0xFFFFFF, true);
        }
        if (isDisplaySpeedPerSecond.value) {
            renderNumber(context, speedPerSecond, translate("speedPerSecond") + ":",
                    5, 0, 20, 0xFFFFFF, true);
        }
        if (isDisplaySpeedPerSecondGround.value) {
            renderNumber(context, speedPerSecondGround, translate("speedPerSecondGround") + ":",
                    5, 0, 30, 0xFFFFFF, true);
        }
        if (isDisplaySpeedInTwiceJump.value) {
            renderNumber(context, speedInTwiceJump, translate("speedInTwiceJump") + ":", 5, 0, 40, 0xFFFFFF, true);
        }
    }
}
