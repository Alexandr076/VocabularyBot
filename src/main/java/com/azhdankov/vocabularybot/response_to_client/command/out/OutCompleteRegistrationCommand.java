package com.azhdankov.vocabularybot.response_to_client.command.out;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.azhdankov.vocabularybot.service.UserService;
import com.google.auto.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AutoService(Command.class)
public class OutCompleteRegistrationCommand implements Command {

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

        //передаём пароль, который ввёл пользователь для получения токена с edvibe платформы
        userService.addTokenToUser(chatID, update.getMessage().getText());
        userService.uploadDictionary(chatID);
        userService.setReply(chatID,"");

        SendMessage sendMessageToClient = SendMessage.builder()
                .chatId(chatID)
                .text("Вход успешно выполнен и ваш словарь так же загружен. Удачного обучения!")
                .build();
        return sendMessageToClient;
    }
}
