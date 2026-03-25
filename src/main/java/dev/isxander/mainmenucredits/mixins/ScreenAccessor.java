package dev.isxander.mainmenucredits.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Invoker
    static void callDefaultHandleGameClickEvent(final ClickEvent event, final Minecraft minecraft, @Nullable final Screen activeScreen) {
        throw new AssertionError();
    }

    @Invoker
    static void callDefaultHandleClickEvent(final ClickEvent event, final Minecraft minecraft, @Nullable final Screen activeScreen) {
        throw new AssertionError();
    }
}
