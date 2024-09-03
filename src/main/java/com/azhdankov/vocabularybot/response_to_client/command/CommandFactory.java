package com.azhdankov.vocabularybot.response_to_client.command;

import com.azhdankov.vocabularybot.response_to_client.dictionary.ResponsesDictionary;
import com.azhdankov.vocabularybot.response_to_client.dictionary.ResponseCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class CommandFactory {

    @Autowired
    private ResponsesDictionary responsesDictionary;

    public Command getCurrentCommand(String replyByClientMessage) {
        for (Map.Entry<String, Command> entry : responsesDictionary.getCommandDictionary().entrySet()) {
            if (StandardCharsets.UTF_8.encode(replyByClientMessage).compareTo(StandardCharsets.UTF_8.encode(entry.getKey())) == 0) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Command getNotFoundCommand() {
        String notFoundMessage = ResponseCommand.OUT_NOT_FOUND.getResponse();
        return responsesDictionary.getCommandDictionary().get(notFoundMessage);
    }

    public Command somethingWasWrongMessage() {
        String notFoundMessage = ResponseCommand.OUT_SOMETHING_WAS_WRONG.getResponse();
        return responsesDictionary.getCommandDictionary().get(notFoundMessage);
    }
}
