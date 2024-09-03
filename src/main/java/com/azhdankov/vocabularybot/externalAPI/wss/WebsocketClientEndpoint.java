package com.azhdankov.vocabularybot.externalAPI.wss;

import jakarta.websocket.*;

import java.net.URI;
import java.nio.ByteBuffer;

@ClientEndpoint
public class WebsocketClientEndpoint {

    private Session userSession;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint(URI endpoint) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpoint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");

        this.addMessageHandler(new MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });

        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
//        if (this.messageHandler != null) {
//            this.messageHandler.handleMessage(message);
//        }
        System.out.println(message.substring(0,100));
    }

    @OnMessage
    public void onMessage(ByteBuffer bytes) {
        System.out.println("Handle byte buffer");
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    public WebsocketClientEndpoint sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
        if (this.messageHandler != null) this.messageHandler.handleMessage(message);
        return this;
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}
