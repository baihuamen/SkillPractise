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

package com.github.baihuamen.skillpractise.event;

import com.github.baihuamen.skillpractise.event.events.ServerEndedEvent;
import com.github.baihuamen.skillpractise.event.events.ServerStartedEvent;
import com.github.baihuamen.skillpractise.event.events.ServerTickEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public final class ServerEventManager {


    private static final Map<Class<?>, List<Consumer>> eventMap = new HashMap<>();

    public static ServerEventManager INSTANCE = new ServerEventManager();


    public ServerEventManager() {
        INSTANCE = this;
        register();
    }

    private void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> callEvent(new ServerTickEvent()));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> callEvent(new ServerStartedEvent(server)));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> callEvent(new ServerEndedEvent()));
    }

    public <T> void registerEvent(Class<T> eventType, Consumer<T> consumer) {
        eventMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(consumer);
    }

    public void callEvent(Object event) {
        Class<?> eventType = event.getClass();
        if (eventMap.containsKey(eventType)) {
            for (Consumer<Object> consumer : eventMap.get(eventType)) {
                if (eventType.isInstance(event)) {
                    consumer.accept(event);
                }
            }
        }
    }
}
