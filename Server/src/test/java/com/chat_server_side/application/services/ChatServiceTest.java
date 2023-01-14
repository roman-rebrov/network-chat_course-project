package com.chat_server_side.application.services;

import com.chat_server_side.application.connections.Clients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ChatServiceTest {
    @Test
    public void addMemberTest(){
        final ChatService service = new ChatService();
        service.addMember(Clients.newClient(null));

        Assertions.assertEquals(1, service.memberNumber());


        service.addMember(Clients.newClient(null));

        Assertions.assertEquals(2, service.memberNumber());

    }
}