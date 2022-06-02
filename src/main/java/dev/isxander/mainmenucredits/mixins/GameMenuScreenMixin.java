package dev.isxander.mainmenucredits.mixins;

import dev.isxander.mainmenucredits.MainMenuCredits;
import dev.isxander.mainmenucredits.gui.TextWidget;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text text) {
        super(text);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void addText(CallbackInfo ci) {
        var config = MainMenuCredits.getInstance().getConfig().PAUSE_MENU;

        {
            var i = 0;
            for (var text : config.getTopLeft()) {
                addDrawableChild(new TextWidget(2, 2 + i * 12, textRenderer.getWidth(text), 10, text, (Screen) (Object) this));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getTopRight()) {
                addDrawableChild(new TextWidget(width - textRenderer.getWidth(text) - 2, 2 + i * 12, textRenderer.getWidth(text), 10, text, (Screen) (Object) this));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getBottomLeft()) {
                addDrawableChild(new TextWidget(2, height - (10 + i * 12), textRenderer.getWidth(text), 10, text, (Screen) (Object) this));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getBottomRight()) {
                addDrawableChild(new TextWidget(width - textRenderer.getWidth(text) - 2, height - (10 + i * 12), textRenderer.getWidth(text), 10, text, (Screen) (Object) this));
                i++;
            }
        }

    }
}
