package com.github.baihuamen.skillpractise.client.keystroke;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class KeyStroke {
    public static int wInterval = 0;
    public static int sInterval = 0;
    public static int aInterval = 0;
    public static int dInterval = 0;
    public static int spaceInterval = 0;

    public static Map<String,Boolean> updateMap= new HashMap<>(
            Map.of(
                    "w",false,
                    "s",false,
                    "a",false,
                    "d",false,
                    "space",false
            )
    );




    public static void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().options.backKey.isPressed()) {
                sInterval++;
            }
            else if (sInterval != 0){
                updateMap.put("s",true);
            }

            if (MinecraftClient.getInstance().options.forwardKey.isPressed()) {
                wInterval++;
            }
            else {
                updateMap.put("w",true);
            }

            if (MinecraftClient.getInstance().options.leftKey.isPressed()) {
                aInterval++;
            }
            else {
                updateMap.put("a",true);
            }

            if (MinecraftClient.getInstance().options.rightKey.isPressed()) {
                dInterval++;
            }
            else {
                updateMap.put("d",true);
            }

            if (MinecraftClient.getInstance().options.jumpKey.isPressed()) {
                spaceInterval++;
            }
            else {
                updateMap.put("space",true);
            }
        });
    }


}
