package dev.isxander.mainmenucredits.api;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public interface MainMenuCreditAPI {
    default List<Component> getTitleScreenTopLeft() {
        return new ArrayList<>();
    }

    default List<Component> getTitleScreenTopRight() {
        return new ArrayList<>();
    }

    default List<Component> getTitleScreenBottomLeft() {
        return new ArrayList<>();
    }

    default List<Component> getTitleScreenBottomRight() {
        return new ArrayList<>();
    }

    default List<Component> getPauseScreenTopLeft() {
        return new ArrayList<>();
    }

    default List<Component> getPauseScreenTopRight() {
        return new ArrayList<>();
    }

    default List<Component> getPauseScreenBottomLeft() {
        return new ArrayList<>();
    }

    default List<Component> getPauseScreenBottomRight() {
        return new ArrayList<>();
    }
}
