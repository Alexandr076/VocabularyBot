package com.azhdankov.vocabularybot.configuration;

import com.azhdankov.vocabularybot.initialization.BotInitialization;
import com.azhdankov.vocabularybot.initialization.TelegramBotInstance;
import com.azhdankov.vocabularybot.response_to_client.command.CommandFactory;
import com.azhdankov.vocabularybot.response_to_client.dictionary.ResponsesDictionary;
import com.azhdankov.vocabularybot.service.DictionaryBotService;
import com.azhdankov.vocabularybot.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class AppConfig {

    @Bean
    public BotConfig botConfig() {
        return new BotConfig();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public ResponsesDictionary responsesDictionary() {
        return new ResponsesDictionary(userService());
    }

    @Bean
    public CommandFactory commandFactory() {
        return new CommandFactory();
    }

    @Bean
    public DictionaryBotService dictionaryBotService() {
        return new DictionaryBotService();
    }

    @Bean
    public TelegramBotInstance telegramBotInstance() throws TelegramApiException {
        return new TelegramBotInstance(botConfig(), commandFactory(), userService());
    }

    @Bean
    public BotInitialization botInitialization() throws TelegramApiException {
        return new BotInitialization(telegramBotInstance());
    }
}
