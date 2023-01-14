package com.chat_server_side.application;


import com.chat_server_side.application.services.ChatService;
import com.chat_server_side.application.threads.ClientThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code><strong>class ChatServerSideApplication</strong></code>
 * стартовый класс чата серверной стороны.
 */
public class ChatServerSideApplication {

    private ServerSocket serverSocket = null;
    private ChatService chatService = null;
    private Map<String, String> props = new HashMap<>();
    final String SETTING_PATH = "settings.txt";


    public ChatServerSideApplication() {
    }

    /**
     * method propsRead служит для чтения файла с настройками сервера.
     */
    private void propsRead() {

        final File file = new File(SETTING_PATH);

        try {

            List<String> lines = Files.readAllLines(file.toPath());

            for (String s : lines) {
                try {
                    String[] newProp = s.split("=");
                    props.put(newProp[0], newProp[1]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(ex);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * метод инициализирует серверный чат перед стартом,
     * устанавливает все необходимые настройки.
     */
    private void initialize() {
        this.propsRead();

        try {

            final String port = props.get("port");
            if (port == null) {
                return;
            }else {
                this.serverSocket = new ServerSocket(Integer.parseInt(port));
                this.chatService = new ChatService();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * метод запускает приложение,слушает порт для подключение нового клиентского сокета,
     * создаёт для каждого соединения новый поток.
     */
    public void run() {

        this.initialize();

        while (true) {

            try {

                Socket socket = this.serverSocket.accept();
                new Thread(new ClientThread(socket, chatService)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
