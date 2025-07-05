package com.github.baihuamen.skillpractise.client.screen;

import com.github.baihuamen.skillpractise.client.event.EventListener;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.features.BridgeSpeedCounter;
import com.github.baihuamen.skillpractise.client.features.CPSCounterHud;
import com.github.baihuamen.skillpractise.client.features.InteractBlockTips;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class ScreenManager extends EventListener {


    private Screen screen;
    private final Map<Class<? extends ScreenConfig>, ScreenConfig> screensMap = new HashMap<>();


    private static KeyBinding openKey;
    private final Class<?> tickHandler = registerEvent(TickEvent.class,event-> {
        if (openKey.wasPressed()) {
            mc.setScreen(screen);
        }
    });

    public static void register() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.skillpractise.screenmanager.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "SkillPractise"));
    }

    public ScreenManager() {
            screensMap.put(SkillPractiseScreen.class, new SkillPractiseScreen());
        screensMap.put(BridgeSpeedCounter.class, new BridgeSpeedCounter());
        screensMap.put(CPSCounterHud.class, new CPSCounterHud());
        screensMap.put(InteractBlockTips.class, new InteractBlockTips());
        screensMap.forEach((screenConfigClass, screenConfig) -> {
            screenConfig.initialize();
        });
        screen = new Screen(Text.of("SkillPractise")) {
            @Override
            protected void init() {
                super.init();
                int screenIndex = 0;
                int indexX = 0;
                int indexY = 0;
                for (Map.Entry<Class<? extends ScreenConfig>, ScreenConfig> screenConfig : screensMap.entrySet()) {
                    ButtonWidget buttonWidget = ButtonWidget.builder(Text.of(screenConfig.getValue()
                                    .name()), onPress -> {
                                client.setScreen(screenConfig.getValue().screen);
                            })
                            .dimensions((indexX + 1) * this.width / 5 + 10, (indexY + 1) * this.height / 5 + 10, this.width / 6, this.height / 9)
                            .build();
                    addDrawableChild(buttonWidget);
                    indexX++;
                    if (indexX >= 4) {
                        indexX = 0;
                        indexY++;
                    }
                    if (indexY >= 4) {
                        break;
                    }
                }
            }
        };
    }
}
