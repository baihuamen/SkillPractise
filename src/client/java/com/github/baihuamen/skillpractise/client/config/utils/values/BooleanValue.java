package com.github.baihuamen.skillpractise.client.config.utils.values;

import com.github.baihuamen.skillpractise.client.utils.typesetter.Typesetter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class BooleanValue implements Value{
    public boolean value;
    public String screenConfig;
    public String name;
    public BooleanValue(String screenConfig, String name, boolean value){
        this.value = value;
        this.screenConfig = screenConfig;
        this.name = name;
    }

    public void invert() {
        this.value = !this.value;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void render(DrawContext context,String screenName,Screen screen) {
        context.drawTextWithShadow(mc().textRenderer, Text.of(name), screen.width * 1 / 10,Typesetter.getY(screenName,name), 0xFFFFFF);
    }

    @Override
    public <T extends Element & Drawable & Selectable> T achieveComponent(String name,Screen screen) {


        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of(String.valueOf(value)), button -> {
            invert();
            button.setMessage(Text.of(String.valueOf(value)));
        }).dimensions(screen.width * 6 / 10, Typesetter.getY(name,name()), screen.width * 3 / 10, 40).build();
        return (T) buttonWidget;
    }

    @Override
    public boolean isCurrentValue(JsonElement jsonElement) {
        try {
            jsonElement.getAsBoolean();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JsonElement getCurrentValue() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public Value castToThisValue(String screen, String name,JsonElement valueInFile) {
        return new BooleanValue(screen,name,valueInFile.getAsBoolean());
    }
}
