package com.azhdankov.vocabularybot.model.dictionary;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class WorkWithTranslation implements Serializable {

    private String word;

    private String translation;

}
