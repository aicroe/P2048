package org.example.p2048;

public class RowCol {
    
    public byte row;
    public byte col;
    
    public RowCol(byte row, byte col) {
        
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        
        RowCol rc = (RowCol) obj;
        return rc.col == this.col && rc.row == this.row;
    }
    
    
}
