/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits;

import com.mojang.logging.LogUtils;
import dev.isxander.mainmenucredits.config.MMCConfig;
import dev.isxander.mainmenucredits.platform.MainMenuCreditsPlatform;
import org.slf4j.Logger;

public class MainMenuCredits {
	public static final Logger LOGGER = LogUtils.getLogger();

	private static MainMenuCredits instance;

	private MMCConfig config;

	public void onInitializeClient() {
		config = new MMCConfig();
		config.load();

		var integrations = MainMenuCreditsPlatform.get().getIntegrations();
		for (var api : integrations) {

			if (api.getModId() == null || !config.MAIN_MENU.getModBlacklist().contains(api.getModId())) {
				config.MAIN_MENU.getTopLeft().addAll(api.getTitleScreenTopLeft());
				config.MAIN_MENU.getTopRight().addAll(api.getTitleScreenTopRight());
				config.MAIN_MENU.getBottomLeft().addAll(api.getTitleScreenBottomLeft());
				config.MAIN_MENU.getBottomRight().addAll(api.getTitleScreenBottomRight());
			}

			if (api.getModId() == null || !config.PAUSE_MENU.getModBlacklist().contains(api.getModId())) {
				config.PAUSE_MENU.getTopLeft().addAll(api.getPauseScreenTopLeft());
				config.PAUSE_MENU.getTopRight().addAll(api.getPauseScreenTopRight());
				config.PAUSE_MENU.getBottomLeft().addAll(api.getPauseScreenBottomLeft());
				config.PAUSE_MENU.getBottomRight().addAll(api.getPauseScreenBottomRight());
			}
		}
	}

	public MMCConfig getConfig() {
		return config;
	}

	public static MainMenuCredits getInstance() {
		if (instance == null) {
			instance = new MainMenuCredits();
		}
		return instance;
	}
}
