package dev.isxander.mainmenucredits.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MMCConfigEntry {
    private final String key;

    private final List<Text> topLeft = new ArrayList<>();
    private final List<Text> topRight = new ArrayList<>();
    private final List<Text> bottomLeft = new ArrayList<>();
    private final List<Text> bottomRight = new ArrayList<>();

    private final List<String> modBlacklist = new ArrayList<>();

    public MMCConfigEntry(String key) {
        this.key = key;
    }

    public void load(JsonObject root) {
        if (!root.has(key)) {
            return;
        }

        var child = root.getAsJsonObject(key);
        if (child.has("top_left")) {
            var topLeftJson = child.getAsJsonArray("top_left");
            topLeftJson.forEach((element) -> topLeft.add(Text.Serializer.fromJson(element)));
        }

        if (child.has("top_right")) {
            var topRightJson = child.getAsJsonArray("top_right");
            topRightJson.forEach((element) -> topRight.add(Text.Serializer.fromJson(element)));
        }

        if (child.has("bottom_left")) {
            var bottomLeftJson = child.getAsJsonArray("bottom_left");
            bottomLeftJson.forEach((element) -> bottomLeft.add(Text.Serializer.fromJson(element)));
        }

        if (child.has("bottom_right")) {
            var bottomRightJson = child.getAsJsonArray("bottom_right");
            bottomRightJson.forEach((element) -> bottomRight.add(Text.Serializer.fromJson(element)));
        }

        if (child.has("mod_blacklist")) {
            var modBlacklistJson = child.getAsJsonArray("mod_blacklist");
            modBlacklistJson.forEach((element) -> modBlacklist.add(element.getAsString()));
        }
    }

    public void createEmpty(JsonObject root) {
        var child = new JsonObject();
        child.add("top_left", new JsonArray());
        child.add("top_right", new JsonArray());
        child.add("bottom_left", new JsonArray());
        child.add("bottom_right", new JsonArray());
        child.add("mod_blacklist", new JsonArray());
        root.add(key, child);
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

    public List<String> getModBlacklist() {
        return modBlacklist;
    }
}
