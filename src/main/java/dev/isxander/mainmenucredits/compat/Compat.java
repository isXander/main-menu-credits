package dev.isxander.mainmenucredits.compat;

import net.fabricmc.loader.api.FabricLoader;

public class Compat {
    public static final boolean MINIMAL_MENU = FabricLoader.getInstance().isModLoaded("minimalmenu");

    public static int getTitleScreenBottomRightOffset() {
        return MINIMAL_MENU && MinimalMenuCompat.removeCopyright() ? 10 : 20;
    }
}
