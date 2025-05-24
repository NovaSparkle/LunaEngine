package com.example.novasparkle.LunaEngine.telegram.command;

import com.example.novasparkle.LunaEngine.telegram.Bot;
import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.novasparkle.LunaEngine.configuration.Messages.getMessage;

public class StartCommand extends Command {
    public StartCommand(Bot bot) {
        super("start", bot, Permission.USE);
    }

    @Override
    public void consume(Message message) {
        this.invoke(message);
    }

    public void invoke(Message message) {
        List<Command> commands = this.getBot().getCommands();
        List<KeyboardRow> rows = new ArrayList<>();
        commands.forEach(cmd -> rows.add(new KeyboardRow("/" + cmd.getIdentifier())));
        ReplyKeyboardMarkup markup = ReplyKeyboardMarkup.builder().keyboard(rows).resizeKeyboard(true).oneTimeKeyboard(false).build();

        this.getBot().sendMessage(message.getChatId(), String.format(getMessage("welcomeMessage"), message.getFrom().getFirstName()), markup);
    }
}
