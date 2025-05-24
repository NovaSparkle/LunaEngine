package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.DeleteMessageQuery;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.KeyQuery;
import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.Collections;

public class ShowSpecificPlugin extends KeyQuery {
    private final Plugin plugin;
    public ShowSpecificPlugin(long chatId, Plugin plugin) {
        super(chatId);
        this.plugin = plugin;
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        DeleteMessageQuery deleteMessageQuery = new DeleteMessageQuery(this.getChatId());
        bot.registerQuery(deleteMessageQuery);

        InlineKeyboardRow row = new InlineKeyboardRow(InlineKeyboardButton.builder().text(Messages.getMessage("messageShown")).callbackData(deleteMessageQuery.getQueryId()).build());
        bot.sendMessage(this.getChatId(), String.format(Messages.getMessage("showPluginInfo"),
                plugin.getName(), plugin.getAuthor().getFirstName(), plugin.getAuthor().getUserName(), plugin.getVersion(), plugin.getPluginType()), new InlineKeyboardMarkup(Collections.singletonList(row)));
    }
}
