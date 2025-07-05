package com.github.baihuamen.skillpractise.client.utils.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;

public class RendererUtils {
    public static void renderText(@NotNull DrawContext context, String text, int x, int y, int color, boolean shadow) {
        context.drawText(mc().textRenderer, Text.of(text), x, y, color, shadow);
    }

    public static void renderNumber(DrawContext context, double number, int length, int x, int y, int color, boolean shadow) {
        renderNumber(context, number, "", length, x, y, color, shadow);
        }

        public static void renderNumber(DrawContext context, double number, String prefix, int length, int x, int y, int color, boolean shadow) {
            if (String.valueOf(number).length() <= length) {
                renderText(context, prefix + number, x, y, color,shadow);
        } else if (String.valueOf(number).length() > length) {
            renderText(context, prefix + String.valueOf(number).substring(0, length), x, y, color, shadow);
        }
    }
}
