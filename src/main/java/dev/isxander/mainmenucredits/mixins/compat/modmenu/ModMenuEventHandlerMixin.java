package dev.isxander.mainmenucredits.mixins.compat.modmenu;

import com.terraformersmc.modmenu.event.ModMenuEventHandler;
import dev.isxander.mainmenucredits.gui.TextWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Widget;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = ModMenuEventHandler.class)
public class ModMenuEventHandlerMixin {
    @Inject(method = "shiftButtons(Lnet/minecraft/client/gui/widget/ClickableWidget;ZI)V", at = @At("HEAD"), cancellable = true, require = 0)
    private static void cancelShiftButtonsMM610(ClickableWidget button, boolean shiftUp, int spacing, CallbackInfo ci) {
        if (button instanceof TextWidget)
            ci.cancel();
    }

    @Inject(method = "shiftButtons(Lnet/minecraft/client/gui/widget/Widget;ZI)V", at = @At("HEAD"), cancellable = true, require = 0)
    private static void cancelShiftButtonsMM620(Widget widget, boolean shiftUp, int spacing, CallbackInfo ci) {
        if (widget instanceof TextWidget)
            ci.cancel();
    }
}
