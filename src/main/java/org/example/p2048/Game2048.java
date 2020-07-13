package org.example.p2048;

import java.util.ArrayList;
import java.util.Random;


public class Game2048 extends Observable {
    
    private Tile panel[][];
    private byte current_tiles;
    private boolean is_moved = false;
    private int points;

    public static final int GAME_OVER_AND_WIN = 0;
    public static final int GAME_OVER_AND_LOSE = 1;
    public static final int NOT_GAME_OVER = 2;
    
    public static final int DEFAULT_START_A = 2;
    public static final int DEFAULT_START_B = 4;
    public static final int RANDOM = 101;
    public static final byte ROWS_COLS = 4;
    public static final byte REAL_END = ROWS_COLS-1;
    public static final byte FAKE_END = 0;
    public static final byte LEFT_INCREMENT = 1;
    public static final byte RIGHT_INCREMENT = -1;
    
    public Game2048() {
        
        panel = new Tile[ROWS_COLS][ROWS_COLS];
        is_moved = false;
        points = 0;
        current_tiles = 0;
        generate(true);
    }
    
    public void restart() {
        
        panel = new Tile[ROWS_COLS][ROWS_COLS];
        is_moved = false;
        points = 0;
        current_tiles = 0;
        generate(true);
        notifyObservers();
    }
    
