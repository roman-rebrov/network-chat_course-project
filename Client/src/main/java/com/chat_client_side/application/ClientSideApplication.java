package com.chat_client_side.application;


import com.chat_client_side.application.loggers.Logger;
import com.chat_client_side.application.loggers.Loggers;
import com.chat_client_side.application.services.ChatService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <code><strong>class ClientSideApplication</strong></code>
 * корневой загрузочный объект клиента.
 * Инициализарует и запускает соединения с сервером,
 * обрабатывает ввод сообщений  чата.
 */
public class ClientSideApplication {

    private Map<String, String> props = new HashMap<>();
    private ChatService chatService;
    private Logger logger = Loggers.getLogger();
    final String SETTING_PATH = "settings.txt";


    public ClientSideApplication() {
        this.initialize();
    }

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
     * метод инициализирует клиентский чат перед стартом,
     * устанавливает все необходимые настройки.
     */
    private void initialize() {
        this.propsRead();

        final int port = Integer.parseInt(props.get("port"));
        final String host = props.get("host");
        this.chatService = new ChatService(host, port, logger);

    }


    /**
     * метод запускает приложение, принимает введённые сообщения в терминале,
     * отправляет сообщения обработчику в чатсервис.
     */
    public void run() {

        try (
                Scanner scan = new Scanner(System.in);
        ) {

            while (!this.chatService.isSocketClosed()) {
                if (scan.hasNext()) {
                    String str = scan.nextLine();
                    this.chatService.sendMessage(str);
                }

            }

        } catch (NullPointerException exception) {

            System.err.println(exception.toString());
        }

    }
}