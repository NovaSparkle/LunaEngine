package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.plugin;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.PluginInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.TransferContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.TransferQuery;
import com.example.novasparkle.LunaEngine.telegram.utils.PluginType;
import com.example.novasparkle.LunaEngine.telegram.utils.Status;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.example.novasparkle.LunaEngine.configuration.Messages.getMessage;

public class SendPluginTypeQuery extends TransferQuery {
    private final PluginType pluginType;
    public SendPluginTypeQuery(long chatId, TransferContainer container, PluginType pluginType) {
        super(chatId, container);
        this.pluginType = pluginType;
    }

    @Override
    @SneakyThrows
    public void accept(Bot bot, CallbackQuery query) {
        PluginInfoContainer container = (PluginInfoContainer) this.getContainer();

        bot.getService().getPluginController().register(container.getPluginName(), container.getDeveloper(), container.getVersion().getVersionName(), this.pluginType);
        String message = String.format(getMessage("pluginRegistered"), container.getPluginName(), container.getDeveloper().getFirstName(), container.getVersion().getVersionName(), this.pluginType.getType());

        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(this.getChatId()), query.getMessage().getMessageId());
        bot.getClient().execute(deleteMessage);
        bot.sendMessage(message, Status.ADMIN);
    }
}
