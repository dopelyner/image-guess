package org.academiadecodigo.gitbusters;

import org.academiadecodigo.bootcamp.Prompt;

import java.io.*;
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

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    private BufferedWriter bufferedWriter;
    private Prompt prompt;

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
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));

                PrintStream out = new PrintStream(userSocket.getOutputStream());
                InputStream in = userSocket.getInputStream();
                prompt = new Prompt(in,out);


                System.out.println("New connection from: " + userSocket.getInetAddress().getHostAddress());
                userCount++;

                String username = "Nerd " + userCount;

                UsersHandler usersHandler = new UsersHandler(username, userSocket, this);
                welcome();

                UsersHandler.broadcastMessage("", usersHandler.getUsername() + " has entered the chat. Say hi !");
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

    public Prompt getPrompt() {
        return prompt;
    }

}