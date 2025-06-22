package com.github.baihuamen.skillpractise.client.utils.keystroke;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.HashMap;
import java.util.Map;


@Environment(EnvType.CLIENT)
public class KeyStroke {
    public static Map<KeyStrokeType, KeyState> updateMap= new HashMap<>(
            Map.of(
                    KeyStrokeType.W,new KeyState(false,0),
                    KeyStrokeType.S,new KeyState(false,0),
                    KeyStrokeType.A,new KeyState(false,0),
                    KeyStrokeType.D,new KeyState(false,0),
                    KeyStrokeType.SPACE,new KeyState(false,0)

            )
    );

    public static void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().options.backKey.isPressed()) {
                updateMap.get(KeyStrokeType.S).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.S).interval != 0){
                updateMap.get(KeyStrokeType.S).setReleased(true);
            }

            if (MinecraftClient.getInstance().options.forwardKey.isPressed()) {
                updateMap.get(KeyStrokeType.W).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.W).interval!= 0){
                updateMap.get(KeyStrokeType.W).setReleased(true);
            }

            if (MinecraftClient.getInstance().options.leftKey.isPressed()) {
                updateMap.get(KeyStrokeType.A).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.A).interval!= 0) {
                updateMap.get(KeyStrokeType.A).setReleased(true);
            }

            if (MinecraftClient.getInstance().options.rightKey.isPressed()) {
                updateMap.get(KeyStrokeType.D).addInterval();
            }
            else if (updateMap.get(KeyStrokeType.D).interval!= 0) {
                updateMap.get(KeyStrokeType.D).setReleased(true);
            }
        });
    }
}

