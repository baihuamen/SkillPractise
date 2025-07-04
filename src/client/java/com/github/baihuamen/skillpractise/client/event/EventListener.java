package com.github.baihuamen.skillpractise.client.event;

public class EventListener {

    public static Class<? extends Event> registerEvent(Class<? extends Event> event, EventVoid eventVoid) {
        EventManager.registerEvent(event, eventVoid);
        return event;
    }

    public static <I> Class<? extends EventReturnable> registerEvent(Class<? extends EventReturnable> event, EventVoidReturnable<I> eventVoidReturnable) {
        EventManager.registerEvent(event,eventVoidReturnable);
        return event;
    }
}
