/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

import javafx.collections.ObservableList;

/**
 *
 * @author aniket
 */
public class TempPiece extends Piece {

    public TempPiece(int a, int b, Side co) {
        super(a, b, co);
    }

    @Override
    public ObservableList<Location> getValidMoves() {
        return null;
    }

    @Override
    public boolean validMove(int x, int y) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TempPiece) {
            return true;
        }
        return false;
    }

}
