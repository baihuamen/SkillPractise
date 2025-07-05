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

package com.github.baihuamen.skillpractise.client.utils.keystroke;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.HashMap;
import java.util.Map;


@Environment(EnvType.CLIENT)
public class KeyStroke {
    public static Map<KeyStrokeType, KeyState> updateMap= new HashMap<>(
            Map.of(
                    KeyStrokeType.W,new KeyState(false,0),
                    KeyStrokeType.S,new KeyState(false,0),
                    KeyStrokeType.A,new KeyState(false,0),
                    KeyStrokeType.D,new KeyState(false,0),
                    KeyStrokeType.SPACE,new KeyState(false,0)

            )
    );

    public static void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().options.backKey.isPressed()) {
                updateMap.get(KeyStrokeType.S).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.S).interval != 0){
                updateMap.get(KeyStrokeType.S).setReleased(true);
            }

            if (MinecraftClient.getInstance().options.forwardKey.isPressed()) {
                updateMap.get(KeyStrokeType.W).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.W).interval!= 0){
                updateMap.get(KeyStrokeType.W).setReleased(true);
            }

            if (MinecraftClient.getInstance().options.leftKey.isPressed()) {
                updateMap.get(KeyStrokeType.A).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.A).interval!= 0) {
                updateMap.get(KeyStrokeType.A).setReleased(true);
            }

            if (MinecraftClient.getInstance().options.rightKey.isPressed()) {
                updateMap.get(KeyStrokeType.D).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.D).interval!= 0) {
                updateMap.get(KeyStrokeType.D).setReleased(true);
            }
        });
    }
}

