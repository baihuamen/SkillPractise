package com.github.baihuamen.skillpractise.client;

import com.github.baihuamen.skillpractise.client.hud.SkillPractiseHud;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStroke;
import net.fabricmc.api.ClientModInitializer;
import org.jetbrains.annotations.Nullable;

public class SkillPractiseClient implements ClientModInitializer {



    @Nullable
    private SkillPractiseHud skillPractiseHud;
    private final KeyStroke keyStroke;


    public SkillPractiseClient() {
        this.skillPractiseHud = null;
        this.keyStroke = new KeyStroke();
    }
    @Override
    public void onInitializeClient() {


            skillPractiseHud = new SkillPractiseHud();
            skillPractiseHud.register();
            KeyStroke.register();

    }
}
