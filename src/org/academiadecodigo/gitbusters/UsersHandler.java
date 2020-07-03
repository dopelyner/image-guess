package org.academiadecodigo.gitbusters;

import java.io.*;
import java.net.Socket;

public class UsersHandler implements Runnable {

    private String username;
    private Socket userSocket;
    private BufferedReader readInputFromUser;
    private BufferedWriter sendOutput;
    private Server server;

    public UsersHandler(String username, Socket userSocket, Server server) {

        this.username = username;
        this.userSocket = userSocket;
        this.server = server;

        try {

            readInputFromUser = new BufferedReader(new InputStreamReader((userSocket.getInputStream())));
            sendOutput = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void run() {

        String messageFromUser;

        try {

            while (!userSocket.isClosed()) {

                messageFromUser = readInputFromUser.readLine();

                String[] splits = messageFromUser.split(" ");

                if (messageFromUser.startsWith(Server.CHANGE_NAME)) {

                    changeUserName(splits[1]);

                }

                if (messageFromUser.equals(Server.LIST_USERS)) {

                    showListUsers();

                }


                if (messageFromUser.equals(Server.QUIT_GAME)) {

                    quitChat();

                } else {

                    if (!messageFromUser.startsWith(Server.CHANGE_NAME)) {
                        broadcastMessage(username, messageFromUser);

                    }
                }
            }

        } catch (IOException e) {
            e.getStackTrace();

        }
    }

    public void showListUsers() {

        sendMessage("\n" + Message.USERS_IN_CHAT);
        sendMessage(server.getUsersInChat());
    }

    public void changeUserName(String newName) {

        broadcastMessage(username, " is now called " + newName);
        username = Server.changeUsername(newName);

    }

    public void quitChat() throws IOException {

        broadcastMessage(username, Message.LEFT_SERVER);
        readInputFromUser.close();
        sendOutput.close();
        userSocket.close();

    }

    public static void broadcastMessage(String username, String messageToBroadcast) {

        for (UsersHandler usersHandler : Server.usersList) {

            if (!usersHandler.getUsername().equals(username)) {
                usersHandler.sendMessage(username + " " + messageToBroadcast);

            }
        }
    }

    private void sendMessage(String messageToSend) {

        try {

            sendOutput.write(messageToSend);
            sendOutput.newLine();
            sendOutput.flush();

        } catch (IOException e) {
            e.getStackTrace();

        }
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    public String getUsername() {
        return username;
    }

}
