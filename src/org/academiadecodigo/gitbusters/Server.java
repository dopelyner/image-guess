package org.academiadecodigo.gitbusters;

import org.academiadecodigo.bootcamp.Prompt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static final int DEFAULT_PORT = 80;
    private BufferedWriter bufferedWriter;

    public static List<UsersHandler> usersList = Collections.synchronizedList(new ArrayList<>());
    public static List<String> images;

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    private Prompt prompt;


    public static void main(String[] args) {

        Server server = new Server();
        server.establishConnections();

    }

    private void establishConnections() {

        System.out.println(Message.PORT_CONNECTION + DEFAULT_PORT);

        try {

            images = new ArrayList<>();
            images.addAll(ASCII.getList().values());
            Collections.shuffle(images);


            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            int userCount = 0;

            while (true) {

                Socket userSocket = serverSocket.accept();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));

                PrintStream out = new PrintStream(userSocket.getOutputStream());
                InputStream in = userSocket.getInputStream();
                prompt = new Prompt(in, out);


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

    public static List<String> getImages() {
        return images;
    }
}