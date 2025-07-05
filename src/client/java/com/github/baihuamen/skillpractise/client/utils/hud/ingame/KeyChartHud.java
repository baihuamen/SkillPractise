/*
 * Copyright (C) ${YEAR} github.com/baihuamen/SkillPractise
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * ---
 *
 * 版权所有 (C) ${YEAR} github.com/baihuamen/SkillPractise
 *
 * 本程序是自由软件，您可以自由地重新发布或修改它，但必须遵循
 * GNU通用公共许可证（版本3或更高版本）的条款。
 *
 * 本程序没有任何担保，包括适销性或特定用途适用性的暗示担保。
 * 详情请参阅GNU通用公共许可证。
 *
 * 您应该已经随本程序收到了一份GNU通用公共许可证的副本。如果没有，
 * 请访问 <https://www.gnu.org/licenses/> 查看。
 */

package com.github.baihuamen.skillpractise.client.utils.hud.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.debug.DebugChart;
import net.minecraft.util.profiler.log.MultiValueDebugSampleLog;

import static java.lang.Math.min;

@Environment(EnvType.CLIENT)
public class KeyChartHud extends DebugChart {
    public String name = "";
    public boolean enabled = true;
    public int maxHeight = 40;

    public KeyChartHud(TextRenderer textRenderer, MultiValueDebugSampleLog log, boolean enabled, String name) {
        super(textRenderer, log);
        this.enabled = enabled;
        this.name = name;
    }

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
        return min(maxHeight, (int) value);
    }

    @Override
    protected int getColor(long value) {
        return this.getColor(value, 0.0, -16711936, 250.0, -256, 500.0, -65536);
    }

    @Override
    public void render(DrawContext drawContext, int x, int width) {
        if (!enabled) return;
        super.render(drawContext, x, width);
    }

    @Override
    protected void renderThresholds(DrawContext context, int x, int width, int height) {
        drawBorderedText(context, name, x + 1, height * 3/4);
    }
}
