package com.github.baihuamen.skillpractise.client.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.debug.DebugChart;
import net.minecraft.util.profiler.log.MultiValueDebugSampleLog;

@Environment(EnvType.CLIENT)
public class KeyChartHud extends DebugChart {



    public boolean enabled = true;

    protected KeyChartHud(TextRenderer textRenderer, MultiValueDebugSampleLog log, boolean enabled) {
        super(textRenderer, log);
        this.enabled = enabled;
    }

    protected KeyChartHud(TextRenderer textRenderer, MultiValueDebugSampleLog log) {
        super(textRenderer, log);
    }

    @Override
    protected String format(double value) {
        return "";
    }

        @Override
        protected int getHeight(double value) {
            return (int) value;
        }

        @Override
        protected int getColor(long value) {
            return this.getColor(value, 0.0, -16711936, 250.0, -256, 500.0, -65536);
    }

    @Override
    public void render(DrawContext drawContext, int x, int width){
        if (!enabled) return;
        super.render(drawContext, x, width);
    }
}
