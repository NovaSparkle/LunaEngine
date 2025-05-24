package com.example.novasparkle.LunaEngine.telegram;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Component
public class Initializer {

    @Autowired
    Bot bot;

    @EventListener({ContextRefreshedEvent.class})
    @SneakyThrows
    public void init() {
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(bot.getConfig().getBotToken(), bot);

        bot.sendMessage(bot.getConfig().getAdminUserId(), Messages.getMessage("startMessage"));
    }
}
