package dev.isxander.mainmenucredits.mixins;

import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlainTextButton.class)
public interface PressableTextWidgetAccessor {
    @Mutable
    @Accessor
    void setUnderlinedMessage(Component message);
}
