package com.github.baihuamen.skillpractise.client.screen;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SkillPractiseScreen {

    public static KeyBinding openKey;
    public static Screen screen;

    public static void register() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ConfigBuilder builder = ConfigBuilder.create().setTitle(Text.of("SkillPractise"));
            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.skillpractise.general"));
            builder.setFallbackCategory(general);
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            general.addEntry(entryBuilder.startBooleanToggle(Text.of("Display Forward Key Chart"), true)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> {
                        SkillPractiseScreenConfig.enabledForwardKeyChart = newValue;
                    })
                    .setTooltip(Text.translatable("tooltip.skillpractise.display_forward_key_chart"))
                    .build());
            general.addEntry(entryBuilder.startBooleanToggle(Text.of("Display Backward Key Chart"), true)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> {
                        SkillPractiseScreenConfig.enabledBackKeyChart = newValue;
                    })
                    .setTooltip(Text.translatable("tooltip.skillpractise.display_backward_key_chart"))
                    .build());
            general.addEntry(entryBuilder.startBooleanToggle(Text.of("Display Left Key Chart"), true)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> {
                        SkillPractiseScreenConfig.enabledLeftKeyChart = newValue;
                    })
                    .setTooltip(Text.translatable("tooltip.skillpractise.display_left_key_chart"))
                    .build());
            general.addEntry(entryBuilder.startBooleanToggle(Text.of("Display Right Key Chart"), true)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> {
                        SkillPractiseScreenConfig.enabledRightKeyChart = newValue;
                    })
                    .setTooltip(Text.translatable("tooltip.skillpractise.display_right_key_chart"))
                    .build());
            screen = builder.build();
        });
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.skillpractise.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "SkillPractise"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openKey.wasPressed()) {
                client.setScreen(screen);
            }
        });
    }
}
