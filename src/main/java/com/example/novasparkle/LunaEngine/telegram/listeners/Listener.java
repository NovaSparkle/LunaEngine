package com.example.novasparkle.LunaEngine.telegram.listeners;

import org.telegram.telegrambots.meta.api.objects.message.Message;

@FunctionalInterface
public interface Listener {
    void passEvent(Message message);
}
