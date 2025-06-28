package com.github.baihuamen.skillpractise.client.event;

import com.github.baihuamen.skillpractise.client.event.events.ClientEndedEvent;
import com.github.baihuamen.skillpractise.client.event.events.ClientStartedEvent;
import com.github.baihuamen.skillpractise.client.event.events.TickEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    public static Map<Class<? extends Event>, ArrayList<EventVoid>> eventList = new HashMap<>();

    public static void registerEvent(Class<? extends Event> event, EventVoid eventVoid) {
        if (!eventList.containsKey(event)) {
            eventList.put(event, new ArrayList<>(List.of(eventVoid)));
        } else {
            eventList.get(event).add(eventVoid);
        }
    }

    public static void callEvent(Class<? extends Event> event) {
        if (!eventList.containsKey(event)) return;
        eventList.get(event).forEach(EventVoid::onEvent);
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            callEvent(TickEvent.class);
        });
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            callEvent(ClientStartedEvent.class);
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            callEvent(ClientEndedEvent.class);
        });
    }
}
