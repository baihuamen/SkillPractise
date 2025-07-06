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

package com.github.baihuamen.skillpractise.utils.placer;

import com.github.baihuamen.skillpractise.event.ServerEventListener;
import com.github.baihuamen.skillpractise.event.events.ServerTickEvent;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BlockPlacerManager extends ServerEventListener {

    private static final List<BlockState> multiBlockPlacerCache = new ArrayList<>();

    private static final List<BlockState> fastBlockPlacerCache = new ArrayList<>();

    @SuppressWarnings("unused")
    private static final Class<?> tickHandler = registerEvent(ServerTickEvent.class, event -> {
        if (fastBlockPlacerCache.isEmpty()) return;
        fastBlockPlacerCache.forEach((blockState -> blockState.serverWorld()
                .setBlockState(new BlockPos(blockState.x(), blockState.y(), blockState.z()), blockState.block()
                        .getDefaultState())));
        fastBlockPlacerCache.clear();
    });


    public static void applyFastBlockPlacer(BlockState blockState) {
        fastBlockPlacerCache.add(blockState);
    }

    public static void applyFastBlockPlacer(List<BlockState> blockStateList) {
        fastBlockPlacerCache.addAll(blockStateList);
    }

    public static void applyFastBlockPlacer(int x, int y, int z, Block block, ServerWorld serverWorld) {
        fastBlockPlacerCache.add(new BlockState(x, y, z, block, serverWorld));
    }

    public static void applyFastBlockPlacer(List<BlockPos> blockPosList, Block block, ServerWorld serverWorld) {
        blockPosList.forEach(blockPos -> fastBlockPlacerCache.add(new BlockState(blockPos.getX(), blockPos.getY(), blockPos.getZ(), block,
                serverWorld)));
    }
}
