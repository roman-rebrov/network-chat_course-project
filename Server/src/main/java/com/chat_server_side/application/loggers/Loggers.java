package com.chat_server_side.application.loggers;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * <code><strong>class Loggers</strong></code> утилитный класс с фабричными методами
 * служит для реализации интерфейса Logger.
 */
public class Loggers {

    private Loggers(){}

    /**
     * method getLogger()
     * @return возвращает реализацию Logger.
     */
    public static Logger getLogger(){
        return getLogger(null);
    }

    public static Logger getLogger(String path){

        return new Logger() {


            private final Path file = path == null? Path.of("logs.txt") : Path.of(path);

            @Override
            public synchronized void log(String str) {


                StringBuilder builder = new StringBuilder("[ == ");
                builder.append(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm").format(ZonedDateTime.now()));
                builder.append(" == ");
                builder.append(str);
                builder.append(" ]\r\n");

                try(PrintWriter writer = new PrintWriter(new FileWriter(file.toFile(), true));){

                    writer.append(builder.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}

