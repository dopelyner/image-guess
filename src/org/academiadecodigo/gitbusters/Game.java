package org.academiadecodigo.gitbusters;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

public class Game {
    private Server server;
    private Prompt prompt;

    public Game(Server server){
        this.server = server;
        this.prompt = server.getPrompt();
    }

    public void showMenu(){
        String[] options = {"Play the Game", "View Instructions", "Quit"};
        MenuInputScanner menu = new MenuInputScanner(options);
        int i = prompt.getUserInput(menu);

        if(i == 2){
            instructionsMenu();
        } else {
            startGame();
        }

    }

    public void startGame(){
        int i = 0;
        while(i <= 10){
            server.welcome();
            StringInputScanner guess = new StringInputScanner();
            guess.setMessage("Guess The Image");
            prompt.getUserInput(guess);
            i++;
        }
    }

    public void instructionsMenu(){

        System.out.println("sout");

        String[] options = {"Back to Menu"};
        MenuInputScanner menu = new MenuInputScanner(options);
        int i = prompt.getUserInput(menu);

        if(i == 1){
            showMenu();
        }
    }
}
