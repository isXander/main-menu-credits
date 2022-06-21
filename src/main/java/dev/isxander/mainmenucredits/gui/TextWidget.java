package dev.isxander.mainmenucredits.gui;

import dev.isxander.mainmenucredits.mixins.PressableTextWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;

public class TextWidget extends PressableTextWidget {
    private final boolean clickable;

    public TextWidget(int x, int y, int width, int height, Text text, Screen screen) {
        super(x, y, width, height, text, (button) -> screen.handleTextClick(text.getStyle()), MinecraftClient.getInstance().textRenderer);

        clickable = text.getStyle().getClickEvent() != null;
        // remove underline if no click event
        if (!clickable)
            ((PressableTextWidgetAccessor) this).setHoverText(text);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        if (clickable)
            super.playDownSound(soundManager);
    }
}
