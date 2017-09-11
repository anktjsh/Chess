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
public class Promotion extends Move {

    private Piece promote;

    public Promotion(Piece pi, int a, int b, int c, int d, Piece pe) {
        super(pi, a, b, c, d, pe);
    }

    /**
     * @return the promote
     */
    public Piece getPromotion() {
        return promote;
    }

    /**
     * @param promote the promote to set
     */
    public void setPromotion(Piece promote) {
        this.promote = promote;
    }

}
