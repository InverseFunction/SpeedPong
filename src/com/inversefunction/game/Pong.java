package com.inversefunction.game;

import com.inversefunction.state.GameStateManager;
import com.inversefunction.state.GameState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.JPanel;
import com.inversefunction.game.util.Coords;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends GameFrame implements GameState, ActionListener{
    
    public GameStateManager gsm;
    public Timer timer;
    public JPanel panel;
    
    public Map<String, Coords> coords = new HashMap<>();
    
    public String[] entity = {"PLAYER1", "PLAYER2", "BALL" };
    
    public boolean ai = false;
    
    
    public Pong(GameStateManager gsm) {
        super(gsm);
        panel = new JPanel();
        this.gsm = gsm;
        timer = new Timer(100,this);
        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    Coords c = coords.get("PLAYER1");
                    System.out.println("Lol");
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        c.setDeltaY(-3);
                    }
                }});
    }
    
    @Override
    public void start() {
        timer.start();
        addFrame(new DrawPanel());
        createEntities();
    }
    
    @Override
    public void stop() {
        timer.stop();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
    }
    public void move() {
        Coords p1 = coords.get("PLAYER1");
        Coords p2 = coords.get("PLAYER2");
        Coords ball = coords.get("BALL");
        
        if(p1.getDeltaX() > 0 || p1.getDeltaX() < 0) {
            p1.setX(p1.getX() + p1.getDeltaX());
        }
        if(p1.getDeltaY() > 0 || p1.getDeltaY() < 0){
            p1.setY(p1.getY() + p1.getDeltaY());
        }
    }
    public void createEntities() {
        for(int i=0;i<entity.length;i++) {
            Coords cords = new Coords(getStartingX(i), getStartingY(i));
            coords.put(entity[i], cords);
        }
    }
    public int getStartingX(int index) {
        if(index == 0) {
            return 0;
        }
        if(index == 1) {
            return 383;
        }
        if(index == 2) {
            return 0;
        }
        return 0;
    }
    public int getStartingY(int index) {
        if(index == 0) {
            return 0;
        }
        if(index == 1) {
            return 0;
        }
        if(index == 2) {
            return 0;
        }
        return 0;
    }
    class DrawPanel extends JPanel {
        Coords p1 = coords.get("PLAYER1");
        Coords p2 = coords.get("PLAYER2");
        Coords ball = coords.get("BALL");
        
        @Override
        public void paintComponent(Graphics g) {
            g.fillRect(p1.getX(), p1.getY(), 10, 40);
            g.fillRect(p2.getX(), p2.getY(), 10, 40);
        }
    }
}
