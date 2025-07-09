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

import com.github.baihuamen.skillpractise.client.event.EventManager;
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.NetworkC2SPacketEvent;
import com.github.baihuamen.skillpractise.client.event.events.returnableevents.NetworkS2CPacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinsNetworkHandler{

    @Inject(method = "handlePacket", at = @At("HEAD"), require = 1)
    private static void hookReceivedPackets(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        EventManager.INSTANCE.callEvent(new NetworkS2CPacketEvent(packet));
    }
    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"))
    private void hookSendPacket(Packet<?> packet, CallbackInfo ci) {
        EventManager.INSTANCE.callEvent(new NetworkC2SPacketEvent(packet));
    }

}
