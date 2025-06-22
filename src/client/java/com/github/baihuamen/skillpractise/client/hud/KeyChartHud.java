package com.github.baihuamen.skillpractise.client.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.debug.DebugChart;
import net.minecraft.util.profiler.log.MultiValueDebugSampleLog;

public class KeyChartHud extends DebugChart {
    protected KeyChartHud(TextRenderer textRenderer, MultiValueDebugSampleLog log) {
        super(textRenderer, log);
    }

    @Override
    protected String format(double value) {
        return "";
    }

    @Override
    protected int getHeight(double value) {
        return 40;
    }

    @Override
    protected int getColor(long value) {
        return this.getColor(value, 0.0, -16711936, 250.0, -256, 500.0, -65536);
    }
}
