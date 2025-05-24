package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.Branch;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.PluginInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin.SendDeveloperQuery;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.novasparkle.LunaEngine.configuration.Messages.getMessage;

public class RegisterPluginCommand extends Command {
    public RegisterPluginCommand(Bot bot) {
        super("plugin", bot, Permission.WRITE_PLUGIN);
    }

    @Override
    public void consume(Message message) {
        Message sentMessage = this.getBot().sendMessage(message.getChatId(), getMessage("getPluginName"));
        List<User> developers = this.getBot().getService().getUserController().getUsers();
        Branch branch = new Branch(message.getFrom().getId());
        branch.setListener(name -> {
            PluginInfoContainer container = new PluginInfoContainer(name.getText());
            List<InlineKeyboardRow> keyboard = new ArrayList<>();
            for (User developer : developers) {
                SendDeveloperQuery sendDeveloper = new SendDeveloperQuery(name.getChatId(), container, developer);

                this.getBot().registerQuery(sendDeveloper);
                keyboard.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(developer.getFirstName()).callbackData(sendDeveloper.getQueryId()).build()));
            }
            this.getBot().deleteMessage(message.getChatId(), sentMessage.getMessageId());
            this.getBot().deleteMessage(message.getChatId(), name.getMessageId());
            this.getBot().sendMessage(message.getChatId(), String.format(getMessage("getPluginAuthor"), name.getText()), new InlineKeyboardMarkup(keyboard));
            this.getBot().getWaiters().remove(branch);
        });
        this.getBot().getWaiters().add(branch);
    }
}
