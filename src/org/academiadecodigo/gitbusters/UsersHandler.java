package org.academiadecodigo.gitbusters;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.Socket;

public class UsersHandler implements Runnable {

    private String username;
    private Socket userSocket;
    private BufferedReader readInputFromUser;
    private BufferedWriter sendOutput;
    private StringInputScanner guess = new StringInputScanner();
    private Server server;
    private Game game;
    private boolean ready;

    public UsersHandler(String username, Socket userSocket, Server server) {

        this.username = username;
        this.userSocket = userSocket;
        this.server = server;
        this.ready = false;
        this.game = new Game(this);


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

                while(true){
                    game.showMenu();


                }
            }

        } catch (IOException | InterruptedException e) {
            e.getStackTrace();

        }
    }

    public void changeUserName(String newName) {

        broadcastMessage(username, " is now called " + newName);
        username = Server.changeUsername(newName);

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

    public String getUsername() {
        return username;
    }

    public StringInputScanner getGuess() {
        return guess;
    }

    public Server getServer() {
        return server;
    }

    public void quit() throws IOException {
        userSocket.close();
        Server.usersList.remove(this);
    }

    public Game getGame() {
        return game;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean getReady(){
        return ready;
    }
}
