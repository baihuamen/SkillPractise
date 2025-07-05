package com.github.baihuamen.skillpractise.client.mixins.minecraft;

import com.github.baihuamen.skillpractise.client.event.EventManager;
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.PlayerInteractionEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinsClientInteraction {
    @Inject(method = "interactItem",at = @At("HEAD"),cancellable = false)
    private void hookInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        EventManager.INSTANCE.callEvent(new PlayerInteractionEvent(hand));
    }

    @Inject(method = "interactBlock",at = @At("HEAD"),cancellable = false)
    private void hookInteractBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        EventManager.INSTANCE.callEvent(new PlayerInteractionEvent(hand));
    }
}
