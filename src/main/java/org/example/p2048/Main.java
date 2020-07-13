package org.example.p2048;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {

    public static void main(String argc[]) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Game2048 game = new Game2048();
        GameFrame window = new GameFrame(game);
        Console console = new Console(game);

        game.addObserver(window);
        game.addObserver(console);

        window.setSize(new Dimension(GamePanel.HW, GamePanel.HW));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.init();
        window.pack();
        window.setVisible(true);

        console.printGamePanel();
        while (true) {
            while (!console.gameEnd()) {
                console.nextMove();
            }
        }
    }
}
