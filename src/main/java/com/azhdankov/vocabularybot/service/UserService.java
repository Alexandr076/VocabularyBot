package com.azhdankov.vocabularybot.service;

import com.azhdankov.vocabularybot.exceptions.IntegrationError;
import com.azhdankov.vocabularybot.externalAPI.rest.account.Login;
import com.azhdankov.vocabularybot.externalAPI.wss.GetDictionary;
import com.azhdankov.vocabularybot.model.user.User;
import com.azhdankov.vocabularybot.model.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserService implements BotService {

    @Autowired
    private UserDAO userDao;

    public void addNewUser(User user) {
        userDao.save(user);
    }

    public void deleteExistingUser() {

    }

    public void setReply(String chatID, String replyToCommand) {
        Optional<User> optionalUser = userDao.findById(chatID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setReplyToCommand(replyToCommand);
            userDao.save(user);
        }
    }

    public Optional<User> getUserByChatID(String chatID) {
        return userDao.findById(chatID);
    }

    public String getReplyByChatID(String chatID) {
        Optional<User> user = userDao.findById(chatID);
        return user.isPresent() ? user.get().getReplyToCommand() : "";
    }

    // Вот эта часть как-будто бы должна быть улучшена. Я не хочу хардкодить название методов,
    // хочется передавать ссылку на метод. Надо подумать
    public String getUserActionByUpdate(Update update) {
        if (update.hasMessage()) {
            String chatID = String.valueOf(update.getMessage().getChatId());
            Optional<User> user = userDao.findById(chatID);
            if (user.isPresent()) {
                if (user.get().getReplyToCommand() == null || user.get().getReplyToCommand().equals("")) {
                    return "actionByMessage";
                } else {
                    return "actionByReply";
                }
            } else {
                return "actionByMessage";
            }
        } else if (update.hasCallbackQuery()) {
            return "actionByCallback";
        }
        return "";
    }

    public void addEmailForUserByChatID(String chatID, String email) {
        Optional<User> optionalUser = getUserByChatID(chatID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(email);
            userDao.save(user);
        }
    }

    public void addTokenToUser(String chatID, String password) {
        Optional<User> optionalUser = getUserByChatID(chatID);
        if (optionalUser.isPresent()) {

            String userEmail = optionalUser.get().getEmail();

            // нужно переделать на асинхронный вызов через WebFlux, чтобы пользователь не ждал ответа
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Генерация токена для пользователя
            UUID token = UUID.randomUUID();

            // интеграционная дата модель для application/x-www-form-urlencoded типа контента
            MultiValueMap<String, String> mapping = new LinkedMultiValueMap<>();
            mapping.add("Email", "alexandr0751@yandex.ru");
            mapping.add("Password", password);
            mapping.add("UserRole", "1");
            mapping.add("AuthToken", String.valueOf(token));
            mapping.add("ReturnUrl", "");

            Login login = new Login(httpHeaders, mapping);
            if (login.isSignUPCompleted()) {
                User user = optionalUser.get();
                user.setToken(String.valueOf(token));
                userDao.save(user);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // нужно сделать обработку ошибки интеграции
//            else {
//                throw new IntegrationError("Integration with edvibe was failed");
//            }
        }
    }

    public void uploadDictionary(String chatID) {
        Optional<User> optionalUser = userDao.findById(chatID);
        if (optionalUser.isPresent()) {
            String token = optionalUser.get().getToken();
            GetDictionary getDictionary = new GetDictionary(token);
        }
    }
}
