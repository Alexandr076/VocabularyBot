package com.azhdankov.vocabularybot.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO extends CrudRepository<User, String> {
}
