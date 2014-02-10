package com.inversefunction.game.util;

public class Coords {
    private int x,y, dx, dy;
    
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getDeltaX() {
        return this.dx;
    }
    public void setDeltaX(int dx) {
        this.dx = dx;
    }
    public int getDeltaY() {
        return this.dy;
    }
    public void setDeltaY(int dy) {
        this.dy = dy;
    }
    
}
