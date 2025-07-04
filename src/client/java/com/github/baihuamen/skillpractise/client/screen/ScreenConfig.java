package com.github.baihuamen.skillpractise.client.screen;

import com.github.baihuamen.skillpractise.client.config.ConfigManager;
import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.EventListener;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public abstract class ScreenConfig extends EventListener {

    public Screen screen;

    public abstract String name();

    public Map<String, Object> configMap = new HashMap<>();

    public ScreenConfig() {
        screen = new Screen(Text.of(name())) {
            @Override
            protected void init() {
                int componentIndex = 0;
                for (Map.Entry<String, Object> entry : configMap.entrySet()) {
                    String configName = entry.getKey();
                    var configValue = entry.getValue();
                    if (configValue instanceof BooleanValue) {
                        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of(String.valueOf(((BooleanValue) configValue).value)), button -> {
                            ((BooleanValue) configValue).invert();
                            button.setMessage(Text.of(String.valueOf(((BooleanValue) configValue).value)));
                        }).dimensions(this.width * 6 / 10, 50 * componentIndex + 10, this.width * 3 / 10, 40).build();
                        addDrawableChild(buttonWidget);
                        componentIndex++;
                    }
                }
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, float delta) {
                super.render(context, mouseX, mouseY, delta);
                int componentIndex = 0;
                for (Map.Entry<String, Object> entry : configMap.entrySet()) {
                    String configName = entry.getKey();
                    var configValue = entry.getValue();
                    if (configValue instanceof BooleanValue) {
                        context.drawTextWithShadow(this.textRenderer, Text.of(configName), this.width * 1 / 10, 50 * componentIndex + 10, 0xFFFFFF);
                    }
                    componentIndex++;
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

    public void initialize(){

        HudLayerRegistrationCallback.EVENT.register(
                layeredDrawer -> layeredDrawer.attachLayerAfter(
                        IdentifiedLayer.MISC_OVERLAYS,
                        Identifier.of(name().toLowerCase(), "skillpractise/skillpractisehud/"),
                        this::onRenderEvent
                )
        );
        register();
    }

    public void onRenderEvent(DrawContext context, RenderTickCounter tickCounter){};
}
