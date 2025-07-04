package com.github.baihuamen.skillpractise.client.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;

public final class MinecraftUtils {
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public static PlayerEntity player = mc.player;
    public static ClientPlayerInteractionManager interactionManager = mc.interactionManager;
}
