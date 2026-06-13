/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits.platform;

import dev.isxander.mainmenucredits.api.MainMenuCreditAPI;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public class PlatformUtil {
	private PlatformUtil() {}

	public static Stream<MainMenuCreditAPI> getIntegrationsFromServiceLoader() {
		return ServiceLoader.load(MainMenuCreditAPI.class)
				.stream()
				.map(ServiceLoader.Provider::get);
	}

	public static MainMenuCreditAPI withModId(MainMenuCreditAPI api, @Nullable String modId) {
		if (api.getModId() != null) {
			return api;
		}

		return new MainMenuCreditAPI() {
			@Override
			public List<Component> getTitleScreenTopLeft() {
				return api.getTitleScreenTopLeft();
			}

			@Override
			public List<Component> getTitleScreenTopRight() {
				return api.getTitleScreenTopRight();
			}

			@Override
			public List<Component> getTitleScreenBottomLeft() {
				return api.getTitleScreenBottomLeft();
			}

			@Override
			public List<Component> getTitleScreenBottomRight() {
				return api.getTitleScreenBottomRight();
			}

			@Override
			public List<Component> getPauseScreenTopLeft() {
				return api.getPauseScreenTopLeft();
			}

			@Override
			public List<Component> getPauseScreenTopRight() {
				return api.getPauseScreenTopRight();
			}

			@Override
			public List<Component> getPauseScreenBottomLeft() {
				return api.getPauseScreenBottomLeft();
			}

			@Override
			public List<Component> getPauseScreenBottomRight() {
				return api.getPauseScreenBottomRight();
			}

			@Override
			public @Nullable String getModId() {
				return modId;
			}
		};
	}
}
