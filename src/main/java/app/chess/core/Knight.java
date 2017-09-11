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
public class Knight extends Piece {

    public Knight(int a, int b, Side co) {
        super(a, b, co);
    }

    @Override
    public boolean validMove(int x, int y) {
        if ((Math.abs(x - getLocation().getX()) == 1 && Math.abs(y - getLocation().getY()) == 2)
                || (Math.abs(x - getLocation().getX()) == 2 && Math.abs(y - getLocation().getY()) == 1)) {
            return checkCapture(x, y);
        }
        return false;
    }

    @Override
    public ObservableList<Location> getValidMoves() {
        ObservableList<Location> locations = FXCollections.observableArrayList();
        if (Board.getBoard().isValidSpace(getLocation().getX() - 2, getLocation().getY() + 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 2, getLocation().getY() + 1)) {
                locations.add(new Location(getLocation().getX() - 2, getLocation().getY() + 1));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() - 2, getLocation().getY() + 1).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() - 2, getLocation().getY() + 1));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() - 2, getLocation().getY() - 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 2, getLocation().getY() - 1)) {
                locations.add(new Location(getLocation().getX() - 2, getLocation().getY() - 1));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() - 2, getLocation().getY() - 1).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() - 2, getLocation().getY() - 1));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() + 2, getLocation().getY() + 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 2, getLocation().getY() + 1)) {
                locations.add(new Location(getLocation().getX() + 2, getLocation().getY() + 1));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() + 2, getLocation().getY() + 1).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() + 2, getLocation().getY() + 1));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() + 2, getLocation().getY() - 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 2, getLocation().getY() - 1)) {
                locations.add(new Location(getLocation().getX() + 2, getLocation().getY() - 1));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() + 2, getLocation().getY() - 1).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() + 2, getLocation().getY() - 1));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY() + 2)) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY() + 2)) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY() + 2));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY() + 2).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() - 1, getLocation().getY() + 2));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY() + 2)) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY() + 2)) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY() + 2));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY() + 2).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() + 1, getLocation().getY() + 2));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY() - 2)) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY() - 2)) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY() - 2));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY() - 2).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() - 1, getLocation().getY() - 2));
                }
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY() - 2)) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY() - 2)) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY() - 2));
            } else {
                if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY() - 2).getColor() != getColor()) {
                    locations.add(new Location(getLocation().getX() + 1, getLocation().getY() - 2));
                }
            }
        }
        return locations;
    }

}
