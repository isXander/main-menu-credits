/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits.api;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface MainMenuCreditAPI {
	default List<Component> getTitleScreenTopLeft() {
		return new ArrayList<>();
	}

	default List<Component> getTitleScreenTopRight() {
		return new ArrayList<>();
	}

	default List<Component> getTitleScreenBottomLeft() {
		return new ArrayList<>();
	}

	default List<Component> getTitleScreenBottomRight() {
		return new ArrayList<>();
	}

	default List<Component> getPauseScreenTopLeft() {
		return new ArrayList<>();
	}

	default List<Component> getPauseScreenTopRight() {
		return new ArrayList<>();
	}

	default List<Component> getPauseScreenBottomLeft() {
		return new ArrayList<>();
	}

	default List<Component> getPauseScreenBottomRight() {
		return new ArrayList<>();
	}

	default @Nullable String getModId() {
		return null;
	}
}
