package com.github.baihuamen.skillpractise.client.screen;

import com.github.baihuamen.skillpractise.client.config.utils.BooleanValue;
import com.github.baihuamen.skillpractise.client.event.Event;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.TickEvent;

public class SkillPractiseScreen extends ScreenConfig {
    private BooleanValue forwardKeyChartDisplay = Boolean("ForwardKeyChartDisplay", true);
    private BooleanValue backKeyChartDisplay = Boolean("BackKeyChartDisplay", true);
    private BooleanValue leftKeyChartDisplay = Boolean("LeftKeyChartDisplay", true);
    private BooleanValue rightKeyChartDisplay = Boolean("RightKeyChartDisplay", true);


    private Class<? extends Event> tickHandler = registerEvent(TickEvent.class, () -> {
        SkillPractiseScreenConfig.enabledBackKeyChart = forwardKeyChartDisplay.value;
        SkillPractiseScreenConfig.enabledForwardKeyChart = backKeyChartDisplay.value;
        SkillPractiseScreenConfig.enabledLeftKeyChart = leftKeyChartDisplay.value;
        SkillPractiseScreenConfig.enabledRightKeyChart = rightKeyChartDisplay.value;
    });

    @Override
    public String name() {
        return "SkillPractise";
    }
}
