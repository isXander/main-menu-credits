/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenAccessor {
	@Invoker
	static void callDefaultHandleGameClickEvent(final ClickEvent event, final Minecraft minecraft, @Nullable final Screen activeScreen) {
		throw new AssertionError();
	}

	@Invoker
	static void callDefaultHandleClickEvent(final ClickEvent event, final Minecraft minecraft, @Nullable final Screen activeScreen) {
		throw new AssertionError();
	}
}
