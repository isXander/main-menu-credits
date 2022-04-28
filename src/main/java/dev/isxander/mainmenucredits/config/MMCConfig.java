package dev.isxander.mainmenucredits.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MMCConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("isxander-main-menu-credits.json");

    private final List<Text> topLeft = new ArrayList<>();
    private final List<Text> topRight = new ArrayList<>();
    private final List<Text> bottomLeft = new ArrayList<>();
    private final List<Text> bottomRight = new ArrayList<>();

    public void createEmpty() {
        try {
            var root = new JsonObject();
            root.add("top_left", new JsonArray());
            root.add("top_right", new JsonArray());
            root.add("bottom_left", new JsonArray());
            root.add("bottom_right", new JsonArray());

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

            var topLeftJson = root.getAsJsonArray("top_left");
            topLeftJson.forEach((element) -> topLeft.add(Text.Serializer.fromJson(element)));

            var topRightJson = root.getAsJsonArray("top_right");
            topRightJson.forEach((element) -> topRight.add(Text.Serializer.fromJson(element)));

            var bottomLeftJson = root.getAsJsonArray("bottom_left");
            bottomLeftJson.forEach((element) -> bottomLeft.add(Text.Serializer.fromJson(element)));

            var bottomRightJson = root.getAsJsonArray("bottom_right");
            bottomRightJson.forEach((element) -> bottomRight.add(Text.Serializer.fromJson(element)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Text> getTopLeft() {
        return topLeft;
    }

    public List<Text> getTopRight() {
        return topRight;
    }

    public List<Text> getBottomLeft() {
        return bottomLeft;
    }

    public List<Text> getBottomRight() {
        return bottomRight;
    }
}
