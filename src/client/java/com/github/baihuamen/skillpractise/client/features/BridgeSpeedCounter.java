package com.github.baihuamen.skillpractise.client.features;

import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;
import com.github.baihuamen.skillpractise.client.screen.ScreenConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static com.github.baihuamen.skillpractise.client.utils.MinecraftUtils.mc;
import static com.github.baihuamen.skillpractise.client.utils.render.RendererUtils.renderNumber;

public class BridgeSpeedCounter extends ScreenConfig {

    public static double speedPerSecond;
    public static double speedPerSecondGround;
    public static double speedInTwiceJump;
    public static double speedFlash;

    private boolean wasGrounding;
    private int startJumpTick;
    private Vec3d startJumpPosition;
    private Vec3d lastSecondPositon;

    private int tickCounter = 0;
    private long checkMileSecond = 0;

    private BooleanValue isDisplaySpeedPerSecond = Boolean("DisplayerSpeedPerSecond", true);
    private BooleanValue isDisplaySpeedPerSecondGround = Boolean("DisplaySpeedPerSecondGround", true);
    private BooleanValue isDisplaySpeedInTwiceJump = Boolean("DisplaySpeedInTwiceJump", true);
    private BooleanValue isDisplaySpeedFlash = Boolean("DisplaySpeedFlash", true);

    @Override
    public String name() {
        return "BridgeSpeedCounter";
    }

    @Override
    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, Identifier.of("bridgespeecounter", "skillpractise/skillpractisehud"), (context, tickCounter) -> {
            if (mc().player == null) return;
            render(context);
        }));
    }

    private final Class<?> tickHandler = registerEvent(TickEvent.class, event -> {
        tickCounter++;
        if (mc().player == null) return;
        calculateSpeedInTwiceJump();
        calculateSpeedPerSecond();
        calculateSpeedFlash();
        wasGrounding = mc().player.isOnGround();
    });

    private static void calculateSpeedFlash() {
        Vec3d currentPos = mc().player.getPos();
        Vec3d prevPos = new Vec3d(mc().player.prevX, mc().player.prevY, mc().player.prevZ);
        speedFlash = currentPos.distanceTo(prevPos) / 0.05;
    }

    private void calculateSpeedPerSecond() {
        if (System.currentTimeMillis() - checkMileSecond >= 1000) {
            if (lastSecondPositon != null) {
                speedPerSecond = mc().player.getPos().distanceTo(lastSecondPositon);
                speedPerSecondGround = Math.sqrt(Math.pow(mc().player.getPos().x - lastSecondPositon.x, 2) + Math.pow(mc().player.getPos().z - lastSecondPositon.z, 2));
            }
            lastSecondPositon = mc().player.getPos();

            checkMileSecond = System.currentTimeMillis();
        }
    }

    private void calculateSpeedInTwiceJump() {
        if (wasGrounding != mc().player.isOnGround() && mc().player.isOnGround() == false) {
            startJumpPosition = mc().player.getPos();
            startJumpTick = tickCounter;
        }
        if (wasGrounding != mc().player.isOnGround() && mc().player.isOnGround()) {
            if (startJumpPosition != null) {
                speedInTwiceJump = (mc().player.getPos()
                        .distanceTo(startJumpPosition)) / ((tickCounter - startJumpTick) * 0.05);
            }
        }
    }


    private void render(DrawContext context) {
        if (isDisplaySpeedFlash.value) {
            renderNumber(context, speedFlash, "speedFlash:", 5, 0, 10, 0xFFFFFF, true);
        }
        if (isDisplaySpeedPerSecond.value) {
            renderNumber(context, speedPerSecond, "speedPerSecond:", 5, 0, 20, 0xFFFFFF, true);
        }
        if (isDisplaySpeedPerSecondGround.value) {
            renderNumber(context, speedPerSecondGround, "speedPerSecondGround:", 5, 0, 30, 0xFFFFFF, true);
        }
        if (isDisplaySpeedInTwiceJump.value) {
            renderNumber(context, speedInTwiceJump, "speedInTwiceJump:", 5, 0, 40, 0xFFFFFF, true);
        }
    }
}
