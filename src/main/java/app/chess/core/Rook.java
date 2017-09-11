/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author aniket
 */
public class Rook extends Piece {

    public Rook(int a, int b, Side co) {
        super(a, b, co);
    }

    @Override
    public boolean validMove(int x, int y) {
        if (getLocation().getX() == x) {
            if (y > getLocation().getY()) {
                for (int i = getLocation().getY() + 1; true; i++) {
                    if (i == y) {
                        return checkCapture(x, y);
                    }
                    if (!Board.getBoard().isEmpty(x, i)) {
                        return false;
                    }
                }
            } else {
                for (int i = getLocation().getY() - 1; true; i--) {
                    if (i == y) {
                        return checkCapture(x, y);
                    }
                    if (!Board.getBoard().isEmpty(x, i)) {
                        return false;
                    }
                }
            }
        } else if (getLocation().getY() == y) {
            if (x > getLocation().getX()) {
                for (int i = getLocation().getX() + 1; true; i++) {
                    if (i == x) {
                        return checkCapture(x, y);
                    }
                    if (!Board.getBoard().isEmpty(i, y)) {
                        return false;
                    }
                }
            } else {
                for (int i = getLocation().getX() - 1; true; i--) {
                    if (i == x) {
                        return checkCapture(x, y);
                    }
                    if (!Board.getBoard().isEmpty(i, y)) {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ObservableList<Location> getValidMoves() {
        ObservableList<Location> locations = FXCollections.observableArrayList();
        for (int x = getLocation().getX() + 1; x < 8; x++) {
            if (Board.getBoard().isEmpty(x, getLocation().getY())) {
                locations.add(new Location(x, getLocation().getY()));
            } else {
                if (Board.getBoard().getPiece(x, getLocation().getY()).getColor() != getColor()) {
                    locations.add(new Location(x, getLocation().getY()));
                }
                break;
            }
        }
        for (int x = getLocation().getX() - 1; x >= 0; x--) {
            if (Board.getBoard().isEmpty(x, getLocation().getY())) {
                locations.add(new Location(x, getLocation().getY()));
            } else {
                if (Board.getBoard().getPiece(x, getLocation().getY()).getColor() != getColor()) {
                    locations.add(new Location(x, getLocation().getY()));
                }
                break;
            }
        }
        for (int y = getLocation().getY() + 1; y < 8; y++) {
            if (Board.getBoard().isEmpty(getLocation().getX(), y)) {
                locations.add(new Location(getLocation().getX(), y));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX(), y).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX(), y));
                }
                break;
            }
        }
        for (int y = getLocation().getY() - 1; y >= 0; y--) {
            if (Board.getBoard().isEmpty(getLocation().getX(), y)) {
                locations.add(new Location(getLocation().getX(), y));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX(), y).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX(), y));
                }
                break;
            }
        }
        return locations;
    }

}
