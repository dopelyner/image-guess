package org.academiadecodigo.gitbusters;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private static final int DEFAULT_PORT = 8080;
    public static final String QUIT_CHAT = "/quit";
    public static final String LIST_USERS = "/list";
    public static final String CHANGE_NAME = "/name";
    public static List<UsersHandler> usersList = Collections.synchronizedList(new ArrayList<>());

    private ASCII ascii;


    public static void main(String[] args) {

        Server server = new Server();
        server.establishConnections();

    }

    private void establishConnections() {

        System.out.println("Connecting to PORT:" + DEFAULT_PORT);

        try {

            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            int userCount = 0;

            while (true) {

                Socket userSocket = serverSocket.accept();

                System.out.println("New connection from: " + userSocket.getInetAddress().getHostAddress());
                userCount++;

                String username = "Nerd " + userCount;

                UsersHandler usersHandler = new UsersHandler(username, userSocket, this);
                usersList.add(usersHandler);

                welcome();

                UsersHandler.broadcastMessage("",usersHandler.getUsername() + " has entered the chat. Say hi !");

                Thread thread = new Thread(usersHandler);
                thread.setName(username);
                thread.start();

            }

        } catch (IOException e) {
            e.getStackTrace();

        }
    }

    public void welcome (){
        UsersHandler.broadcastMessage("", Image.imageGuess);
        UsersHandler.broadcastMessage("", Image.separator);
    }

    public static String changeUsername(String newName) {
        return newName;
    }

    public String getUsersInChat() {

        StringBuilder stringBuilder = new StringBuilder();

        for (UsersHandler usersHandler : usersList) {
            stringBuilder.append(usersHandler.getUsername() + "\n");
        }
        return stringBuilder.toString();
    }
}