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
import java.util.function.Consumer;

public final class EventManager {


    private static final Map<Class<?>, List<Consumer>> eventMap = new HashMap<>();

    public static EventManager INSTANCE = new EventManager();


    public EventManager() {
        INSTANCE = this;
        register();
    }

    private void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            callEvent(new TickEvent());
        });
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            callEvent(new ClientStartedEvent());
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            callEvent(new ClientEndedEvent());
        });
    }
    public <T> void registerEvent(Class<T> eventType, Consumer<T> consumer) {
        eventMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(consumer);
    }

    public void callEvent(Object event) {
        Class<?> eventType = event.getClass();
        if (eventMap.containsKey(eventType)) {
            for (Consumer<Object> consumer : eventMap.get(eventType)) {
                if(eventType.isInstance(event)){
                    consumer.accept(event);
                }
            }
        }
    }
}
