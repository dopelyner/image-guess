package org.academiadecodigo.gitbusters;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownServiceException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Game {
    private UsersHandler player;
    private Prompt prompt;
    private BufferedWriter out;
    private int finalScore;

    public Game(UsersHandler player) {
        this.player = player;
        this.prompt = this.player.getServer().getPrompt();
        this.out = this.player.getServer().getBufferedWriter();
    }


    public void showMenu() throws IOException, InterruptedException {

        String[] options = {"Play the Game", "Get Ready!", "Scores", "Shuffle", "View Instructions", "Change name", "Quit"};
        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage("Choose an Option\n");
        int i = prompt.getUserInput(menu);

        switch (i) {
            case 1 -> startGame();
            case 2 -> {
                player.setReady(true);
                UsersHandler.broadcastMessage(player.getUsername(), "is ready\n");
                out.write("You are ready\n");
                out.flush();
            }
            case 3 -> {
                out.write("\n" + showAllFinalScore());
                out.flush();
            }
            case 4 -> {
                Collections.shuffle(Server.getImages());
                UsersHandler.broadcastMessage("", "Images have been shuffled\n");
                showMenu();
            }
            case 5 -> instructionsMenu();
            case 6 -> {
                StringInputScanner newname = new StringInputScanner();
                newname.setMessage("Choose name: ");
                player.changeUserName(prompt.getUserInput(newname));


            }
            case 7 -> player.quit();
            default -> showMenu();
        }

    }

    public void startGame() throws IOException, InterruptedException {

        finalScore = 0;
        int i = 0;

        if (!verifyPlayers()) {
            out.write("Must wait all players ready\n");
            out.flush();
            showMenu();
        }
        if (verifyPlayers()) {

            while (i < 10) {

                //UsersHandler.broadcastMessage("",images.get(i));
                for (UsersHandler player : Server.usersList) {
                    player.getGuess().setMessage(Server.getImages().get(i) + "\nGuess The Image: ");
                }

                String answer = prompt.getUserInput(player.getGuess());

                //colocar dentro de um método
                for (String key : ASCII.getList().keySet()) {
                    if (answer.equals(key)) {
                        System.out.println("score++");
                        try {
                            out.write("Correct Answer" + "\n");
                            out.flush();
                            Thread.sleep(1000);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                        finalScore++;
                    }
                }
                i++;
            }

            showFinalScore();
            player.setReady(false);

            out.write(Image.imageGuess);
            out.flush();

            showMenu();
        }

    }

    public void instructionsMenu() throws IOException, InterruptedException {

        System.out.println("sout");
        try {
            out.write("\n");
            out.write("Here are the instructions" + "\n");
            out.write("\n");
            out.write("The Game has 10 rounds" + "\n");
            out.write("Every round you have to guess the image" + "\n");
            out.write("You only have one chance" + "\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] options = {"Back to Menu"};
        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage("Press 1 to go back to MainMenu");
        int i = prompt.getUserInput(menu);

        if (i == 1) {
            showMenu();
        }
    }

    public void showFinalScore() {
        try {
            String i = "Your final score is: " + finalScore;
            out.write(i);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String showAllFinalScore() {

        StringBuilder scores = new StringBuilder();
        for (UsersHandler player : Server.usersList) {
            scores.append(player.getUsername() + ": " + player.getGame().finalScore + "\n");
        }
        return scores.toString();
    }

    public boolean verifyPlayers() {
        for (UsersHandler player : Server.usersList) {
            if (!player.getReady()) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyEnd() {
        for (UsersHandler player : Server.usersList) {
            if (!player.getEnd()) {
                return false;
            }
        }
        return true;

    }
}
