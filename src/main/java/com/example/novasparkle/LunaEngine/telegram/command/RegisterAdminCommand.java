package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class RegisterAdminCommand extends Command {
    public RegisterAdminCommand(Bot bot) {
        super("register", bot, Permission.REGISTER_USER);
    }

    @Override
    public void consume(Message message) {

    }
}
