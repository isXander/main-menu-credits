package dev.isxander.mainmenucredits;

import dev.isxander.mainmenucredits.config.MMCConfig;
import net.fabricmc.api.ClientModInitializer;

public class MainMenuCredits implements ClientModInitializer {
    private static MainMenuCredits instance;

    private MMCConfig config;

    @Override
    public void onInitializeClient() {
        instance = this;

        config = new MMCConfig();
        config.load();
    }

    public MMCConfig getConfig() {
        return config;
    }

    public static MainMenuCredits getInstance() {
        return instance;
    }
}
