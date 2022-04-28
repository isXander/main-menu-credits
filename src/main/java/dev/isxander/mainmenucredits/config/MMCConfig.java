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

    public void save() {
        try {
            var topLeftJson = new JsonArray();
            for (var text : topLeft) topLeftJson.add(Text.Serializer.toJsonTree(text));

            var topRightJson = new JsonArray();
            for (var text : topRight) topRightJson.add(Text.Serializer.toJsonTree(text));

            var bottomLeftJson = new JsonArray();
            for (var text : bottomLeft) bottomLeftJson.add(Text.Serializer.toJsonTree(text));

            var bottomRightJson = new JsonArray();
            for (var text : bottomRight) bottomRightJson.add(Text.Serializer.toJsonTree(text));

            var root = new JsonObject();
            root.add("top_left", topLeftJson);
            root.add("top_right", topRightJson);
            root.add("bottom_left", bottomLeftJson);
            root.add("bottom_right", bottomRightJson);

            Files.deleteIfExists(CONFIG_PATH);
            Files.writeString(CONFIG_PATH, GSON.toJson(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                save();
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
