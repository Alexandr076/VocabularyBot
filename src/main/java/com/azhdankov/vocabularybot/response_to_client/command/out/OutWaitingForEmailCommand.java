package com.azhdankov.vocabularybot.response_to_client.command.out;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.azhdankov.vocabularybot.response_to_client.dictionary.ResponseCommand;
import com.azhdankov.vocabularybot.service.UserService;
import com.google.auto.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AutoService(Command.class)
public class OutWaitingForEmailCommand implements Command {

    @Autowired
    private UserService userService;

    @Override
    public Command setServices(UserService userService) {
        this.userService = userService;
        return this;
    }

    @Override
    public SendMessage replyToCommand(Update update) {

        String chatID = Command.getChatID(update);
        SendMessage replyToCommand = SendMessage.builder()
                .chatId(chatID)
                .text("Ну тогда жду email от Вашего личного кабинета")
                .build();
        userService.setReply(chatID, ResponseCommand.OUT_WAITING_FOR_PASSWORD.getResponse());
        return replyToCommand;
    }
}
