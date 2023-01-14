package com.chat_server_side.application.connections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ClientTest {
    @Test
    void setNicknameTest() {
        final Client client = Clients.newClient(null);
        final String nickname = "test";

        client.setNickname(nickname);

        Assertions.assertEquals(nickname, client.getNickname());
    }
}