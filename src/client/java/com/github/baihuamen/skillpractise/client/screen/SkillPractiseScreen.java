package com.github.baihuamen.skillpractise.client.screen;

import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.Event;
import com.github.baihuamen.skillpractise.client.event.events.OnInitializeClientEvent;
import com.github.baihuamen.skillpractise.client.event.events.TickEvent;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SkillPractiseScreen extends ScreenConfig {

    public static KeyBinding openKey;

    private BooleanValue forwardKeyChartDisplay = Boolean("ForwardKeyChartDisplay", true);
    private BooleanValue backKeyChartDisplay = Boolean("BackKeyChartDisplay", true);
    private BooleanValue leftKeyChartDisplay = Boolean("LeftKeyChartDisplay", true);
    private BooleanValue rightKeyChartDisplay = Boolean("RightKeyChartDisplay", true);


    private Class<? extends Event> registerScreen = registerEvent(OnInitializeClientEvent.class, () -> {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.skillpractise.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "SkillPractise"));
    });

    private Class<? extends Event> tickHandler = registerEvent(TickEvent.class, () -> {
        if (openKey.wasPressed()) {
            MinecraftClient.getInstance().setScreen(screen);
        }
        SkillPractiseScreenConfig.enabledBackKeyChart = forwardKeyChartDisplay.value;
        SkillPractiseScreenConfig.enabledForwardKeyChart = backKeyChartDisplay.value;
        SkillPractiseScreenConfig.enabledLeftKeyChart = leftKeyChartDisplay.value;
        SkillPractiseScreenConfig.enabledRightKeyChart = rightKeyChartDisplay.value;
    });

    @Override
    public String name() {
        return "SkillPractise";
    }
}
