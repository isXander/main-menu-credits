package dev.isxander.mainmenucredits.mixins;

import dev.isxander.mainmenucredits.MainMenuCredits;
import dev.isxander.mainmenucredits.gui.MMCPlainTextButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void addText(CallbackInfo ci) {
        var config = MainMenuCredits.getInstance().getConfig().MAIN_MENU;

        {
            var i = 0;
            for (var text : config.getTopLeft()) {
                addRenderableWidget(new MMCPlainTextButton(2, 2 + i * 12, font.width(text), 10, text, font, false));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getTopRight()) {
                addRenderableWidget(new MMCPlainTextButton(width - font.width(text) - 2, 2 + i * 12, font.width(text), 10, text, font, false));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getBottomLeft()) {
                addRenderableWidget(new MMCPlainTextButton(2, height - (20 + i * 12), font.width(text), 10, text, font, false));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getBottomRight()) {
                addRenderableWidget(new MMCPlainTextButton(width - font.width(text) - 2, height - (20 + i * 12), font.width(text), 10, text, font, false));
                i++;
            }
        }
    }
}
