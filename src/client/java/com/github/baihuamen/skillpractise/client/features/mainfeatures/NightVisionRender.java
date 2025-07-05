package com.github.baihuamen.skillpractise.client.features.mainfeatures;

import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.player;

public class NightVisionRender extends ScreenConfig {


    private final BooleanValue enable = Boolean("Enable", true);

    private final Class<?> tickHandler = registerEvent(TickEvent.class, event -> {
        if (player() == null) return;
        if (enable.value) {
            player().addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false));
        } else {
            player().removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    });

    @Override
    public String name() {
        return "NightVisionRender";
    }
}
