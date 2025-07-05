package com.github.baihuamen.skillpractise.client.features.mainfeatures.keycharthud;

import com.github.baihuamen.skillpractise.client.config.utils.values.BooleanValue;
import com.github.baihuamen.skillpractise.client.features.ScreenConfig;

public class KeyChartHudConfigScreen extends ScreenConfig {

    public BooleanValue forwardKeyChartDisplay = Boolean("ForwardKeyChartDisplay", true);
    public BooleanValue backKeyChartDisplay = Boolean("BackKeyChartDisplay", true);
    public BooleanValue leftKeyChartDisplay = Boolean("LeftKeyChartDisplay", true);
    public BooleanValue rightKeyChartDisplay = Boolean("RightKeyChartDisplay", true);


    @Override
    public String name() {
        return "SkillPractise";
    }
}
