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
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.PlayerInteractionBlockEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import com.github.baihuamen.skillpractise.client.utils.hud.TerminalInfo;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;
import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.player;

public class InteractBlockTips extends ScreenConfig {


    private final BooleanValue enable = Boolean("Enable", true);

    @Override
    public String name() {
        return "InteractBlockTips";
    }

    @SuppressWarnings("unused")
    private final Class<?> interactionHandler = registerEvent(PlayerInteractionBlockEvent.class, event -> {
        if (!enable.value) return;
        var hitResult = player().raycast(4.5, 0, false);
        if (!(hitResult instanceof BlockHitResult)) return;
        if (!mc().options.useKey.isPressed()) return;
        if (((BlockHitResult) hitResult).getSide() == Direction.UP) {
            // 绿色字体消息
            TerminalInfo.displayLog("交互过早，交互到了方块上方");
        }
        if (hitResult.getType() == HitResult.Type.MISS) {
            TerminalInfo.displayLog("交互过晚，没有交互到方块");
        }
        if (event.actionResult() == ActionResult.SUCCESS)
            TerminalInfo.displayLog("交互成功");
    });
}
