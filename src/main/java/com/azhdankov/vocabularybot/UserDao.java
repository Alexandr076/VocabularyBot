package com.azhdankov.vocabularybot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends CrudRepository<UserInfo, String> {
}
