package com.chat_server_side.application.threads;

import com.chat_server_side.application.connections.Auth;
import com.chat_server_side.application.connections.Client;
import com.chat_server_side.application.connections.Clients;
import com.chat_server_side.application.services.ChatService;

import java.net.Socket;

/**
 *
 * <code>class ClientThread</code>
 * клиентский класс который находиться в отдельном потоке,
 * включает в себя этап регистрации имени, приём и передача сообщений.
 * хранит объект с инкапсулированным сокетом клиента.
 *
 */
public class ClientThread implements Runnable {

    private final ChatService chatService;
    private final Auth auth = new Auth();
    private final Client client;


    public ClientThread(Socket socket, ChatService chatService) {
        this.client = Clients.newClient(socket);
        this.chatService = chatService;
    }

    /**
     * method run()
     * исполняет основную функцию по взаимодействию клиента с сервером.
     */
    @Override
    public void run() {

        final boolean isRegistered = this.auth.registration(client);

        if (isRegistered && client.getNickname() != null) {
            this.chatService.addMember(this.client);
        } else {
            Thread.currentThread().interrupt();
            client.close();
        }


        while (!Thread.currentThread().isInterrupted() || !client.isClosed()) {

            String message = this.client.listen();
            this.chatService.sendMessage(this.client, message);

        }

    }
}

