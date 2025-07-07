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

package com.github.baihuamen.skillpractise.client.mixins.minecraft;

import com.github.baihuamen.skillpractise.client.features.mainconfigscreen.ScreenManager;
import com.github.baihuamen.skillpractise.client.features.mainfeatures.DisableCreativeFly;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinsPlayerEntity {
    @Inject(method = "getAbilities",at = @At("RETURN"), cancellable = true)
    private void hookGetAbilities(CallbackInfoReturnable<PlayerAbilities> cir) {
        if(ScreenManager.INSTANCE.getInstance(DisableCreativeFly.class).enabled.value){
            PlayerAbilities playerAbilities = new PlayerAbilities();
            playerAbilities.flying = false;
            playerAbilities.allowFlying = false;
            playerAbilities.creativeMode = cir.getReturnValue().creativeMode;
            playerAbilities.invulnerable = cir.getReturnValue().invulnerable;
            playerAbilities.allowModifyWorld = cir.getReturnValue().allowModifyWorld;
            playerAbilities.setFlySpeed(cir.getReturnValue().getFlySpeed());
            playerAbilities.setWalkSpeed(cir.getReturnValue().getWalkSpeed());
            cir.setReturnValue(playerAbilities);
        }
    }
}
