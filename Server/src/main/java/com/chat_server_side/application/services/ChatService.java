package com.chat_server_side.application.services;

import com.chat_server_side.application.connections.Account;
import com.chat_server_side.application.connections.Client;
import com.chat_server_side.application.loggers.Logger;
import com.chat_server_side.application.loggers.Loggers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * <code><strong>class ChatService</strong></code>
 * хранит участников чата, проверяет и рассылает сообщения,
 * добавляет новых участников.
 *
 */
public class ChatService {

    private final List<Client> connectedClients = new ArrayList<>();
    private final Logger logger = Loggers.getLogger();

    /**
     * method sendMessage(Client clientSocket, String message)
     * метод служит для проверки сообщения перед тем как сделать рассылку всем
     * участникам чата.
     * @param clientSocket сокет клиента-отправителя сообщения.
     * @param message строковое сообщение.
     *
     */
    public synchronized void sendMessage(final Client clientSocket, String message, Account account)  {

        if (message != null && message.equals("--exit")){
            message = "User: " + clientSocket.getNickname() + " disconnected";
            logger.log(message);
            clientSocket.disconnect();
            connectedClients.remove(clientSocket);
            Thread.currentThread().interrupt();
            account.logout();
        }else if(message == null) {

            message = "User: " + clientSocket.getNickname() + ": " + "interrupted";
            logger.log(message);
            clientSocket.disconnect();
            connectedClients.remove(clientSocket);
            Thread.currentThread().interrupt();
            System.out.println(connectedClients.size());
            account.logout();


        }else {
            logger.log("User: " + clientSocket.getNickname() + ": " + message);
            message = clientSocket.getNickname() + ": " + message;

        }
        this.sendMessageToAll(message);


    }

    /**
     * method sendMessageToAll(String message) делает рассылку сообщения всем участникам чата.
     * @param message принимает строку сообщения.
     */
    public synchronized void sendMessageToAll(final String message){
        for (Client cl : this.connectedClients){
            cl.sendMessage(message);
        }
    }

    /**
     * method addMember(Client client) добавляет объект клиента в участники чата,
     * логирует нового участника, отправляет рассылку всем участникам.
     * @param client принимает объект клиента
     */
    public synchronized void addMember(Client client){
        this.connectedClients.add(client);
        System.out.println("New connection accepted");
        String message = "new user: " + client.getNickname();
        logger.log(message);
        this.sendMessageToAll(message);
    }

    public int memberNumber(){
        return this.connectedClients.size();
    }
}

