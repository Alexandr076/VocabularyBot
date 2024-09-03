package com.azhdankov.vocabularybot.model.dictionary;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary {
    @EmbeddedId
    private WorkWithTranslation word;
    private String audioUrl;
    private Integer countOfRightAnswer;
}
