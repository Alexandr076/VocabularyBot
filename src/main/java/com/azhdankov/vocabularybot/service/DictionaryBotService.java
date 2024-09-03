package com.azhdankov.vocabularybot.service;

import com.azhdankov.vocabularybot.model.dictionary.DictionaryDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class DictionaryBotService implements BotService {

    @Autowired
    private DictionaryDAO dictionaryDAO;
}
