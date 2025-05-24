package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.PluginInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.TransferQuery;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.utils.Version;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.novasparkle.LunaEngine.configuration.Messages.getMessage;

public class SendDeveloperQuery extends TransferQuery {
    private final User developer;
    public SendDeveloperQuery(long chatId, PluginInfoContainer container, User developer) {
        super(chatId, container);
        this.developer = developer;
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        this.getContainer().set(this.developer);

        for (Version version : Version.values()) {

            SendVersionQuery sendVersion = new SendVersionQuery(this.getChatId(), this.getContainer(), version);
            bot.registerQuery(sendVersion);
            rows.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(version.getVersionName()).callbackData(sendVersion.getQueryId()).build()));
        }
        bot.editMessage(this.getChatId(), query.getMessage().getMessageId(), rows,
                String.format(getMessage("getPluginVersion"), ((PluginInfoContainer) this.getContainer()).getPluginName(), this.developer.getFirstName()));
    }
}
