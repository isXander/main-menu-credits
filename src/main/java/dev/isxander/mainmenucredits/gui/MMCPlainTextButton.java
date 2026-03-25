package dev.isxander.mainmenucredits.gui;

import dev.isxander.mainmenucredits.mixins.ScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import org.jspecify.annotations.NonNull;

public class MMCPlainTextButton extends StringWidget {
    private final Component message;
    private final Component underlinedMessage;
    private final boolean clickable;
    private final boolean inGame;

    public MMCPlainTextButton(int x, int y, int width, int height, Component message, Font font, boolean inGame) {
        super(x, y, width, height, message, font);
        this.setComponentClickHandler(this::onClickStyle);

        this.active = true;
        this.clickable = message.getStyle().getClickEvent() != null;
        this.message = message;
        this.underlinedMessage = this.clickable
                ? ComponentUtils.mergeStyles(message, Style.EMPTY.withUnderlined(true))
                : this.message;
        this.inGame = inGame;
    }

    @Override
    public void visitLines(@NonNull ActiveTextCollector output) {
        if (isHoveredOrFocused()) {
            this.setMessage(this.underlinedMessage);
        }

        super.visitLines(output);

        if (isHoveredOrFocused()) {
            this.setMessage(this.message);
        }
    }

    @Override
    public void playDownSound(@NonNull SoundManager soundManager) {
        if (this.clickable) {
            super.playDownSound(soundManager);
        }
    }

    private void onClickStyle(Style style) {
        if (this.inGame) {
            ScreenAccessor.callDefaultHandleGameClickEvent(style.getClickEvent(), Minecraft.getInstance(), Minecraft.getInstance().screen);
        } else {
            ScreenAccessor.callDefaultHandleClickEvent(style.getClickEvent(), Minecraft.getInstance(), Minecraft.getInstance().screen);
        }
    }
}
