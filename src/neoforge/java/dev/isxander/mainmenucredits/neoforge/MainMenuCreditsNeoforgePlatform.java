/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits.neoforge;

import dev.isxander.mainmenucredits.api.MainMenuCreditAPI;
import dev.isxander.mainmenucredits.platform.MainMenuCreditsPlatform;
import dev.isxander.mainmenucredits.platform.PlatformUtil;
import dev.isxander.mainmenucredits.platform.TextOffsets;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.List;

public class MainMenuCreditsNeoforgePlatform implements MainMenuCreditsPlatform {
	@Override
	public List<MainMenuCreditAPI> getIntegrations() {
		// TODO: source mod id from class impls
		return PlatformUtil.getIntegrationsFromServiceLoader().toList();
	}

	@Override
	public Path getConfigDir() {
		return FMLPaths.CONFIGDIR.get();
	}

	@Override
	public TextOffsets getMainMenuTextOffsets() {
		return new TextOffsets(
				0, 0, 10, 0
		);
	}

	@Override
	public TextOffsets getPauseMenuTextOffsets() {
		return new TextOffsets(0, 0, 0, 0);
	}
}
