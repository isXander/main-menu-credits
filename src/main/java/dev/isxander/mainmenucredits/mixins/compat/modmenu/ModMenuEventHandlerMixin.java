package dev.isxander.mainmenucredits.mixins.compat.modmenu;

import com.terraformersmc.modmenu.event.ModMenuEventHandler;
import dev.isxander.mainmenucredits.gui.TextWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(ModMenuEventHandler.class)
public class ModMenuEventHandlerMixin {
    @Inject(method = "shiftButtons", at = @At("HEAD"), cancellable = true)
    private static void cancelShiftButtons(ClickableWidget button, boolean shiftUp, int spacing, CallbackInfo ci) {
        if (button instanceof TextWidget)
            ci.cancel();
    }
}
