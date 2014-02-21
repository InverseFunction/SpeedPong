package com.inversefunction.game;

import com.inversefunction.state.GameStateManager;
import com.inversefunction.state.GameState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.TimerTask;

public class Pong extends GameFrame implements GameState, ActionListener{
    
    public GameStateManager gsm;
    public Timer timer;
    public JPanel panel;
    
    public Random r = new Random();
    
    public Map<String, double[]> coords = new HashMap<>();
    
    public String[] entity = {"PLAYER1", "PLAYER2", "BALL" };
    
    public DrawPanel dp = new DrawPanel(this);
    
    public Rectangle p1 = new Rectangle();
    public Rectangle p2= new Rectangle();
    
    public boolean isRunning = false;
    
    public Timer cd;
    
    public int X = 0;
    public int Y = 1;
    public int DX = 2;
    public int DY = 3;
    public int arraySize = 4;
    
    public int hitCounter = 0;
    
    public double speedX = 1.5;
    
    public boolean ai = false;
    
    public Ellipse2D ball;
    
    public enum Collision {
        PLAYER1, PLAYER2, NONE
    }
    
    public boolean timerFinished = false;
    
    public Collision collision;
    
    public Collision getCollision() {
        return this.collision;
    }
    public void setCollision(Collision c) {
        this.collision = c;
    }
    
    public enum Score {
        PLAYER1, PLAYER2, NONE
    }
    
    public Score score;
    
