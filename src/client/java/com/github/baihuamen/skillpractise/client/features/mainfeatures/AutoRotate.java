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
import com.github.baihuamen.skillpractise.client.config.utils.values.IntValue;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import com.github.baihuamen.skillpractise.client.utils.hud.TerminalInfo;
import net.minecraft.util.math.Vec3d;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class AutoRotate extends ScreenConfig {



    private final BooleanValue enabled = Boolean("Enabled", false);
    private final IntValue targetYaw = Int("TargetYaw", 0);
    private final IntValue targetPitch = Int("TargetPitch", 0);
    private final IntValue teleportDistance = Int("TeleportDistance", 10);

    private boolean isLastTickEnabled = false;
    private Vec3d lastPos;

    @SuppressWarnings("unused")
    private final Class<?> tickHandler = registerEvent(TickEvent.class, event -> {
        if (mc().player == null) return;
        if(!isLastTickEnabled && enabled.value){
            TerminalInfo.displayLog(translate("Warning"));
        }
        isLastTickEnabled = enabled.value;
        if(!enabled.value) return;
        if(lastPos == null) {
            lastPos = mc().player.getPos();
            return;
        }
        if(lastPos.distanceTo(mc().player.getPos()) > teleportDistance.value) {
            mc().player.setYaw(targetYaw.value);
            mc().player.setPitch(targetPitch.value);
        }
        lastPos = mc().player.getPos();
    });

    @Override
    public String name() {
        return "AutoRotate";
    }
}
