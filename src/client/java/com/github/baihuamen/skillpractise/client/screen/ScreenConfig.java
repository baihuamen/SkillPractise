package com.github.baihuamen.skillpractise.client.screen;

import com.github.baihuamen.skillpractise.client.config.ConfigManager;
import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.config.utils.values.Value;
import com.github.baihuamen.skillpractise.client.event.EventListener;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public abstract class ScreenConfig extends EventListener {

    public Screen screen;

    public abstract String name();

    public Map<String, Value> configMap = new HashMap<>();

    public ScreenConfig() {
        screen = new Screen(Text.of(name())) {
            @Override
            protected void init() {
                for (Map.Entry<String, Value> entry : configMap.entrySet()) {
                    var configValue = entry.getValue();
                    var component = configValue.achieveComponent(name(), screen);
                    addDrawableChild(component);
                }
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, float delta) {
                super.render(context, mouseX, mouseY, delta);
                for (Map.Entry<String, Value> entry : configMap.entrySet()) {
                    var configValue = entry.getValue();
                    configValue.render(context, name(), screen);
                }
            }
        };
    }

    public BooleanValue Boolean(String name, boolean value) {
        BooleanValue booleanValue = new BooleanValue(this.name(), name, value);
        BooleanValue configValue = ConfigManager.config(this.name(), name, booleanValue);
        configMap.put(name, configValue);
        return configValue;
    }

    public void register() {
    }

    public void initialize() {
        HudLayerRegistrationCallback.EVENT.register(
                layeredDrawer -> layeredDrawer.attachLayerAfter(
                        IdentifiedLayer.MISC_OVERLAYS,
                        Identifier.of(name().toLowerCase(), "skillpractise/skillpractisehud/"),
                        this::onRenderEvent
                )
        );
        register();
    }

    public void onRenderEvent(DrawContext context, RenderTickCounter tickCounter) {
    }
}
