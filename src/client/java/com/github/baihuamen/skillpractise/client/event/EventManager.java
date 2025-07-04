package com.github.baihuamen.skillpractise.client.event;

import com.github.baihuamen.skillpractise.client.event.events.commonevents.ClientEndedEvent;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.ClientStartedEvent;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    public static Map<Class<? extends Event>, ArrayList<EventVoid>> commonEventList = new HashMap<>();
    public static Map<Class<? extends EventReturnable>, ArrayList<EventVoidReturnable>> returnableEventList = new HashMap<>();

    public static void registerEvent(Class<? extends Event> event, EventVoid eventVoid) {
        if (!commonEventList.containsKey(event)) {
            commonEventList.put(event, new ArrayList<>(List.of(eventVoid)));
        } else {
            commonEventList.get(event).add(eventVoid);
        }
    }

    public static void registerEvent(Class<? extends EventReturnable> event, EventVoidReturnable eventVoidReturnable){
        if (!returnableEventList.containsKey(event)) {
            returnableEventList.put(event, new ArrayList<>(List.of(eventVoidReturnable)));
        } else {
            returnableEventList.get(event).add(eventVoidReturnable);
        }
    }

    public static void callEvent(Class<? extends Event> event) {
        if (!commonEventList.containsKey(event)) return;
        commonEventList.get(event).forEach(EventVoid::onEvent);
    }

    public static void callEvent(Class<? extends EventReturnable> event, Object eventReturnValue){
        if (!returnableEventList.containsKey(event.getClass())) return;
        returnableEventList.get(event.getClass()).forEach(eventVoidReturnable -> {
            eventVoidReturnable.onEvent(eventReturnValue);
        });
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
