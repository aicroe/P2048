package org.example.p2048;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;


public class GameFrame extends JFrame implements ActionListener, Observer {
    
    private Game2048 game;
    private JButton restart;
    private JLabel points;
    
    public static final KeyStroke LEFT = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
    public static final KeyStroke RIGHT = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
    public static final KeyStroke UP = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
    public static final KeyStroke DOWN = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
    
    public GameFrame(Game2048 game) {
        
        this.game = game;
    }
    
    private void updatePointsLabel() {
        
        points.setText("Points: " + game.getPoints());
    }
    
    public void init() {
        
        restart = new JButton("Restart");
        restart.addActionListener(this);
        points = new JLabel("Points: 0");
        
        JPanel contentPane = new JPanel(new BorderLayout());
        GamePanel gamePanel = new GamePanel(game);
        
        ActionMap actionMap = gamePanel.getActionMap();
        InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(UP, "UP");
        actionMap.put("UP", new UpAction());
        inputMap.put(DOWN, "DOWN");
        actionMap.put("DOWN", new DownAction());
        inputMap.put(LEFT, "LEFT");
        actionMap.put("LEFT", new LeftAction());
        inputMap.put(RIGHT, "RIGHT");
        actionMap.put("RIGHT", new RightAction());
      
        JPanel controlsPanel = new JPanel(new BorderLayout(10, 10));
        controlsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlsPanel.add(restart, BorderLayout.WEST);
        controlsPanel.add(points, BorderLayout.EAST);
        
        contentPane.add(gamePanel, BorderLayout.NORTH);
        contentPane.add(controlsPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        game.restart();
        init();
        pack();
        repaint();
    }

    @Override
    public void update() {
        
        repaint();
        updatePointsLabel();
    }

    
    private class UpAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            game.moveUp();
        }
    }
    
    private class DownAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            game.moveDown();
        }
    }
    
    private class LeftAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            game.moveLeft();
        }
    }
    
    private class RightAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            game.moveRight();
        }
    }
}
