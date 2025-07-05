package com.github.baihuamen.skillpractise.client.event.events.returnableevents;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public record PlayerInteractionBlockEvent(ActionResult actionResult, Hand hand,BlockHitResult blockHitResult) {}
