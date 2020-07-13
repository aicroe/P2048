package org.example.p2048;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
    //instance variables
    private Game2048 game;
    private DrawTile[][] drawPanel;
    
    public static final int SEPARATION_LENGTH = 4;
    public static final int BLOCK_WIDTH = 119;
    public static final int BLOCK_CENTER = 119>>1;
    
    public static final int HW = 489;

    //Colors of different numbers
    public static final Color BACKGROUND = new Color(187,173,160);
    public static final Color DEFAULT_TILE = new Color(204,192,179);
    public static final Color TWO = new Color(238,228,218);
    public static final Color FOUR = new Color(237,224,200);
    public static final Color EIGHT = new Color(242,177,121);
    public static final Color SIXTEEN = new Color(245,149,98);
    public static final Color THIRTYTWO = new Color(246,124,95);
    public static final Color SIXTYFOUR = new Color(246,94,59);
    public static final Color REMAINING = new Color(237,204,97);

    //x and y positions of the four possible places of a tile.
    public static final int JUMPS[] = {SEPARATION_LENGTH,
                                      (BLOCK_WIDTH+SEPARATION_LENGTH),
                                      ((BLOCK_WIDTH<<1)+SEPARATION_LENGTH),
                                      (((BLOCK_WIDTH<<1)+BLOCK_WIDTH)+SEPARATION_LENGTH)};

    public final Font END = new Font("Lithograph", Font.BOLD, 50);
    public static final RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);

    public GamePanel(Game2048 game)
    {
        this.game = game;
        drawPanel = new DrawTile[Game2048.ROWS_COLS][Game2048.ROWS_COLS];
        setBackground(BACKGROUND);
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(HW,HW));

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        fillDrawPanel();
    }
    
    private void fillDrawPanel() {
        
        for (int row = 0; row < drawPanel.length; row++) {
            for (int col = 0; col < drawPanel[0].length; col++) {
                drawPanel[row][col] = new DrawTile(0);
                add(drawPanel[row][col].getLabel());
            }
        }
    }

    @Override
    public void paintComponent(Graphics gFirst)
    {
        super.paintComponent(gFirst);
        int[][] panel = game.getPanel();
        Graphics2D g = (Graphics2D) gFirst;
        g.setRenderingHints(rh);
        
        for (byte row=0; row<Game2048.ROWS_COLS; row++)
        {
            int YJump = JUMPS[row];
            for (byte col=0; col<Game2048.ROWS_COLS; col++)
            {
                int xJump = JUMPS[col];

                if (panel[row][col] == 0)
                {
                    drawPanel[row][col].update(0);
                    g.setColor(DEFAULT_TILE);
                    g.fillRoundRect(xJump, YJump, BLOCK_WIDTH, BLOCK_WIDTH, 80, 80);
                }
                else
                {
                    int value = panel[row][col];
                    DrawTile drawTile = drawPanel[row][col];
                    drawTile.update(value);
                    JLabel temp = drawTile.getLabel();
                    
                    if (value == 2)
                    {
                        g.setColor(TWO);
                        temp.setLocation(xJump+BLOCK_CENTER-18, YJump+BLOCK_CENTER-20);
                    }
                    else if (value == 4)
                    {
                        g.setColor(FOUR);
                        temp.setLocation(xJump+BLOCK_CENTER-18, YJump+BLOCK_CENTER-20);
                    }
                    else if (value == 8)
                    {
                        g.setColor(EIGHT);
                        temp.setLocation(xJump+BLOCK_CENTER-18, YJump+BLOCK_CENTER-20);
                    }
                    else if (value == 16)
                    {
                        g.setColor(SIXTEEN);
                        temp.setLocation(xJump+BLOCK_CENTER-28, YJump+BLOCK_CENTER-23);
                    }
                    else if (value == 32)
                    {
                        g.setColor(THIRTYTWO);
                        temp.setLocation(xJump+BLOCK_CENTER-28, YJump+BLOCK_CENTER-23);
                    }
                    else if (value == 64)
                    {
                        g.setColor(SIXTYFOUR);
                        temp.setLocation(xJump+BLOCK_CENTER-30, YJump+BLOCK_CENTER-23);
                    }
                    else if (value < 1024)
                    {
                        g.setColor(REMAINING);
                        temp.setLocation(xJump+BLOCK_CENTER-45, YJump+BLOCK_CENTER-20);
                    }
                    else
                    {
                        g.setColor(REMAINING);
                        temp.setFont(DrawTile.BIG_NUMBER);
                        temp.setLocation(xJump+BLOCK_CENTER-45, YJump+BLOCK_CENTER-15);
                    }

                    g.fillRoundRect(xJump, YJump, BLOCK_WIDTH, BLOCK_WIDTH, 80, 80);
                }
            }
        }
        checkEndGame();
    }
    
    private void checkEndGame() {
        
        int state = game.getGameState();
        if (state != Game2048.NOT_GAME_OVER) {
            if (state == Game2048.GAME_OVER_AND_LOSE) {
                System.out.println("YOU LOSE BAKA!!");
            } else {
                System.out.println("YOU WIN!!");
            }
        }
    }
}