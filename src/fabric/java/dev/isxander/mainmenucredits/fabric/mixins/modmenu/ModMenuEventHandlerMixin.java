/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits.fabric.mixins.modmenu;

import com.terraformersmc.modmenu.event.ModMenuEventHandler;
import dev.isxander.mainmenucredits.gui.MMCPlainTextButton;
import net.minecraft.client.gui.layouts.LayoutElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = ModMenuEventHandler.class)
public class ModMenuEventHandlerMixin {
	// for ModMenu >=6.2.0
	@Surrogate
	@Inject(method = "shiftButtons", at = @At("HEAD"), cancellable = true, require = 0)
	private static void cancelShiftButtons(LayoutElement widget, boolean shiftUp, int spacing, CallbackInfo ci) {
		if (widget instanceof MMCPlainTextButton) {
			ci.cancel();
		}
	}
}
