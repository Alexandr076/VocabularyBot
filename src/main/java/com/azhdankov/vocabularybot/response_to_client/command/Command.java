package com.azhdankov.vocabularybot.response_to_client.command;

import com.azhdankov.vocabularybot.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

// Используется для того, чтобы можно было через @AutoService(Command.class) найти все реализации этого интерфейса
public interface Command {
    SendMessage replyToCommand(Update update);

    default Command setServices(UserService userService) {
        return this;
    };

    static String getChatID(Update update) {
        return String.valueOf(update.getMessage() != null ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId());
    }
}
