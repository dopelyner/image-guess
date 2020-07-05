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

    public Game(UsersHandler player){
        this.player = player;
        this.prompt = this.player.getServer().getPrompt();
        //this.scoreBoard = new HashMap<String, Integer>();
        this.out = this.player.getServer().getBufferedWriter();
    }


    public void showMenu() throws IOException {
        String[] options = {"Play the Game", "View Instructions", "Quit"};
        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage("Choose an Option");
        int i = prompt.getUserInput(menu);

        switch(i){
            case 1:
                startGame();
                break;
            case 2:
                instructionsMenu();
                break;
            case 3:
                player.setOn(false);
            default:
                showMenu();
        }

    }

    public void startGame() throws IOException {


        int i = 0;
        while(i < 10){

            //UsersHandler.broadcastMessage("",images.get(i));

                for (UsersHandler player : Server.usersList){
                    player.getGuess().setMessage("Guess The Image: \n"+Server.getImages().get(i));
                }

                String answer = prompt.getUserInput(player.getGuess());

                //colocar dentro de um método
                for (String key: ASCII.getList().keySet()) {
                    if(answer.equals(key)){
                        System.out.println("score++");
                        try {
                            out.write("Correct Answer" + "\n");
                            out.flush();
                            Thread.sleep(1000);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                        finalScore++;
                        player.setScore(1);
                    }
                }
                i++;
        }
        Collections.shuffle(Server.getImages());
        showFinalScore();
        try {
            showMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalScore = 0;

    }

    public void instructionsMenu() throws IOException {

        System.out.println("sout");
        try {
            out.write("\n");
            out.write("Here are the instructions" + "\n");
            out.write("\n");
            out.write("The Game has 10 rounds"  + "\n");
            out.write("Every round you have to guess the image"  + "\n");
            out.write("You only have one chance" + "\n");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
        String[] options = {"Back to Menu"};
        MenuInputScanner menu = new MenuInputScanner(options);
        menu.setMessage("Press 1 to go back to MainMenu");
        int i = prompt.getUserInput(menu);

        if(i == 1){
            showMenu();
        }
    }

    public void showFinalScore(){
        try {
            System.out.println("sout");
            String i = String.valueOf(finalScore);
            out.write(i);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