    public Score getScore() {
        return this.score;
    }
    public void setScore(Score s) {
        this.score = s;
    }
    
    
    public Pong(GameStateManager gsm) {
        super(gsm);
        panel = new JPanel();
        this.gsm = gsm;
        timer = new Timer(10,this);
        cd = new Timer(30, t1);
        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        double[] a = coords.get("PLAYER1");
                        a[DY] = 5;
                        coords.put("PLAYER1", a);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        double[] a = coords.get("PLAYER1");
                        a[DY] = -5;
                        coords.put("PLAYER1", a);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_0) {
                        double[] a = coords.get("BALL");
                        speedX += 5;
                        a[DX] = speedX;
                        coords.put("BALL", a);
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        double[] a = coords.get("PLAYER1");
                        a[DY] = 0;
                        coords.put("PLAYER1", a);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        double[] a = coords.get("PLAYER1");
                        a[DY] = 0;
                        coords.put("PLAYER1", a);
                    }
                }
        });
    }
    
    private ActionListener t1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!timerFinished) {
                    timerFinished = true;
                }
            }
    
    };
    
    
    @Override
    public void start() {
        this.isRunning = true;
        timer.start();
        addFrame(dp);
        this.validate();
        createObjects();
        initBall();
        initPlayer();
    }
    
    @Override
    public void stop() {
        timer.stop();
    }
    
    @Override
    public boolean isRunning() {
        return this.isRunning;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        player2();
        checkCollision();
    }
    public void createObjects() {
        for(int i=0;i<entity.length;i++) {
            if(!coords.containsKey(entity[i])) {
                coords.put(entity[i], createObjects(i));
            }
        }
        double[] a = coords.get("BALL");
        ball = new Ellipse2D.Double((double)a[X], (double)a[Y], 10.0, 10.0);
    }
    public double[] createObjects(int index) {
        double[] a = new double[this.arraySize];
        a[X] = getStartingX(index);
        a[Y] = getStartingY(index);
        a[DX] = 0;
        a[DY] = 0;
        return a;
    }
    public void initBall() {
        double[] a = coords.get("BALL");
        a[DX] = speedX;
        Random rand = new Random();
        int temp = rand.nextInt(10);
        if(temp < 5) {
            a[DY] = 1;
        }
        if(temp > 5) {
            a[DY] = -1;
        }
        if(temp == 5 || temp < 0) {
            temp = rand.nextInt(10);
        }
        coords.put("BALL", a);
    }
    public void move() {
        double[] a = coords.get("PLAYER1");
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
        double[] b = coords.get("PLAYER2");
        if(b[DY] != 0 && onScreen(a[Y])) {
            b[Y] += b[DY];
        }
        if(!onScreen(b[Y])) {
            if(b[Y] >  333) {
                b[Y] = 333;
            }
            if(b[Y] < 0) {
                b[Y] = 0;
            }
        }
        coords.put("PLAYER2", b);
        coords.put("PLAYER1", a);
        double[] c = coords.get("BALL");
        c[DX] = checkSpeed();
        if(c[DX] != 0 && onScreenX(c[X])) {
            c[X] += c[DX];
        }
        c[Y] += c[DY];
        //System.out.println("Ball dx: " + c[DX]);
        coords.put("BALL", c);
        
    }
    public double checkSpeed() {
        double dx = this.getDeltaX("BALL");
        speedX = hitCounter * 0.3;
        if(dx < 0) {
            dx = -speedX;
        }
        if(dx > 0) {
            dx = speedX;
        }
        if(dx == 0) {
            dx = 0.3;
        }
        return dx;
        
    }
    public void player2() {
        double[] a = coords.get("PLAYER2");
        double[] b = coords.get("BALL");
        a[DY] = b[DY];
        coords.put("PLAYER2", a);
    }
    public void initPlayer() {
        double[] a = coords.get("PLAYER2");
        double[] b = coords.get("BALL");
        a[Y] = b[Y] - 15;
        coords.put("PLAYER2", a);
    }
    public void checkCollision() {
        double a[] = coords.get("BALL");
        if(!ballOnScreenY(a[Y])) {
            a[DY] = -a[DY];
        }
        if(p1.getBounds2D().intersects(ball.getBounds2D())) {
            checkCollision(Collision.PLAYER1);
        }
        if(p2.getBounds2D().intersects(ball.getBounds2D())) {
            checkCollision(Collision.PLAYER2);
        }
        coords.put("BALL", a);
    }
    public void checkCollision(Collision c) {
        double[] ball = coords.get("BALL");
        double[] player1 = coords.get("PLAYER1");
        double[] player2 = coords.get("PLAYER2");
        switch(c) {
            case PLAYER1:
                if(ball[DX] != speedX)
                    checkCollision(player1, ball);
            break;
            case PLAYER2:
                if(ball[DX] != -speedX)
                    checkCollision(player2, ball);
            break;
            case NONE:
                
            break;
        }
    }
    public void checkCollision(double[] a, double[] b) {
        
        b[DX] = -(b[DX]);
        if(a[DY] < 0) {
            b[DY] = -1;
        }
        if(a[DY] > 0) {
            b[DY] = 1;
        }
        if(!this.timerFinished) {
            if(!cd.isRunning()) {
                cd.start();
            }
        }
        
        if(this.timerFinished) {
            if(cd.isRunning()) {
                cd.stop();
            }
            hitCounter += 1;
            System.out.println("HitCounter: " + hitCounter);
            this.timerFinished = false;
            cd.start();
        }
        

        
        coords.remove("BALL");
        coords.put("BALL", b);
    }
    public void checkScore() {
        switch(getScore()) {
            case PLAYER1:
                
            break;
            case PLAYER2:
                
            break;
            case NONE:
                
            break;
        }
    }
    public void playerOneScore() {
        
    }
    public void playerTwoScore() {
        
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
    public double getX(String key) {
        return coords.get(key)[X];
    }
    public double getY(String key) {
        return coords.get(key)[Y];
    }
    public double getDeltaX(String key) {
        return coords.get(key)[DX];
    }
    public double getDeltaY(String key) {
        return coords.get(key)[DY];
    }
    public boolean onScreenX(double x) {
        return ((x<=383) && (x>= 0));
    }
    public boolean onScreen(double y) {
        return ((y<=335)&&(y>=0));
    }
    public boolean ballOnScreenY(double y) {
        return ((y<=365) && (y>=0));
    }
    class DrawPanel extends JPanel {
        public Pong pong;
        public DrawPanel(Pong pong) {
            this.pong = pong;
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //p1.setBounds(pong.getX("PLAYER1"), pong.getY("PLAYER1"), 10, 40);
            p2.setFrame(pong.getX("PLAYER2"), pong.getY("PLAYER2"), 10.0, 40.0);
            p1.setFrame(pong.getX("PLAYER1"), pong.getY("PLAYER1"), 10.0, 40.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.fill(ball);
            g2d.fill(p1);
            g2d.fill(p2);
            g.setColor(Color.red);
            //g.drawLine(pong.getX("PLAYER2") , pong.getY("PLAYER2") + 20, pong.getX("BALL") + 5, pong.getY("BALL") + 5);
            g.setColor(Color.BLUE);
            //g.drawLine(pong.getX("PLAYER1"), pong.getY("PLAYER1") + 20, pong.getX("BALL") + 5, pong.getY("BALL") + 5);
            Toolkit.getDefaultToolkit().sync();
            checkPosition();
            g2d.dispose();
            g.dispose();
            
        }
        public void checkPosition() {
            if((int)ball.getX() != pong.getX("BALL")) {
                ball.setFrame((double)pong.getX("BALL"), (double)pong.getY("BALL"), 10.0, 10.0);
            }
        }
    }
}
