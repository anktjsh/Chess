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
public class Castle extends Move {

    private final int rookx, rooky, rookx0, rooky0;

    public Castle(Piece pi, int a, int b, int c, int d, Piece pe, int f, int g, int h, int i) {
        super(pi, a, b, c, d, pe);
        rookx = f;
        rooky = g;
        rookx0 = h;
        rooky0 = i;
    }

    /**
     * @return the rookx
     */
    public int getRookx() {
        return rookx;
    }

    /**
     * @return the rooky
     */
    public int getRooky() {
        return rooky;
    }

    /**
     * @return the rookx0
     */
    public int getRookx0() {
        return rookx0;
    }

    /**
     * @return the rooky0
     */
    public int getRooky0() {
        return rooky0;
    }

}
