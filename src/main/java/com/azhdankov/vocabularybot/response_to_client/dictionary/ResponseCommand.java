package com.azhdankov.vocabularybot.response_to_client.dictionary;

public enum ResponseCommand {
    IN_START("start"),
    IN_GET_NEW_WORD("следующее слово"),
    OUT_WHY_I_HAVE_TO_SEND_DATA("Зачем это нужно? Я боюсь..."),
    OUT_SOMETHING_WAS_WRONG("Что-то пошло не так... Попробуй заново"),
    OUT_WAITING_FOR_EMAIL("ДА!"),
    OUT_GOODBYE("НЕТ!"),
    OUT_NOT_FOUND("Команда не найдена"),
    OUT_WAITING_FOR_PASSWORD("replyForPassword"),
    OUT_COMPLETE_REGISTRATION("replyForRegistration");

    private final String response;

    ResponseCommand(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
