package com.inversefunction.state;

import com.inversefunction.state.States;


public class GameStateManager {
    
    private States state;
    
    public States getState() {
        return this.state;
    }
    public void setState(States s) {
        this.state = s;
    }
    
}

