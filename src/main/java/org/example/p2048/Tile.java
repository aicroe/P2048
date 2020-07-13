package org.example.p2048;

class Tile {

    private int value;

    public Tile(int value) {
        
        this.value = value;
    }
    
    public int getValue() {
        
        return value;
    }

    public void doubleValue() {
        
        value = value << 1;
    }
    
    public boolean hasReach2048() {
        
        return value == 2048;
    }

    @Override
    public boolean equals(Object obj) {

        Tile t = (Tile) obj;
        return t.value == this.value;
    }
}
