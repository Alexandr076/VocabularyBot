package com.azhdankov.vocabularybot.model.dictionary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface DictionaryDAO extends CrudRepository<Dictionary, String> {
}
