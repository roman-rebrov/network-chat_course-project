package com.chat_server_side.application.connections;


/**
 * <code><strong>interface Client</strong></code>
 *  служит для инкапсуляции сокета, ввода и вывода, и других данных клиента.
 */
public interface Client {
    public void sendMessage(String message);
    public int getPort();
    public String listen();
    public void disconnect();
    public void setNickname(String name);
    public String getNickname();
    public void close();
    public boolean isClosed();
}

