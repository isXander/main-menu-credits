package dev.isxander.mainmenucredits.mixins;

import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PressableTextWidget.class)
public interface PressableTextWidgetAccessor {
    @Mutable
    @Accessor
    void setHoverText(Text content);
}
