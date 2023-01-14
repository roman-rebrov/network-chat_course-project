package com.chat_client_side.application.loggers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;


class LoggerTest {

    static final String pathTest = "./src/test/logs.txt";

    @AfterAll
    public static void clear(){
        File path = new File(pathTest);
        path.delete();
    }

    @Test
    public void loggingTest(){
        final Logger logger = Loggers.getLogger(pathTest);

        Assertions.assertDoesNotThrow(() -> logger.log("123"));

        final File file = new File(pathTest);
        Assertions.assertTrue(file.isFile());
    }
}