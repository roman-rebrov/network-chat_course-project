package com.chat_server_side.application.connections;


import java.util.HashSet;
import java.util.Set;

/**
 *
 *  <code><strong>class Auth</strong></code> - Утилитный класс
 *  служит для этапа преверки имени клиента и регистрации в системе.
 *
 * */
public class Auth {

    private static final Set<String> nicknames = new HashSet<>();
    private static final int MAX_NAME_LENGTH = 10;
    private static final int MIN_NAME_LENGTH = 3;


    /**
     * registration(Client client) служит для регистрации имени клиента.
     * @param client принимает объект клиента.
     * @return true если имя было успешно проверенно на уникальность и длинну.
     */
    public boolean registration(Client client) {

        client.sendMessage("Enter your nickname: ");

        while (true) {
            boolean result = false;
            String name = client.listen();

            if (name != null && name.equals("--exit")){
                return false;
            }
            if (name.length() > MAX_NAME_LENGTH || name.length() < MIN_NAME_LENGTH) {
                client.sendMessage("Enter other nickname: ");
                continue;
            }

            synchronized (nicknames) {
                result = nicknames.contains(name);

                if (!result) {

                    nicknames.add(name);
                    client.setNickname(name);
                    return true;

                } else {
                    client.sendMessage("Enter other nickname: ");
                }
            }
        }

    }

    public boolean logout(Client client){
        return nicknames.remove(client.getNickname());
    }
}
