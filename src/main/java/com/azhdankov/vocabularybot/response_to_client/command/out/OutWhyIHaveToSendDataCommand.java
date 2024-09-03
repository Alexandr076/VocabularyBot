package com.azhdankov.vocabularybot.response_to_client.command.out;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.azhdankov.vocabularybot.response_to_client.dictionary.ResponseCommand;
import com.google.auto.service.AutoService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@AutoService(Command.class)
public class OutWhyIHaveToSendDataCommand implements Command {

    @Override
    public SendMessage replyToCommand(Update update) {

        String chatID = Command.getChatID(update);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton keyboardButtonYes = InlineKeyboardButton.builder()
                .text(ResponseCommand.OUT_WAITING_FOR_EMAIL.getResponse())
                .callbackData(ResponseCommand.OUT_WAITING_FOR_EMAIL.getResponse())
                .build();

        InlineKeyboardButton keyboardButtonNo = InlineKeyboardButton.builder()
                .text(ResponseCommand.OUT_GOODBYE.getResponse())
                .callbackData(ResponseCommand.OUT_GOODBYE.getResponse())
                .build();

        List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonYes);
        keyboardButtonList.add(keyboardButtonNo);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonList);
        keyboardMarkup.setKeyboard(rowList);

        SendMessage replyToCommand = SendMessage.builder()
                .chatId(chatID)
                .text("К сожалению, так как Ваш словарик находится на портале edvibe в личном кабинете, " +
                        "мне необходимо получить к нему доступ и без email и пароля я это сделать не смогу" + "\n" +
                        "Но Вы всегда можете перед использованием бота поменять свой пароль на тот, который Вы больше нигде " +
                        "не используете. Так Вам будет спокойнее, а мне - удобнее, что Вам спокойнее \uD83D\uDC36" +
                        "\n" +
                        "Кстати, Ваш пароль используется только для одноразового входа и дальше удаляется, поэтому " +
                        "может быть такое, что спустя какое-то время я заново могу попросить Вас ввести пароль" + "\n" +
                        "Ну что, я Вас убедил? \uD83D\uDE0F \uD83D\uDE0F \uD83D\uDE0F")
                .replyMarkup(keyboardMarkup)
                .build();
        return replyToCommand;
    }
}