    public int[][] getPanel() {
        
        int[][] image = new int[ROWS_COLS][ROWS_COLS];
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                if (panel[row][col] != null) {
                    image[row][col] = panel[row][col].getValue();
                }
            }
        }
        return image;
    }
    
    public int getPoints() {
        
        return points;
    }
    
    private boolean horizontalPressed(byte left_or_right, byte increment)
    {
        byte compare = (byte)(increment+left_or_right);
        byte which_end = (byte)(REAL_END-left_or_right);

        for (byte row=0; row<ROWS_COLS; row++)
        {
            shiftRow(row,which_end,compare,increment);
        }

        //merge_row
        for (byte y=0; y<ROWS_COLS; y++)
        {
            byte x = which_end;
            while (x != compare && x != left_or_right)
            {
                if (panel[y][x] != null && 
                        panel[y][x+increment] != null && 
                        panel[y][x].getValue() == panel[y][x+increment].getValue())
                {
                    panel[y][x].doubleValue();
                    points += panel[y][x].getValue();
                    panel[y][x+increment] = null;
                    current_tiles--;
                    is_moved = true;
                    x = (byte)(x+(increment+increment));
                }
                else
                {
                    x = (byte)(x+increment);
                }
            }
            shiftRow(y,which_end,compare,increment);
        }

        return is_moved;

    }
    

    private void shiftRow(byte row, byte which_end, byte compare, byte increment)
    {
        ArrayList<Tile> temp_row = new ArrayList<>();
        byte col;
        for (col = which_end; col!=compare; col = (byte)(col+increment))
        {
            if (panel[row][col] != null)
            {
                temp_row.add(panel[row][col]);
            }
        }

        byte next = 0;
        for (col=which_end; col!=compare; col= (byte)(col+increment))
        {
            try {
                if (temp_row.get(next) != panel[row][col])
                {
                    is_moved = true;
                    panel[row][col] = temp_row.get(next);
                }
            }
            catch (IndexOutOfBoundsException E) {
                panel[row][col] = null;
            }

            next++;
        }
    }
    
    // Supone que siempre hay almenos una casilla vacia
    private RowCol getEmptyRowCol(Tile[][] tiles) {
        
        Random row_col = new Random();
        byte row = (byte) row_col.nextInt(ROWS_COLS);
        byte col = (byte) row_col.nextInt(ROWS_COLS);
        if (tiles[row][col] == null) {
            return new RowCol(row, col);
        } else {
            return getEmptyRowCol(tiles);
        }
    }

    private void generate(boolean is_moved)
    {
        if (is_moved)
        {
            RowCol empty = getEmptyRowCol(panel);
            byte row = empty.row;
            byte col = empty.col;
            
            Random rand = new Random();
            int two_four = rand.nextInt(RANDOM);

            if (two_four % 2 == 0)
            {
                panel[row][col] = new Tile(DEFAULT_START_A);
                current_tiles++;
            }
            else
            {
                panel[row][col] = new Tile(DEFAULT_START_B);
                current_tiles++;
            }
        }
    }

    private Tile[][] rotateLeft(Tile image[][]) 
    {
        Tile new_image[][] = new Tile[ROWS_COLS][ROWS_COLS];

        for (int y = 0; y < ROWS_COLS; y++) {
            for (int x = 0; x < ROWS_COLS; x++) {
                new_image[x][y] = image[y][REAL_END - x];
            }
        }

        return new_image;
    }

    private Tile[][] rotateRight(Tile image[][]) 
    {
        Tile new_image[][] = new Tile[ROWS_COLS][ROWS_COLS];
        for (int y = 0; y < ROWS_COLS; y++) {
            for (int x = 0; x < ROWS_COLS; x++) {
                new_image[x][REAL_END - y] = image[y][x];
            }
        }

        return new_image;
    }
    
    // Supone que el panel tiene 16 tiles, esta lleno
    private boolean canDoNextMove() {
        
        boolean check = false;
        for (byte x=0; x<ROWS_COLS; x++)
        {
            try{
                byte y=0;
                while  (y!=ROWS_COLS)
                {
                    if (y+1 <= REAL_END && x+1 <= REAL_END)
                    {
                        if (panel[x][y].getValue() == panel[x][y+1].getValue() || 
                                panel[x][y].getValue() == panel[x+1][y].getValue())
                        {
                            check = true;
                            break;
                        }
                        else
                        {
                            y++;
                        }
                    }
                    else if (y+1 <= REAL_END)
                    {
                        if (panel[x][y].getValue() == panel[x][y+1].getValue())
                        {
                            check = true;
                            break;
                        }
                        else
                        {
                            y++;
                        }
                    }
                    else
                    {
                        if (panel[x][y].getValue() == panel[x+1][y].getValue())
                        {
                            check = true;
                            break;
                        }
                        else
                        {
                            y++;
                        }
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                break;
            }
            if (check)
            {
                break;
            }
        }
        return check;
    }
    
    private boolean achievedGoal() {
        
        for (Tile[] row : panel) {
            for (Tile tile :row) {
                if (tile != null && tile.hasReach2048()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public synchronized int getGameState() {
        
        if (!achievedGoal())
        {
            if (current_tiles == 16)
            {
                if (!canDoNextMove())
                {
                    return GAME_OVER_AND_LOSE;
                }
            }
            return NOT_GAME_OVER;
        }
        else
        {
            return GAME_OVER_AND_WIN;
        }
    }
    
    public synchronized void moveLeft() {
        
        is_moved=false;
        is_moved = horizontalPressed(REAL_END, LEFT_INCREMENT);
        generate(is_moved);
        notifyObservers();
    }
    
    public synchronized void moveRight() {
        
        is_moved=false;
        is_moved = horizontalPressed(FAKE_END, RIGHT_INCREMENT);
        generate(is_moved);
        notifyObservers();
    }
    
    public synchronized void moveUp() {
        
        is_moved=false;
        panel = rotateRight(panel);
        is_moved = horizontalPressed(FAKE_END, RIGHT_INCREMENT);
        panel = rotateLeft(panel);
        generate(is_moved);
        notifyObservers();
    }
    
    public synchronized void moveDown() {
        
        is_moved=false;
        panel = rotateRight(panel);
        is_moved = horizontalPressed(REAL_END,LEFT_INCREMENT);
        panel = rotateLeft(panel);
        generate(is_moved);
        notifyObservers();
    }
}
