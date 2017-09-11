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
public class King extends Piece {

    public King(int a, int b, Side co) {
        super(a, b, co);
    }

    @Override
    public Move move(int x, int y) {
        if (x == getLocation().getX() && y == getLocation().getY() + 2) {
            Piece rook = Board.getBoard().getPiece(x, y + 1);
            return new Castle(this, getLocation().getX(), getLocation().getY(), x, y, rook, x, y + 1, x, y - 1);
        } else if (x == getLocation().getX() && y == getLocation().getY() - 2) {
            Piece rook = Board.getBoard().getPiece(x, y - 2);
            return new Castle(this, getLocation().getX(), getLocation().getY(), x, y, rook, x, y - 2, x, y + 1);
        } else {
            Piece cap = Board.getBoard().getPiece(x, y);
            return new Move(this, getLocation().getX(), getLocation().getY(), x, y, cap);
        }
    }

    @Override
    public boolean inCheckCheck(int x, int y) {
        return Board.inCheckCheck(this, x, y);
    }

    @Override
    public boolean validMove(int x, int y) {
        if (x == getLocation().getX()) {
            if (Math.abs(y - getLocation().getY()) == 1) {
                return checkCapture(x, y);
            } else {
                if (!Board.getBoard().isInCheck(getColor())) {
                    if (y == getLocation().getY() + 2) {
                        Piece rook = (Board.getBoard().getPiece(x, getLocation().getY() + 3));
                        if (rook instanceof Rook) {
                            if (rook.getMoves() == 0) {
                                if (getMoves() == 0) {
                                    return checkCapture(x, getLocation().getY() + 1) && checkCapture(x, getLocation().getY() + 2);
                                }
                            }
                        }
                    } else if (y == getLocation().getY() - 2) {
                        Piece rook = (Board.getBoard().getPiece(x, getLocation().getY() - 4));
                        if (rook instanceof Rook) {
                            if (rook.getMoves() == 0) {
                                if (getMoves() == 0) {
                                    return checkCapture(x, getLocation().getY() - 1) && checkCapture(x, getLocation().getY() - 2) && checkCapture(x, getLocation().getY() - 3);
                                }
                            }
                        }
                    }
                }
            }
        } else if (y == getLocation().getY()) {
            if (Math.abs(x - getLocation().getX()) == 1) {
                return checkCapture(x, y);
            }
        } else if (Math.abs(x - getLocation().getX()) == Math.abs(y - getLocation().getY())) {
            if (Math.abs(x - getLocation().getX()) == 1) {
                if (Math.abs(y - getLocation().getY()) == 1) {
                    return checkCapture(x, y);
                }
            }
        }

        return false;
    }

    @Override
    public ObservableList<Location> getValidMoves() {
        ObservableList<Location> locations = FXCollections.observableArrayList();
        if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY())) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY())) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY()));
            } else if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY()).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY()));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY() + 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY() + 1)) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY() + 1));
            } else if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY() + 1).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY() + 1));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY() - 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY() - 1)) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY() - 1));
            } else if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY() - 1).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX() + 1, getLocation().getY() - 1));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX(), getLocation().getY() + 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() + 1)) {
                locations.add(new Location(getLocation().getX(), getLocation().getY() + 1));
            } else if (Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() + 1).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX(), getLocation().getY() + 1));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX(), getLocation().getY() - 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 1)) {
                locations.add(new Location(getLocation().getX(), getLocation().getY() - 1));
            } else if (Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() - 1).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX(), getLocation().getY() - 1));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY())) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY())) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY()));
            } else if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY()).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY()));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY() + 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY() + 1)) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY() + 1));
            } else if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY() + 1).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY() + 1));
            }
        }
        if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY() - 1)) {
            if (Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY() - 1)) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY() - 1));
            } else if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY() - 1).getColor() != getColor()) {
                locations.add(new Location(getLocation().getX() - 1, getLocation().getY() - 1));
            }
        }
        if (getMoves() == 0) {
            if (!Board.getBoard().isInCheck(getColor())) {
                if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() + 1)) {
                    if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() + 2)) {
                        if (!Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() + 3)) {
                            Piece rook = Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() + 3);
                            if (rook instanceof Rook) {
                                if (rook.getMoves() == 0) {
                                    locations.add(new Location(getLocation().getX(), getLocation().getY() + 2));
                                }
                            }
                        }
                    }
                }
                if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 1)) {
                    if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 2)) {
                        if (Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 3)) {
                            if (!Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 4)) {
                                Piece rook = Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() - 4);
                                if (rook instanceof Rook) {
                                    if (rook.getMoves() == 0) {
                                        locations.add(new Location(getLocation().getX(), getLocation().getY() - 2));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return locations;
    }
}
