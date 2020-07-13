package org.example.p2048;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;


public class Console implements Observer {
    
    private InputStream in;
    private PrintStream out;
    private Game2048 game;
    
    public Console(Game2048 game) {
        
        in = System.in;
        out = System.out;
        this.game = game;
    }
    
    private int read() {
        
        try {
            return in.read();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }
    
    private void printRowSeparator() {
        
        out.println(" ---- ---- ---- ---- ");
    }
    
    public void printGamePanel() {
        
        int[][] panel = game.getPanel();
        for (int[] row: panel) {
            printRowSeparator();
            for (int j = 0; j< row.length; j++) {
                if (row[j] != 0) {
                    out.print("|" + fillSpaces(row[j], 4));
                } else {
                    out.print("|    ");
                }
            }
            out.println("|");
        }
        printRowSeparator();
    }
    
    private String fillSpaces(int val, int size) {
        
        String str = val + "";
        for (int strlen = str.length(); strlen < size; strlen++) {
            str = " " + str;
        }
        return str;
    }
    
    public boolean gameEnd() {
     
        int gameState = game.getGameState();
        if (gameState == Game2048.NOT_GAME_OVER) {
            return false;
        }
        if (gameState == Game2048.GAME_OVER_AND_LOSE) {
            System.out.println("YOU LOSE BAKA!!");
        } else {
            System.out.println("YOU WIN!!");
        }
        return true;
    }
    
    public void nextMove() {
        
        int key = read();
        if (key == 'a') {
            game.moveLeft();
        } 
        else if (key == 'd') {
            game.moveRight();
        } 
        else if (key == 'w') {
            game.moveUp();
        } 
        else if (key == 's') {
            game.moveDown();
        } 
        else  if (key == 'r') {
            game.restart();
        } 
        else if (key == 'p') {
            System.out.println("Points: " + game.getPoints());
        }
    }

    @Override
    public void update() {
        
        printGamePanel();
    }
}
