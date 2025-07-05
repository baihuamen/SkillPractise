package com.github.baihuamen.skillpractise.client.features.mainfeatures;

import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.PlayerInteractionBlockEvent;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;
import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.player;

public class InteractBlockTips extends ScreenConfig {


    private BooleanValue enable = Boolean("Enable", true);

    @Override
    public String name() {
        return "InteractBlockTips";
    }

    private Class<?> interactionHandler = registerEvent(PlayerInteractionBlockEvent.class, event -> {
        if (!enable.value) return;
        var hitResult = player().raycast(4.5, 0, false);
        if (!(hitResult instanceof BlockHitResult)) return;
        hitResult = (BlockHitResult) hitResult;
        if (!mc().options.useKey.isPressed()) return;
        if (((BlockHitResult) hitResult).getSide() == Direction.UP) {
            // 绿色字体消息
            player().sendMessage(Text.of("交互过早，交互到了方块上方"), true);
        }
        if (hitResult.getType() == HitResult.Type.MISS) {
            player().sendMessage(Text.of("交互过晚，没有交互到方块"), true);
        }
        if (event.actionResult() == ActionResult.SUCCESS)
            player().sendMessage(Text.of("交互成功"), true);
    });
}
