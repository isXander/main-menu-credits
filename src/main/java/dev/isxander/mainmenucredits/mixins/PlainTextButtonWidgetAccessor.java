package dev.isxander.mainmenucredits.mixins;

import net.minecraft.client.gui.widget.PlainTextButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlainTextButtonWidget.class)
public interface PlainTextButtonWidgetAccessor {
    @Mutable
    @Accessor
    void setUnderlinedContent(Text content);
}
