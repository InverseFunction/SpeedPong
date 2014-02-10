package com.inversefunction.game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.inversefunction.state.*;

public class GameFrame extends JFrame {
    
    public GameStateManager gsm;
    
    public GameFrame(GameStateManager gsm) {
        this.gsm = gsm;
        buildFrame();
    }
    private void buildFrame() {
        setSize(400, 400);
        setFocusable(true);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.gsm.setState(States.RUNNING);
    }
    public void addFrame(JPanel panel) {
        this.add(panel);
    }
    
}
