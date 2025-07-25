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

package com.github.baihuamen.skillpractise.client;

import com.github.baihuamen.skillpractise.client.config.ConfigManager;
import com.github.baihuamen.skillpractise.client.event.EventManager;
import com.github.baihuamen.skillpractise.client.event.events.commonevents.OnInitializeClientEvent;
import com.github.baihuamen.skillpractise.client.features.mainconfigscreen.ScreenManager;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.keycharthud.KeyChartInGameHud;
import com.github.baihuamen.skillpractise.client.utils.keystroke.KeyStroke;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SkillPractiseClient implements ClientModInitializer {

    @Nullable
    private KeyChartInGameHud keyChartInGameHud;

    public SkillPractiseClient() {
        this.keyChartInGameHud = null;
    }

    @Override
    public void onInitializeClient() {
        keyChartInGameHud = new KeyChartInGameHud();
        keyChartInGameHud.register();
        KeyStroke.register();
        ConfigManager.register();
        ScreenManager screenManager = new ScreenManager();
        ScreenManager.register();
        EventManager eventManager = new EventManager();
        EventManager.INSTANCE.callEvent(new OnInitializeClientEvent());
    }
}
