package com.example.novasparkle.LunaEngine.telegram.listeners.query;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.UUID;
import java.util.function.BiConsumer;

@Getter
public abstract class KeyQuery implements BiConsumer<Bot, CallbackQuery> {
    private final String queryId;
    private final long chatId;
    public KeyQuery(long chatId) {
        this.queryId = UUID.randomUUID().toString();
        this.chatId = chatId;
    }
}
