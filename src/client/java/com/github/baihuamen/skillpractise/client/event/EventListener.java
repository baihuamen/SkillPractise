package com.github.baihuamen.skillpractise.client.event;

import java.util.function.Consumer;

public class EventListener {
    public static <T> Class<?> registerEvent(Class<T> event, Consumer<T> consumer) {
        EventManager.INSTANCE.registerEvent(event,consumer);
        return event;
    }
}
