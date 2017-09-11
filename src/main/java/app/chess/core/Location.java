/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

/**
 *
 * @author aniket
 */
public class Location {

    private int x, y;

    public Location(int a, int b) {
        x = a;
        y = b;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    public void moveTo(int a, int b) {
        x = a;
        y = b;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location loc = (Location) obj;
            if (loc.getX() == getX() && loc.getY() == getY()) {
                return true;
            }
        }
        return false;
    }

}
