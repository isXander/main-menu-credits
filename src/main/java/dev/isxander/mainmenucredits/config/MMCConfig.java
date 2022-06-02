package dev.isxander.mainmenucredits.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class MMCConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("isxander-main-menu-credits.json");

    public final MMCConfigEntry MAIN_MENU = new MMCConfigEntry("main_menu");
    public final MMCConfigEntry PAUSE_MENU = new MMCConfigEntry("pause_menu");

    public void createEmpty() {
        try {
            var root = new JsonObject();

            MAIN_MENU.createEmpty(root);
            PAUSE_MENU.createEmpty(root);

            Files.deleteIfExists(CONFIG_PATH);
            Files.writeString(CONFIG_PATH, GSON.toJson(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                createEmpty();
                return;
            }

            var root = GSON.fromJson(Files.readString(CONFIG_PATH), JsonObject.class);

            MAIN_MENU.load(root);
            PAUSE_MENU.load(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
