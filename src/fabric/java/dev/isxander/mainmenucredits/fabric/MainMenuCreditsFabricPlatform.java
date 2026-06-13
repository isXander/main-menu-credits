/*
 * Copyright (C) 2026 isXander
 *
 * This file is part of Main Menu Credits.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.isxander.mainmenucredits.fabric;

import dev.isxander.mainmenucredits.api.MainMenuCreditAPI;
import dev.isxander.mainmenucredits.platform.MainMenuCreditsPlatform;
import dev.isxander.mainmenucredits.platform.PlatformUtil;
import dev.isxander.mainmenucredits.platform.TextOffsets;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class MainMenuCreditsFabricPlatform implements MainMenuCreditsPlatform {
	@Override
	public List<MainMenuCreditAPI> getIntegrations() {
		var fabricIntegrations = FabricLoader.getInstance().getEntrypointContainers("main-menu-credits", MainMenuCreditAPI.class)
				.stream()
				.map(container -> PlatformUtil.withModId(container.getEntrypoint(), container.getProvider().getMetadata().getId()));
		var serviceLoaderIntegrations = PlatformUtil.getIntegrationsFromServiceLoader();

		return Stream.concat(fabricIntegrations, serviceLoaderIntegrations).toList();
	}

	@Override
	public Path getConfigDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	public TextOffsets getMainMenuTextOffsets() {
		return new TextOffsets(0, 0, 0, 0);
	}

	@Override
	public TextOffsets getPauseMenuTextOffsets() {
		return new TextOffsets(0, 0, 0, 0);
	}
}
