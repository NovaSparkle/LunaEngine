package com.example.novasparkle.LunaEngine.telegram.listeners.query;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class DeleteMessageQuery extends KeyQuery {
    public DeleteMessageQuery(long chatId) {
        super(chatId);
    }

    @Override
    public void accept(Bot bot, CallbackQuery query) {
        bot.deleteMessage(this.getChatId(), query.getMessage().getMessageId());
    }
}
