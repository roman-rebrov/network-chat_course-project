package com.chat_client_side.application;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ClientSideApplicationTest {

    @Test
    public void propsReadTest(){
        final var app = new ClientSideApplication();
        final File file = new File(app.SETTING_PATH);
        final Map<String, String> props = new HashMap<>();


        Assertions.assertTrue(file.isFile());

        List<String> lines = null;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : lines) {
            try {
                String[] newProp = s.split("=");
                props.put(newProp[0], newProp[1]);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println(ex);
            }
        }

        Assertions.assertTrue(props.containsKey("port"));
        Assertions.assertTrue(props.containsKey("host"));
    }

}