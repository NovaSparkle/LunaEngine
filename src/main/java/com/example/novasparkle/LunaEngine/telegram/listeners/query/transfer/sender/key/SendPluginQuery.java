package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.key;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.KeyInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.TransferContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.TransferQuery;
import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import com.example.novasparkle.LunaEngine.telegram.utils.KeyQueryAnswer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SendPluginQuery extends TransferQuery {
    private final Plugin plugin;
    public SendPluginQuery(long chatId, TransferContainer container, Plugin plugin) {
        super(chatId, container);
        this.plugin = plugin;
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        List<InlineKeyboardRow> keyboard = new ArrayList<>();
        this.getContainer().set(plugin);
        for (KeyQueryAnswer answer : KeyQueryAnswer.values()) {
            AppAnswerQuery appAnswerQuery = new AppAnswerQuery(this.getChatId(), this.getContainer(), answer);
            bot.registerQuery(appAnswerQuery);
            keyboard.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(answer.getText()).callbackData(appAnswerQuery.getQueryId()).build()));
        }
        KeyInfoContainer container = (KeyInfoContainer) this.getContainer();
        bot.editMessage(this.getChatId(), query.getMessage().getMessageId(), null, String.format(Messages.getMessage("appSentKey"), this.plugin.getName(), container.getIpv4()));

        String adminMessage = String.format(Messages.getMessage("appReceivedKey"), container.getSender().getUserName(), this.getChatId(), plugin.getName(), container.getIpv4());
        bot.sendMessage(this.plugin.getAuthor().getId(), adminMessage, InlineKeyboardMarkup.builder().keyboard(keyboard).build());
    }
}
