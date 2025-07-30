package dev.isxander.mainmenucredits.mixins;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Invoker
    static void invokeHandleBasicClickEvent(ClickEvent clickEvent, MinecraftClient client, @Nullable Screen screenAfterRun) {}

    @Mutable
    @Accessor
    List<Drawable> getDrawables();
}
