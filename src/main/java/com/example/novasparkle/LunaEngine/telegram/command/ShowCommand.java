package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin.ShowAllPluginsQuery;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

public class ShowCommand extends Command {
    public ShowCommand(Bot bot) {
        super("show", bot, Permission.SHOW_USERS);
    }

    @Override
    public void consume(Message message) {
        Bot bot = this.getBot();

        ShowAllPluginsQuery allPluginsQuery = new ShowAllPluginsQuery(message.getChatId());
        InlineKeyboardRow pluginsRow = new InlineKeyboardRow(InlineKeyboardButton.builder().text(Messages.getMessage("pluginRow")).callbackData(allPluginsQuery.getQueryId()).build());


        bot.sendMessage(message.getChatId(), Messages.getMessage("chooseShowEntry"));
    }
}
