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
public class Move {

    private final Piece piece;
    private final int prevx, prevy, finx, finy;
    private final Piece capture;

    public Move(Piece pi, int a, int b, int c, int d, Piece pe) {
        piece = pi;
        prevx = a;
        prevy = b;
        finx = c;
        finy = d;
        capture = pe;
    }

    /**
     * @return the piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @return the prevx
     */
    public int getPrevx() {
        return prevx;
    }

    /**
     * @return the prevy
     */
    public int getPrevy() {
        return prevy;
    }

    /**
     * @return the finx
     */
    public int getFinx() {
        return finx;
    }

    /**
     * @return the finy
     */
    public int getFiny() {
        return finy;
    }

    /**
     * @return the capture
     */
    public Piece getCapture() {
        return capture;
    }
}
