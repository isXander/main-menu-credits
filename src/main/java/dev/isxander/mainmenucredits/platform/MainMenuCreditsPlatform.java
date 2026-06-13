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

import java.nio.file.Path;
import java.util.List;

public interface MainMenuCreditsPlatform {
	List<MainMenuCreditAPI> getIntegrations();

	Path getConfigDir();

	TextOffsets getMainMenuTextOffsets();

	TextOffsets getPauseMenuTextOffsets();

	static MainMenuCreditsPlatform get() {
		return ApiImpl.INSTANCE;
	}

	class ApiImpl {
		public static MainMenuCreditsPlatform INSTANCE = null;
	}
}
