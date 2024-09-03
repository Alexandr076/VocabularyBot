package com.azhdankov.vocabularybot.initialization;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.azhdankov.vocabularybot.response_to_client.command.CommandFactory;
import com.azhdankov.vocabularybot.configuration.BotConfig;
import com.azhdankov.vocabularybot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBotInstance extends TelegramLongPollingBot {
    @Autowired
    private BotConfig botConfig;
    @Autowired
    private CommandFactory commandFactory;
    @Autowired
    private UserService userService;

    // Создаём инстанс тг бота, передав в него конфиг
    public TelegramBotInstance(BotConfig botConfig, CommandFactory commandFactory, UserService userService) throws TelegramApiException {
        super(botConfig.getBotToken());
        this.userService = userService;
        this.commandFactory = commandFactory;
        this.botConfig = botConfig;
        log.info("Bot started");
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {

        String action = userService.getUserActionByUpdate(update);
        switch (action) {
            case "actionByMessage" -> actionByMessage(update);
            case "actionByCallback" -> actionByCallback(update);
            case "actionByReply" -> actionByReply(update);
            default -> commandFactory.somethingWasWrongMessage().replyToCommand(update);
        }
    }

    public void actionByMessage(Update update) {
        // Ожидаем получение сообщения, парсим его, убрав "/" из сообщения
        // Создаём инстанс фабрики и через неё вызываем необходимый хэндлер для обработки сообщения от пользователя
        // На выходе из хэндлера получаем объект SendMessage, который прокидываем в execute метод Telegram FW
        // Если команда не найдена, то забираем not found объект

        String replyByClientMessage = update.getMessage().getText().replace("/", "").toLowerCase();

        Command commandHandler = commandFactory.getCurrentCommand(replyByClientMessage);
        try {
            if (commandHandler == null) {
                execute(commandFactory.getNotFoundCommand().replyToCommand(update));
            } else {
                execute(commandFactory.getCurrentCommand(replyByClientMessage).replyToCommand(update));
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void actionByCallback(Update update) {
        String callbackData = update.getCallbackQuery()
                .getData().toLowerCase();

        Command commandHandler = commandFactory.getCurrentCommand(callbackData);
        try {
            if (commandHandler == null) {
                execute(commandFactory.somethingWasWrongMessage().replyToCommand(update));
            } else {
                execute(commandFactory.getCurrentCommand(callbackData).replyToCommand(update));
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void actionByReply(Update update) {
        String chatID = String.valueOf(update.getMessage().getChatId());
        String replyByClientMessage = userService.getReplyByChatID(chatID).toLowerCase();


        Command commandHandler = commandFactory.getCurrentCommand(replyByClientMessage);

        try {
            if (commandHandler == null) {
                execute(commandFactory.somethingWasWrongMessage().replyToCommand(update));
            } else {
                execute(commandFactory.getCurrentCommand(replyByClientMessage).replyToCommand(update));
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
