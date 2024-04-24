package dev.isxander.mainmenucredits;

import com.mojang.logging.LogUtils;
import dev.isxander.mainmenucredits.api.MainMenuCreditAPI;
import dev.isxander.mainmenucredits.config.MMCConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

public class MainMenuCredits implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();

    private static MainMenuCredits instance;

    private MMCConfig config;

    @Override
    public void onInitializeClient() {
        instance = this;

        config = new MMCConfig();
        config.load();

        var entrypoints = FabricLoader.getInstance().getEntrypointContainers("main-menu-credits", MainMenuCreditAPI.class);
        for (var container : entrypoints) {
            var api = container.getEntrypoint();

            if (!config.MAIN_MENU.getModBlacklist().contains(container.getProvider().getMetadata().getId())) {
                config.MAIN_MENU.getTopLeft().addAll(api.getTitleScreenTopLeft());
                config.MAIN_MENU.getTopRight().addAll(api.getTitleScreenTopRight());
                config.MAIN_MENU.getBottomLeft().addAll(api.getTitleScreenBottomLeft());
                config.MAIN_MENU.getBottomRight().addAll(api.getTitleScreenBottomRight());
            }

            if (!config.PAUSE_MENU.getModBlacklist().contains(container.getProvider().getMetadata().getId())) {
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
        return instance;
    }
}
