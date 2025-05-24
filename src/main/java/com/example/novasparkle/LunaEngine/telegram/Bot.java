package com.example.novasparkle.LunaEngine.telegram;

import com.example.novasparkle.LunaEngine.configuration.Config;
import com.example.novasparkle.LunaEngine.configuration.Messages;
import com.example.novasparkle.LunaEngine.telegram.command.Command;
import com.example.novasparkle.LunaEngine.telegram.command.RegisterKeyCommand;
import com.example.novasparkle.LunaEngine.telegram.command.RegisterPluginCommand;
import com.example.novasparkle.LunaEngine.telegram.command.StartCommand;
import com.example.novasparkle.LunaEngine.telegram.controllers.service.BotService;
import com.example.novasparkle.LunaEngine.telegram.listeners.Branch;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.KeyQuery;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.utils.Status;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.*;


@Getter
@Component
public class Bot implements LongPollingSingleThreadUpdateConsumer {

    private final Config config;
    private final List<Command> commands;

    private final TelegramClient client;
    @Autowired
    private BotService service;
    private final Set<Branch> waiters;
    private final Set<KeyQuery> keyQueries;

    public Bot(Config config) {
        this.waiters = new HashSet<>();
        this.keyQueries = new HashSet<>();

        this.config = config;
        this.commands = List.of(new StartCommand(this), new RegisterPluginCommand(this), new RegisterKeyCommand(this));
        this.client = new OkHttpTelegramClient(this.config.getBotToken());
    }


//    this.service.getUserController().register(getUser(client, msg.getChatId(), msg.getFrom().getId()), Status.ADMIN)
    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message msg = update.getMessage();
            Command command = this.commands.stream().filter(cmd -> ("/" + cmd.getIdentifier()).equals(msg.getText())).findFirst().orElse(null);
            if (command != null) {
                User sender = this.service.getUserController().getUser(msg.getFrom().getId());
                if (sender.hasPermission(command.getPermission()))
                    command.consume(msg);
                else
                    this.sendMessage(sender.getId(), Messages.getMessage("noPermission"));

            } else if (!this.waiters.isEmpty()) {
                this.waiters.stream().filter(b -> b.getUserId() == msg.getChatId()).findFirst().ifPresent(b -> b.accept(msg));

            } else {
                this.deleteMessage(msg.getChatId(), msg.getMessageId());
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();

            this.keyQueries.stream().filter(keyQuery -> keyQuery.getQueryId().equals(callbackQuery.getData())).findFirst()
                    .ifPresent(query -> {
                        query.accept(this, callbackQuery);
                        keyQueries.remove(query);
                    });
        }
    }

    public void registerQuery(KeyQuery query) {
          this.keyQueries.add(query);
    }

    public void inlineMessage(Message message) {
        InlineKeyboardButton btn = InlineKeyboardButton.builder().text("Text").callbackData("data").build();
        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(new InlineKeyboardRow(btn))).build();
        this.sendMessage(message.getChatId(), "TEXT", markup);
    }

    @SneakyThrows
    public void sendMessage(long chatId, String text, ReplyKeyboard markup) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(text).parseMode("Markdown").replyMarkup(markup).build();
        this.client.execute(sendMessage);
    }
    @SneakyThrows
    public Message sendMessage(long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(text).parseMode("Markdown").build();
        return this.client.execute(sendMessage);
    }

    @SneakyThrows
    public void deleteMessage(long chatId, int msgId) {
        DeleteMessage deleteMessage = DeleteMessage.builder().chatId(chatId).messageId(msgId).build();
        this.client.execute(deleteMessage);
    }

    @SneakyThrows
    public void editMessage(long chatId, int msgId, Collection<? extends InlineKeyboardRow> keyboard, String newMessage) {
        EditMessageText editMessageText = EditMessageText.builder().chatId(chatId).messageId(msgId).text(newMessage).build();
        this.client.execute(editMessageText);
        if (keyboard != null) {
            EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder().chatId(chatId).messageId(msgId).replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard).build()).build();
            this.client.execute(editMessageReplyMarkup);
        }
    }

    public void sendMessage(String text, Status status) {
        this.getService().getUserController().getUsers(status).forEach(user -> this.sendMessage(user.getId(), text));

    }
    public void sendMessage(String text, Status status, InlineKeyboardMarkup markup) {
        this.getService().getUserController().getUsers(status).forEach(user -> this.sendMessage(user.getId(), text, markup));

    }

    @SneakyThrows
    public static org.telegram.telegrambots.meta.api.objects.User getUser(TelegramClient client, long currentChatId, long userId) {
        return client.execute(new GetChatMember(String.valueOf(currentChatId), userId)).getUser();
    }
}
