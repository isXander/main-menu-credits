package dev.isxander.mainmenucredits.api;

import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public interface MainMenuCreditAPI {
    default List<Text> getTitleScreenTopLeft() {
        return new ArrayList<>();
    }

    default List<Text> getTitleScreenTopRight() {
        return new ArrayList<>();
    }

    default List<Text> getTitleScreenBottomLeft() {
        return new ArrayList<>();
    }

    default List<Text> getTitleScreenBottomRight() {
        return new ArrayList<>();
    }

    default List<Text> getPauseScreenTopLeft() {
        return new ArrayList<>();
    }

    default List<Text> getPauseScreenTopRight() {
        return new ArrayList<>();
    }

    default List<Text> getPauseScreenBottomLeft() {
        return new ArrayList<>();
    }

    default List<Text> getPauseScreenBottomRight() {
        return new ArrayList<>();
    }
}
