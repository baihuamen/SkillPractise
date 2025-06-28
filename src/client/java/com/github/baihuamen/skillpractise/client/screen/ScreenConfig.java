package com.github.baihuamen.skillpractise.client.screen;

import com.github.baihuamen.skillpractise.client.config.ConfigManager;
import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.EventListener;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

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
                configMap.forEach((configName, configValue) -> {
                    if (configValue instanceof BooleanValue) {
                        ButtonWidget buttonWidget = ButtonWidget
                                .builder(Text.of(String.valueOf(((BooleanValue) configValue).value)), button -> {
                                    ((BooleanValue) configValue).invert();
                                    button.setMessage(Text.of(String.valueOf(((BooleanValue) configValue).value)));
                                })
                                .dimensions(this.width / 2 - 100, this.height / 6 + 48, 200, 20)
                                .build();

                        addDrawableChild(buttonWidget);
                    }
                });
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, float delta) {
                super.render(context, mouseX, mouseY, delta);
                configMap.forEach((configName, configValue) -> {
                    if (configValue instanceof BooleanValue) {
                        context.drawTextWithShadow(this.textRenderer, Text.of(configName ), this.width / 2 - 100, this.height / 6 + 48, 0xFFFFFF);
                    }
                });
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
}
