package dev.isxander.mainmenucredits.api;

import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public interface MainMenuCreditAPI {
    default List<Text> getTopLeft() {
        return new ArrayList<>();
    }

    default List<Text> getTopRight() {
        return new ArrayList<>();
    }

    default List<Text> getBottomLeft() {
        return new ArrayList<>();
    }

    default List<Text> getBottomRight() {
        return new ArrayList<>();
    }
}
