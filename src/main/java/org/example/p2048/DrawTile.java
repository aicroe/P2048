package org.example.p2048;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;


public class DrawTile  {
    
    private int value;
    private JLabel label;
    
    public static final Font FONT = new Font("Lithograph", Font.BOLD, 50);
    public static final Font BIG_NUMBER = new Font("Lithograph", Font.BOLD, 35);
    public static final Color DEFAULT = new Color(119,110,101);
    public static final Color REMAINING = new Color(249,246,242);
    
    public DrawTile(int value) {
        
        this.value = value;
        label = new JLabel();
        label.setSize(40,40);
        label.setFont(FONT);
        update(value);
    }
    
    public JLabel getLabel() {
        
        return label;
    }
    
    public void update(int _value) {
        
        if (this.value > 512 && _value <= 512) {
            label.setSize(40,40);
            label.setFont(FONT);
        }
        this.value = _value;
        if (value == 0) {
            label.setText("");
        } 
        else if (value <= 4){
            label.setForeground(DEFAULT);
            label.setText(Integer.toString(value));
        }
        else {
            label.setText(Integer.toString(value));
            label.setForeground(REMAINING);
        }
    }
}
