package com.azhdankov.vocabularybot.response_to_client.command.out;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.google.auto.service.AutoService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AutoService(Command.class)
public class OutNotFoundCommand implements Command {
    // если отправить "команда не найдена", то ответит "command not found": подумать, как пофиксить правильно
    @Override
    public SendMessage replyToCommand(Update update) {

        String chatID = Command.getChatID(update);

        SendMessage replyToCommand = SendMessage.builder()
                .chatId(chatID)
                .text("command not found")
                .build();
        return replyToCommand;
    }
}
