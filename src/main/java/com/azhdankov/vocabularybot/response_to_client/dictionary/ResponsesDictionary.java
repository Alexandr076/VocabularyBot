package com.azhdankov.vocabularybot.response_to_client.dictionary;

import com.azhdankov.vocabularybot.response_to_client.command.Command;
import com.azhdankov.vocabularybot.service.UserService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

@Getter
@Component
public class ResponsesDictionary {

    private final HashMap<String, Command> commandDictionary = new HashMap<>();
    private final String COMMAND_CLASS_POSTFIXE = "command";
    private UserService userService;

    // Создаём справочник, который содержит хэндлер для команды. Как пример, в hashMap может быть:
    // [start][StartCommand] или [cледующее слово][NextWordMessage], где ключ - это команда от пользователя, а
    // значение - это объект реализующий Command интерфейс.
    // Через ServiceLoader ищем все реализации интерфейса Command, чтобы заполнить справочник

    public ResponsesDictionary(UserService userService) {

        this.userService = userService;

        ServiceLoader<Command> commandList = ServiceLoader.load(Command.class);

        Arrays.stream(ResponseCommand.values())
                .forEach(x -> {
                    String classNameLowerCase = x.toString().replace("_", "").toLowerCase() + COMMAND_CLASS_POSTFIXE;
                    Command commandHandler = commandList
                            .stream()
                            .filter(c -> classNameLowerCase.equals(c.get().getClass().getSimpleName().toLowerCase()))
                            .findFirst()
                            .orElseThrow(NoSuchElementException::new)
                            .get();
                    commandHandler.setServices(userService);
                    commandDictionary.put(x.getResponse().toLowerCase(), commandHandler);
                });
    }
}
