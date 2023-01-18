package com.chat_client_side.application.services;


import com.chat_client_side.application.loggers.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <code><strong>class ChatService</strong></code>
 * класс служит для отправки сообщений на сервер по сокету.
 */
public class ChatService {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Thread receiveThread = null;
    private Logger logger = null;

    public ChatService(String host, int port, Logger log){
        this(host, port);
        this.logger = log;
    }
    public ChatService(String host, int port){

        try {
            this.socket = new Socket(host, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.runDataReceiver();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method runDataReceiver() создаёт и запусткает поток в котором сокет принимает
     * сообщения от сервера форматирует и отбражает в терменале.
     */
    private void runDataReceiver(){
        this.receiveThread = new Thread(() -> {
            while (true) {

                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                try {
                    String res = null;
                    if (in.ready()) {

                            res = in.readLine();
                            this.logger.log(res);

                            final String messageForming = "[" + DateTimeFormatter.ofPattern("HH:mm").format(ZonedDateTime.now()) + "] " + res;
                            System.out.println(messageForming);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();

                    if(!socket.isClosed()) {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                }
            }

        });
        receiveThread.start();
    }

    public boolean isSocketClosed(){
        return this.socket.isClosed();
    }

    /**
     *
     * method sendMessage(String message)
     * @param message строковое сообщение клиента.
     * Отправляет сообщение на сервер по сокету.
     *
     */
    public void sendMessage(String message){
        out.println(message);
        out.flush();

        if (message.equals("--exit")) {
            receiveThread.interrupt();
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

    }
}
