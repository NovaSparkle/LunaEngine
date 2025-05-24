package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.KeyQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ShowAllPluginsQuery extends KeyQuery {
    public ShowAllPluginsQuery(long chatId) {
        super(chatId);
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        List<InlineKeyboardRow> keyboard = new ArrayList<>();
        bot.getService().getPluginController().getPlugins().forEach(plugin -> {
                ShowSpecificPlugin showSpecificPlugin = new ShowSpecificPlugin(this.getChatId(), plugin);
                bot.registerQuery(showSpecificPlugin);
                keyboard.add(
                        new InlineKeyboardRow(
                                InlineKeyboardButton.builder().text(plugin.getName()).callbackData(showSpecificPlugin.getQueryId()).build()
                        )
                );
            }
        );
        bot.editMessage(this.getChatId(), query.getMessage().getMessageId(), keyboard, String.format(Messages.getMessage("pluginList"), keyboard.size()));
    }
}
