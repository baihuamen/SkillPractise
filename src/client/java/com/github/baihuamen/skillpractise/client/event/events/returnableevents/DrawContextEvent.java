package com.github.baihuamen.skillpractise.client.event.events.returnableevents;

import net.minecraft.client.gui.DrawContext;

public class DrawContextEvent {

    public DrawContext context;

    public DrawContextEvent(DrawContext context) {
        this.context = context;
    }
}
