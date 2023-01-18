package com.chat_server_side.application.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <code><strong>class Clients</strong></code> - Утилитный класс
 * служит для реализации интерфейса Client.
 *
 */
public class Clients {

    private Clients() {
    }

    /**
     *
     * @param soc принимает объект сокета клиента.
     * @return Реализация интерфейса Client
     *
     */
    public static Client newClient(Socket soc) {

        /**
         *
         * @return имплементация интерфейса <code>Client</code>
         * Инкапсулирует сокет пользователя.
         * Контролирует ввод/вывод данных сокета.
         *
         */
        return new Client() {

            private final Socket socket = soc;
            private BufferedReader in;
            private PrintWriter out;
            private String nickname = null;


            {
                if (socket != null) {
                    try {
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            /**
             *
             * @param message принимает строку для отправки сообщения.
             * отправляет сообщение по сокету и очищает буфер.
             *
             */
            @Override
            public void sendMessage(String message) {
                if (socket == null) {
                    return;
                }
                try {
                    out.println(message);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * void disconnect() закрывает сокет.
             */
            @Override
            public void disconnect() {
                close();
            }


            /**
             * String lister() слушает входящие данные от клиента.
             * @return данные от клиента в строковом формате.
             */
            @Override
            public String listen() {
                try {
                    String s = in.readLine();
                    System.out.println(getNickname() + ": " + s);
                    return s;
                } catch (IOException e) {
                    disconnect();
                    Thread.currentThread().interrupt();

                }
                return null;
            }

            @Override
            public int getPort() {
                return soc.getPort();
            }

            @Override
            public void setNickname(String name) {
                this.nickname = name;
            }

            @Override
            public String getNickname() {
                return this.nickname;
            }

            @Override
            public void close() {
                try {
                    if (this.socket.isInputShutdown() && this.socket.isOutputShutdown()) {
                        this.socket.shutdownInput();
                        this.socket.shutdownOutput();
                    }
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean isClosed() {
                return this.socket.isClosed();
            }
        };
    }
}

