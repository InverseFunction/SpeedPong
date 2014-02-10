package com.inversefunction.main;

import com.inversefunction.state.*;
import com.inversefunction.game.*;

public class Main {
    
    //Global Variables
    public static GameStateManager gsm;
    public static GameFrame gf;
    
    public static Pong pong;

    public static boolean isRunning = true;
    //Global Variables
    
    public static void setup() {
        gf = new GameFrame(gsm);
    }
    public static void loop() {
        switch(gsm.getState()) {
            case START:
                setup();
            break;
            case RUNNING:
                pong.start();
            break;
            case STOP:
            
            break;
        }
    }
    public static void checkState() {
        if(gsm.getState() == null) {
            gsm.setState(States.START);
        }
    }
    public static void main(String[] args) {
        gsm = new GameStateManager();
        pong = new Pong(gsm);
        checkState();
        while(isRunning) {
            loop();
        }
    }
    
}
