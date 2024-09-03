package com.azhdankov.vocabularybot.response_to_client.command.in;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.google.auto.service.AutoService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AutoService(Command.class)
public class InGetNewWordCommand implements Command {

    @Override
    public SendMessage replyToCommand(Update update) {

        String chatID = Command.getChatID(update);

        SendMessage sendMessageToClient = SendMessage.builder()
                .chatId(chatID)
                .text("hello from ContinueCommand")
                .build();
        return sendMessageToClient;
    }
}
