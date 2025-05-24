package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class DeletePluginCommand extends Command {
    public DeletePluginCommand(Bot bot) {
        super("deleteplugin", bot, Permission.WRITE_PLUGIN);
    }

    @Override
    public void consume(Message message) {
        Bot bot = this.getBot();
        List<User> developers = this.getBot().getService().getUserController().getUsers();
        List<InlineKeyboardRow> keyboard = new ArrayList<>();
//        for (User developer : developers) {
//            SendDeveloperQuery sendDeveloper = new SendDeveloperQuery(message.getChatId(), container, developer);
//
//            this.getBot().registerQuery(sendDeveloper);
//            keyboard.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(developer.getFirstName()).callbackData(sendDeveloper.getQueryId()).build()));
//        }

        bot.sendMessage(message.getChatId(), Messages.getMessage("getPluginName"));
    }
}
