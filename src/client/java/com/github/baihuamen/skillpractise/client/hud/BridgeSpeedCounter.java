package com.github.baihuamen.skillpractise.client.hud;

import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.Event;
import com.github.baihuamen.skillpractise.client.event.events.TickEvent;
import com.github.baihuamen.skillpractise.client.screen.ScreenConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

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

    private static final MinecraftClient client = MinecraftClient.getInstance();

    private static KeyBinding openKey;

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
            if (client.player == null) return;
            render(context);
        }));

        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.skillpractise.bridgespeedcounter.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "SkillPractise"));
    }

    private final Class<? extends Event> tickHandler = registerEvent(TickEvent.class, () -> {
        tickCounter++;
        if (client.player == null) return;
        if (wasGrounding != client.player.isOnGround() && client.player.isOnGround() == false) {
            startJumpPosition = client.player.getPos();
            startJumpTick = tickCounter;
        }
        if (wasGrounding != client.player.isOnGround() && client.player.isOnGround()) {
            if (startJumpPosition != null) {
                speedInTwiceJump = (client.player.getPos()
                        .distanceTo(startJumpPosition)) / ((tickCounter - startJumpTick) * 0.05);
            }
        }
        if (System.currentTimeMillis() - checkMileSecond >= 1000) {
            if (lastSecondPositon != null) {
                speedPerSecond = client.player.getPos().distanceTo(lastSecondPositon) / 1000L;
                speedPerSecondGround = Math.sqrt(Math.pow(client.player.getPos().x - lastSecondPositon.x, 2) + Math.pow(client.player.getPos().z - lastSecondPositon.z, 2));
            }
            lastSecondPositon = client.player.getPos();
        }
        speedFlash = client.player.speed;
        wasGrounding = client.player.isOnGround();

        if(openKey.wasPressed()){
            client.setScreen(screen);
        }
    });


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
