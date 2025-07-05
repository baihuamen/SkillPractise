package com.github.baihuamen.skillpractise.client.features;

import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.screen.ScreenConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.HashMap;
import java.util.Map;

import static com.github.baihuamen.skillpractise.client.utils.render.RendererUtils.renderText;

public class CPSCounterHud extends ScreenConfig {

    public BooleanValue showCPS = Boolean("ShowCPS", true);

    public static Map<Long, Boolean> leftClickMap = new HashMap<>();
    public static Map<Long, Boolean> rightClickMap = new HashMap<>();

    @Override
    public String name() {
        return "CPSCounterHud";
    }


    public CPSCounterHud() {
    }

    @Override
    public void onRenderEvent(DrawContext context, RenderTickCounter tickCounter) {
        if (showCPS.value) {
            render(context);
        }
    }

    private void render(DrawContext context) {
        int leftClickCounter = 0;
        int rightClickCounter = 0;
        for (int i = 0; i < 1000; i++) {
            if (leftClickMap.getOrDefault(System.currentTimeMillis() - i, false)) {
                leftClickCounter++;
            }
            if (rightClickMap.getOrDefault(System.currentTimeMillis() - i, false)) {
                rightClickCounter++;
            }
        }
        renderText(context, "LeftCPS: " + leftClickCounter, 0, 60, 0xFFFFFF, true);
        renderText(context, "RightCPS: " + rightClickCounter, 0, 70, 0xFFFFFF, true);
    }
}
