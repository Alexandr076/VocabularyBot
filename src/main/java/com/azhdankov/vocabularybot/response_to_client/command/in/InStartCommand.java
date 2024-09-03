package com.azhdankov.vocabularybot.response_to_client.command.in;

import com.azhdankov.vocabularybot.model.user.User;
import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.azhdankov.vocabularybot.response_to_client.dictionary.ResponseCommand;
import com.azhdankov.vocabularybot.service.UserService;
import com.google.auto.service.AutoService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@AutoService(Command.class)
@Setter
public class InStartCommand implements Command {

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

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = InlineKeyboardButton.builder()
                .text(ResponseCommand.OUT_WHY_I_HAVE_TO_SEND_DATA.getResponse())
                .callbackData(ResponseCommand.OUT_WHY_I_HAVE_TO_SEND_DATA.getResponse())
                .build();

        List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonList);
        keyboardMarkup.setKeyboard(rowList);

        SendMessage replyToCommand = SendMessage.builder()
                .chatId(chatID)
                .text("Добро пожаловать в бот-помощник по изучению иностранных слов \uD83D\uDC40" + "\n" +
                        "Для того, чтобы начать обучение, Вам необходимо указать свою почту и пароль от " +
                        "личного кабинета edvibe" + "\n" +
                        "Введите Вашу почту в ответном сообщении")
                .replyMarkup(keyboardMarkup)
                .build();
        userService.addNewUser(User.builder()
                .chatID(chatID)
                .build());
        return replyToCommand;
    }
}
