package dev.isxander.mainmenucredits.gui;

import dev.isxander.mainmenucredits.mixins.PlainTextButtonWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PlainTextButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;

public class TextWidget extends PlainTextButtonWidget {
    private final boolean clickable;

    public TextWidget(int x, int y, int width, int height, Text text, Screen screen) {
        super(x, y, width, height, text, (button) -> screen.handleTextClick(text.getStyle()), MinecraftClient.getInstance().textRenderer);

        clickable = text.getStyle().getClickEvent() != null;
        // remove underline if no click event
        if (!clickable)
            ((PlainTextButtonWidgetAccessor) this).setUnderlinedContent(text);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        if (clickable)
            super.playDownSound(soundManager);
    }
}
