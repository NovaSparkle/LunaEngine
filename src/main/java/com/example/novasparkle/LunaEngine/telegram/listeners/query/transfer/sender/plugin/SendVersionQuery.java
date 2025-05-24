package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.PluginInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.TransferContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.TransferQuery;
import com.example.novasparkle.LunaEngine.telegram.utils.PluginType;
import com.example.novasparkle.LunaEngine.telegram.utils.Version;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.novasparkle.LunaEngine.configuration.Messages.getMessage;

public class SendVersionQuery extends TransferQuery {
    private final Version version;

    public SendVersionQuery(long chatId, TransferContainer container, Version version) {
        super(chatId, container);
        this.version = version;
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        this.getContainer().set(this.version);
        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (PluginType pluginType : PluginType.values()) {
            SendPluginTypeQuery sendPluginTypeQuery = new SendPluginTypeQuery(this.getChatId(), this.getContainer(), pluginType);
            bot.registerQuery(sendPluginTypeQuery);
            rows.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(pluginType.getType()).callbackData(sendPluginTypeQuery.getQueryId()).build()));
        }
        PluginInfoContainer container = (PluginInfoContainer) this.getContainer();
        bot.editMessage(this.getChatId(), query.getMessage().getMessageId(), rows,
                String.format(getMessage("getPluginType"), container.getPluginName(), container.getDeveloper().getFirstName(), this.version.getVersionName()));
    }
}
