package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.Branch;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.KeyInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.key.SendPluginQuery;
import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import com.example.novasparkle.LunaEngine.telegram.utils.PluginType;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.novasparkle.LunaEngine.configuration.Messages.getMessage;

public class RegisterKeyCommand extends Command {
    public RegisterKeyCommand(Bot bot) {
        super("key", bot, Permission.GET_KEY);
    }

    @Override
    public void consume(Message message) {
        Message sentMessage = this.getBot().sendMessage(message.getChatId(), getMessage("getServerIp"));
        Branch branch = new Branch(message.getFrom().getId());
        branch.setListener(ipv4Message -> {
            long chatId = ipv4Message.getChatId();
            String ip = ipv4Message.getText();
            if (!Pattern.matches("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)", ip)) {
                this.getBot().sendMessage(chatId, getMessage("incorrectIp"));
                return;
            }
            KeyInfoContainer container = new KeyInfoContainer(ipv4Message.getFrom(), ip);

            List<Plugin> paidPlugins = this.getBot().getService().getPluginController().getPlugins(PluginType.PAID);
            List<InlineKeyboardRow> keyboard = new ArrayList<>();
            for (Plugin plugin : paidPlugins) {
                SendPluginQuery sendPluginQuery = new SendPluginQuery(ipv4Message.getChatId(), container, plugin);
                this.getBot().registerQuery(sendPluginQuery);
                keyboard.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(plugin.getName()).callbackData(sendPluginQuery.getQueryId()).build()));

            }
            Bot bot = this.getBot();

            bot.deleteMessage(chatId, sentMessage.getMessageId());
            bot.deleteMessage(chatId, ipv4Message.getMessageId());

            bot.sendMessage(chatId, String.format(getMessage("getPluginName"), ipv4Message.getText()), new InlineKeyboardMarkup(keyboard));
            bot.getWaiters().remove(branch);
        });
        this.getBot().getWaiters().add(branch);
    }
}
