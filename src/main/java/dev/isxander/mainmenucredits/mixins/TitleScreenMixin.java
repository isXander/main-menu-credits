package dev.isxander.mainmenucredits.mixins;

import dev.isxander.mainmenucredits.MainMenuCredits;
import dev.isxander.mainmenucredits.gui.TextWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow
    @Final
    private static Text COPYRIGHT;

    protected TitleScreenMixin(Text text) {
        super(text);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void addText(CallbackInfo ci) {
        var config = MainMenuCredits.getInstance().getConfig().MAIN_MENU;

        {
            var i = 0;
            for (var text : config.getTopLeft()) {
                addDrawableChild(new TextWidget(2, 2 + i * 12, textRenderer.getWidth(text), 10, text, this));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getTopRight()) {
                addDrawableChild(new TextWidget(width - textRenderer.getWidth(text) - 2, 2 + i * 12, textRenderer.getWidth(text), 10, text, this));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getBottomLeft()) {
                addDrawableChild(new TextWidget(2, height - (20 + i * 12), textRenderer.getWidth(text), 10, text, this));
                i++;
            }
        }

        {
            var i = 0;
            for (var text : config.getBottomRight()) {
                addDrawableChild(new TextWidget(width - textRenderer.getWidth(text) - 2, height - (getCopyrightOffset() + i * 12), textRenderer.getWidth(text), 10, text, this));
                i++;
            }
        }
    }

    @Unique
    private Integer getCopyrightOffset() {
        AtomicInteger copyrightOffset = new AtomicInteger(10);
        ((ScreenAccessor) this).getDrawables().forEach(d -> {
            if (d instanceof PressableTextWidgetAccessor widget && widget.getText() == COPYRIGHT) copyrightOffset.set(20);
        });

        return copyrightOffset.get();
    }
}
