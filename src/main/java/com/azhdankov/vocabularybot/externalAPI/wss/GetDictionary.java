package com.azhdankov.vocabularybot.externalAPI.wss;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class GetDictionary {

    private final String url = "wss://edvibe.com/ws/WebSockets/SocketHandler.ashx?Page=PupilProfile&MessageShowToken=dev&userRole=1&token=";

    public GetDictionary(String token) {
        try {
            // открываем веб сокет
            WebsocketClientEndpoint clientEndpoint = new WebsocketClientEndpoint(new URI(url + token));

            // листнер веб сокета
//            new Thread(() -> {
//                clientEndpoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
//                    @Override
//                    public void handleMessage(String message) {
//                        System.out.println(message.substring(0, 100));
//                    }
//                });
//            }).start();

            // отправка сообщения через веб сокет
            clientEndpoint
                    .sendMessage("{\"controller\":\"Vocabulary\",\"metod\":\"LoadVocabulary\",\"value\":\"{\\\"PupilId\\\":1473769,\\\"IsRandom\\\":false}\"}");

            // ждём ответа от веб сокета (херовая штука, нужно через паблиш/сабскрайб делать
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException exception: " + e.getMessage());
        }
    }
}
