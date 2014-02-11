package com.inversefunction.game;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ball extends Ellipse2D {
    
    public double x,y,w,h;
    
    public Ball(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getWidth() {
        return this.w;
    }

    @Override
    public double getHeight() {
        return this.h;
    }

    @Override
    public boolean isEmpty() {
        return this.isEmpty();
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        this.setFrame(x, y, w, h);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return this.getBounds2D();
    }
    
}
