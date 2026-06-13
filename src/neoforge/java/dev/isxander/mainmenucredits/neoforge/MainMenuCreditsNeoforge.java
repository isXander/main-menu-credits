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

import dev.isxander.mainmenucredits.MainMenuCredits;
import dev.isxander.mainmenucredits.platform.MainMenuCreditsPlatform;
import net.neoforged.fml.common.Mod;

@Mod("main_menu_credits")
public class MainMenuCreditsNeoforge {
	public MainMenuCreditsNeoforge() {
		MainMenuCreditsPlatform.ApiImpl.INSTANCE = new MainMenuCreditsNeoforgePlatform();

		MainMenuCredits.getInstance().onInitializeClient();
	}
}
