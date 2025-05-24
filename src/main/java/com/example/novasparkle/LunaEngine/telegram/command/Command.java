package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Getter
public abstract class Command {
    private final String identifier;
    private final Bot bot;
    private final Permission permission;
    public Command(String identifier, Bot bot, Permission permission) {
        this.identifier = identifier;
        this.bot = bot;
        this.permission = permission;
    }
    public abstract void consume(Message message);
}
