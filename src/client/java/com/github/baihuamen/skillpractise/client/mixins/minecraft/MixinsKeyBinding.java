package com.github.baihuamen.skillpractise.client.mixins.minecraft;

import com.github.baihuamen.skillpractise.client.features.mainfeatures.CPSCounterHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public abstract class MixinsKeyBinding {

    @Shadow
    public abstract boolean equals(KeyBinding other);

    @Inject(method = "setPressed", at = @At("HEAD"), cancellable = false)
    private void setPressed(boolean pressed, CallbackInfo ci) {
        if (this.equals(MinecraftClient.getInstance().options.attackKey) && pressed) {
            CPSCounterHud.leftClickMap.put(System.currentTimeMillis(), true);
        }
        if (this.equals(MinecraftClient.getInstance().options.useKey) && pressed) {
            CPSCounterHud.rightClickMap.put(System.currentTimeMillis(), true);
        }
    }
}

