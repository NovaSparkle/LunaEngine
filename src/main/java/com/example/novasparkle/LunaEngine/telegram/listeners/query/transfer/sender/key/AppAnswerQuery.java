package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.key;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.KeyInfoContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.TransferContainer;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender.TransferQuery;
import com.example.novasparkle.LunaEngine.telegram.utils.KeyQueryAnswer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

public class AppAnswerQuery extends TransferQuery {
    private final KeyQueryAnswer answer;
    public AppAnswerQuery(long chatId, TransferContainer container, KeyQueryAnswer answer) {
        super(chatId, container);
        this.answer = answer;
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        KeyInfoContainer container = (KeyInfoContainer) this.getContainer();
        User admin = Bot.getUser(bot.getClient(), this.getChatId(), this.getChatId());
        switch (this.answer) {
            case ACCEPT -> {
                bot.getService().getKeyController().register(container.getPlugin(), container.getIpv4());
                bot.editMessage(this.getChatId(), query.getMessage().getMessageId(), null,
                        String.format(Messages.getMessage("keyAccepted"),
                        container.getSender().getFirstName(), container.getSender().getUserName(), container.getPlugin().getName(), container.getIpv4()));


                bot.sendMessage(container.getSender().getId(), String.format(Messages.getMessage("appAccepted"),
                        admin.getFirstName(), admin.getUserName(), container.getPlugin().getName(), container.getIpv4()));
            } case REJECT -> {
                bot.editMessage(this.getChatId(), query.getMessage().getMessageId(), null,
                        String.format(Messages.getMessage("keyDenied"),
                                container.getSender().getFirstName(), container.getSender().getUserName(), container.getPlugin().getName(), container.getIpv4()));

                bot.sendMessage(container.getSender().getId(), String.format(Messages.getMessage("appRejected"),
                        admin.getFirstName(), admin.getUserName(), container.getPlugin().getName(), container.getIpv4()));
            }
        }
    }
}
