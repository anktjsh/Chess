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
public class Bishop extends Piece {

    public Bishop(int a, int b, Side co) {
        super(a, b, co);
    }

    @Override
    public boolean validMove(int x, int y) {
        if (Math.abs(x - getLocation().getX()) == Math.abs(y - getLocation().getY())) {
            if (x < getLocation().getX()) {
                if (y < getLocation().getY()) {
                    for (int i = 1; true; i++) {
                        if (getLocation().getX() - i == x) {
                            return checkCapture(x, y);
                        }
                        if (!Board.getBoard().isEmpty(getLocation().getX() - i, getLocation().getY() - i)) {
                            return false;
                        }
                    }
                } else {
                    for (int i = 1; true; i++) {
                        if (getLocation().getX() - i == x) {
                            return checkCapture(x, y);
                        }
                        if (!Board.getBoard().isEmpty(getLocation().getX() - i, getLocation().getY() + i)) {
                            return false;
                        }
                    }
                }
            } else {
                if (y < getLocation().getY()) {
                    for (int i = 1; true; i++) {
                        if (getLocation().getX() + i == x) {
                            return checkCapture(x, y);
                        }
                        if (!Board.getBoard().isEmpty(getLocation().getX() + i, getLocation().getY() - i)) {
                            return false;
                        }
                    }
                } else {
                    for (int i = 1; true; i++) {
                        if (getLocation().getX() + i == x) {
                            return checkCapture(x, y);
                        }
                        if (!Board.getBoard().isEmpty(getLocation().getX() + i, getLocation().getY() + i)) {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ObservableList<Location> getValidMoves() {
        ObservableList<Location> locations = FXCollections.observableArrayList();
        for (int x = 1; true; x++) {
            if (!Board.getBoard().isValidSpace(getLocation().getX() + x, getLocation().getY() + x)) {
                break;
            } else {
                if (Board.getBoard().isEmpty(getLocation().getX() + x, getLocation().getY() + x)) {
                    locations.add(new Location(getLocation().getX() + x, getLocation().getY() + x));
                } else {
                    if (Board.getBoard().getPiece(getLocation().getX() + x, getLocation().getY() + x).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() + x, getLocation().getY() + x));
                    }
                    break;
                }
            }
        }
        for (int x = 1; true; x++) {
            if (!Board.getBoard().isValidSpace(getLocation().getX() + x, getLocation().getY() - x)) {
                break;
            } else {
                if (Board.getBoard().isEmpty(getLocation().getX() + x, getLocation().getY() - x)) {
                    locations.add(new Location(getLocation().getX() + x, getLocation().getY() - x));
                } else {
                    if (Board.getBoard().getPiece(getLocation().getX() + x, getLocation().getY() - x).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() + x, getLocation().getY() - x));
                    }
                    break;
                }
            }
        }
        for (int x = 1; true; x++) {
            if (!Board.getBoard().isValidSpace(getLocation().getX() - x, getLocation().getY() - x)) {
                break;
            } else {
                if (Board.getBoard().isEmpty(getLocation().getX() - x, getLocation().getY() - x)) {
                    locations.add(new Location(getLocation().getX() - x, getLocation().getY() - x));
                } else {
                    if (Board.getBoard().getPiece(getLocation().getX() - x, getLocation().getY() - x).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() - x, getLocation().getY() - x));
                    }
                    break;
                }
            }
        }
        for (int x = 1; true; x++) {
            if (!Board.getBoard().isValidSpace(getLocation().getX() - x, getLocation().getY() + x)) {
                break;
            } else {
                if (Board.getBoard().isEmpty(getLocation().getX() - x, getLocation().getY() + x)) {
                    locations.add(new Location(getLocation().getX() - x, getLocation().getY() + x));
                } else {
                    if (Board.getBoard().getPiece(getLocation().getX() - x, getLocation().getY() + x).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() - x, getLocation().getY() + x));
                    }
                    break;
                }
            }
        }
        return locations;
    }

}
