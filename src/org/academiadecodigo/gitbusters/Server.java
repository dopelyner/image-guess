package org.academiadecodigo.gitbusters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static final int DEFAULT_PORT = 8080;
    private BufferedWriter bufferedWriter;

    public static final String QUIT_GAME = "/quit";
    public static final String LIST_USERS = "/list";
    public static final String CHANGE_NAME = "/rename";
    public static List<UsersHandler> usersList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        Server server = new Server();
        server.establishConnections();

    }

    private void establishConnections() {

        System.out.println(Message.PORT_CONNECTION + DEFAULT_PORT);

        try {

            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            int userCount = 0;

            while (true) {

                Socket userSocket = serverSocket.accept();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));

                System.out.println(Message.NEW_CONNECTION + userSocket.getInetAddress().getHostAddress());
                userCount++;

                String username = Message.DEFAULT_USER + userCount;

                UsersHandler usersHandler = new UsersHandler(username, userSocket, this);
                welcome();

                UsersHandler.broadcastMessage("", usersHandler.getUsername() + Message.NEW_USER);
                usersList.add(usersHandler);

                Thread thread = new Thread(usersHandler);
                thread.setName(username);
                thread.start();

            }

        } catch (IOException e) {
            e.getStackTrace();

        }
    }

    public void welcome() {

        try {

            bufferedWriter.write(Image.imageGuess);
            bufferedWriter.write(Message.INSTRUCTIONS);
//       bufferedWriter.write(Image.separator);
            bufferedWriter.flush();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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