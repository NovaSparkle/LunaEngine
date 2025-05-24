package com.example.novasparkle.LunaEngine.telegram.listeners;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.function.Consumer;

public class Branch implements Consumer<Message> {
    @Getter
    private final long userId;
    @Setter
    private Listener listener;
    public Branch(long userId) {
        this.userId = userId;
    }

    @Override
    public void accept(Message message) {
        this.listener.passEvent(message);
    }
}
