package com.github.baihuamen.skillpractise.client.config.utils.values;

import com.google.gson.JsonElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;

public interface Value {

    String name();

    void render(DrawContext context, String screenName, Screen screen);

    <T extends Element & Drawable & Selectable> T achieveComponent(String name, Screen screen);

    boolean isCurrentValue(JsonElement jsonElement);

    JsonElement getCurrentValue();

    Value castToThisValue(String screen, String name, JsonElement valueInFile);
}
