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

import dev.isxander.mainmenucredits.MainMenuCredits;
import dev.isxander.mainmenucredits.platform.MainMenuCreditsPlatform;
import net.fabricmc.api.ClientModInitializer;

public class MainMenuCreditsFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MainMenuCreditsPlatform.ApiImpl.INSTANCE = new MainMenuCreditsFabricPlatform();

		MainMenuCredits.getInstance().onInitializeClient();
	}
}
