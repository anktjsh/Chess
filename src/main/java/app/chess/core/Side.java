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
public enum Side {
    WHITE, BLACK;

    public Side opposite() {
        if (this == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
}
