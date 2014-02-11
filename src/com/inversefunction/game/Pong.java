package com.inversefunction.game;

import com.inversefunction.state.GameStateManager;
import com.inversefunction.state.GameState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Pong extends GameFrame implements GameState, ActionListener{
    
    public GameStateManager gsm;
    public Timer timer;
    public JPanel panel;
    
    public Random r = new Random();
    
    public Map<String, int[]> coords = new HashMap<>();
    
    public String[] entity = {"PLAYER1", "PLAYER2", "BALL" };
    
    public int X = 0;
    public int Y = 1;
    public int DX = 2;
    public int DY = 3;
    public int arraySize = 4;
    
    public boolean ai = false;
    
    public Ball ball;
    
    
    public Pong(GameStateManager gsm) {
        super(gsm);
        panel = new JPanel();
        this.gsm = gsm;
        timer = new Timer(10,this);
        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        int[] a = coords.get("PLAYER1");
                        a[DY] = 5;
                        coords.put("PLAYER1", a);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        int[] a = coords.get("PLAYER1");
                        a[DY] = -5;
                        coords.put("PLAYER1", a);
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        int[] a = coords.get("PLAYER1");
                        a[DY] = 0;
                        coords.put("PLAYER1", a);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        int[] a = coords.get("PLAYER1");
                        a[DY] = 0;
                        coords.put("PLAYER1", a);
                    }
                }
        });
    }
    
    @Override
    public void start() {
        timer.start();
        addFrame(new DrawPanel(this));
        createObjects();
        initBall();
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
    public void createObjects() {
        for(int i=0;i<entity.length;i++) {
            if(!coords.containsKey(entity[i])) {
                coords.put(entity[i], createObjects(i));
            }
        }
        int[] a = coords.get("BALL");
        ball = new Ball((double)a[X], (double)a[Y], 10.0, 10.0);
    }
    public int[] createObjects(int index) {
        int[] a = new int[this.arraySize];
        a[X] = getStartingX(index);
        a[Y] = getStartingY(index);
        a[DX] = 0;
        a[DY] = 0;
        return a;
    }
    public void initBall() {
        int[] a = coords.get("BALL");
        a[DX] = r.nextInt(10);
        a[DY] = r.nextInt(10);
        coords.put("BALL", a);
    }
    public void move() {
        int[] a = coords.get("PLAYER1");
        if(a[DY] != 0 && onScreen(a[Y])) {
            a[Y] += a[DY]; 
        }
        if(!onScreen(a[Y])) {
            if(a[Y] > 333) {
                a[Y] = 333;
            }
            if(a[Y] < 0) {
                a[Y] = 0;
            }
        }
        coords.put("PLAYER1", a);
        int[] c = coords.get("BALL");
        if((c[DY] != 0 && onScreen(c[Y]) && c[DX] != 0)) {
            c[X] += c[DX];
            c[Y] += c[DY];
        }
        coords.put("BALL", c);
        
    }
    public int getStartingX(int index) {
        if(index == 0) {
            return 0;
        }
        if(index == 1) {
            return 383;
        }
        if(index == 2) {
            return 200;
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
            return 200;
        }
        return 0;
    }
    public int getX(String key) {
        return coords.get(key)[X];
    }
    public int getY(String key) {
        return coords.get(key)[Y];
    }
    public int getDeltaX(String key) {
        return coords.get(key)[DX];
    }
    public int getDeltaY(String key) {
        return coords.get(key)[DY];
    }
    public boolean onScreen(int y) {
        return ((y<=335)&&(y>=0));
    }
    class DrawPanel extends JPanel {
        public Pong pong;
        public DrawPanel(Pong pong) {
            this.pong = pong;
        }
        
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g.fillRect(pong.getX("PLAYER1"), pong.getY("PLAYER1"), 10, 40);
            g.fillRect(pong.getX("PLAYER2"), pong.getY("PLAYER2"), 10, 40);
            g2d.fill(ball);
            
        }
    }
}
