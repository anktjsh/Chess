/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author aniket
 */
public class Pawn extends Piece {

    public Pawn(int a, int b, Side co) {
        super(a, b, co);
    }

    @Override
    public boolean validMove(int x, int y) {
        if (getColor() == Side.WHITE) {
            if (getLocation().getY() == y) {
                if (getMoves() == 0 && x == getLocation().getX() - 2) {
                    return Board.getBoard().isEmpty(x + 1, y) && Board.getBoard().isEmpty(x, y);
                } else if (x == getLocation().getX() - 1) {
                    return Board.getBoard().isEmpty(x, y);
                }
            } else {
                if (Math.abs(x - getLocation().getX()) == 1 && Math.abs(y - getLocation().getY()) == 1) {
                    if (!Board.getBoard().isEmpty(x, y)) {
                        if (Board.getBoard().getPiece(x, y).getColor() != getColor()) {
                            return true;
                        }
                    } else {
                        Piece p = Board.getBoard().getPiece(x + 1, y);
                        if (p.getMoves() == 1) {
                            List<Move> mov = Board.getBoard().getMoves(p.getColor());
                            Move fin = mov.get(mov.size() - 1);
                            if (fin.getPiece().equals(p)) {
                                if (fin.getFiny() == fin.getPrevy()) {
                                    if (fin.getFinx() == fin.getPrevx() + 2) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (getLocation().getY() == y) {
                if (getMoves() == 0 && x == getLocation().getX() + 2) {
                    return Board.getBoard().isEmpty(x - 1, y) && Board.getBoard().isEmpty(x, y);
                } else if (x == getLocation().getX() + 1) {
                    return Board.getBoard().isEmpty(x, y);
                }
            } else {
                if (Math.abs(x - getLocation().getX()) == 1 && Math.abs(y - getLocation().getY()) == 1) {
                    if (!Board.getBoard().isEmpty(x, y)) {
                        if (Board.getBoard().getPiece(x, y).getColor() != getColor()) {
                            return true;
                        }
                    } else {
                        Piece p = Board.getBoard().getPiece(x - 1, y);
                        if (p.getMoves() == 1) {
                            List<Move> mov = Board.getBoard().getMoves(p.getColor());
                            Move fin = mov.get(mov.size() - 1);
                            if (fin.getPiece().equals(p)) {
                                if (fin.getFiny() == fin.getPrevy()) {
                                    if (fin.getFinx() == fin.getPrevx() - 2) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Move move(int x, int y) {
        if (getColor() == Side.WHITE && x == 0) {
            Piece cap = Board.getBoard().getPiece(x, y);
            return new Promotion(this, getLocation().getX(), getLocation().getY(), x, y, cap);
        } else if (getColor() == Side.BLACK && x == 7) {
            Piece cap = Board.getBoard().getPiece(x, y);
            return new Promotion(this, getLocation().getX(), getLocation().getY(), x, y, cap);
        } else {
            Piece cap = Board.getBoard().getPiece(x, y);
            if (cap == null & Math.abs(x - getLocation().getX()) == 1 && Math.abs(y - getLocation().getY()) == 1) {
                if (getColor() == Side.WHITE) {
                    cap = Board.getBoard().getPiece(x + 1, y);
                } else {
                    cap = Board.getBoard().getPiece(x - 1, y);
                }
            }
            return new Move(this, getLocation().getX(), getLocation().getY(), x, y, cap);
        }
    }

    @Override
    public ObservableList<Location> getValidMoves() {
        ObservableList<Location> locations = FXCollections.observableArrayList();
        if (getColor() == Side.WHITE) {
            if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY())) {
                if (Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY())) {
                    locations.add(new Location(getLocation().getX() - 1, getLocation().getY()));
                    if (getMoves() == 0 && Board.getBoard().isEmpty(getLocation().getX() - 2, getLocation().getY())) {
                        locations.add(new Location(getLocation().getX() - 2, getLocation().getY()));
                    }
                }
            }
            if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY() - 1)) {
                if (!Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY() - 1)) {
                    if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY() - 1).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() - 1, getLocation().getY() - 1));
                    }
                } else {
                    if (!Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 1)) {
                        Piece cap = Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() - 1);
                        if (cap.getColor() != getColor()) {
                            if (cap.getMoves() == 1) {
                                List<Move> mov = Board.getBoard().getMoves(cap.getColor());
                                Move fin = mov.get(mov.size() - 1);
                                if (fin.getPiece().equals(cap)) {
                                    if (fin.getFiny() == fin.getPrevy()) {
                                        if (fin.getFinx() == fin.getPrevx() + 2) {
                                            locations.add(new Location(getLocation().getX() - 1, getLocation().getY() - 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (Board.getBoard().isValidSpace(getLocation().getX() - 1, getLocation().getY() + 1)) {
                if (!Board.getBoard().isEmpty(getLocation().getX() - 1, getLocation().getY() + 1)) {
                    if (Board.getBoard().getPiece(getLocation().getX() - 1, getLocation().getY() + 1).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() - 1, getLocation().getY() + 1));
                    }
                } else {
                    if (!Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() + 1)) {
                        Piece cap = Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() + 1);
                        if (cap.getColor() != getColor()) {
                            if (cap.getMoves() == 1) {
                                List<Move> mov = Board.getBoard().getMoves(cap.getColor());
                                Move fin = mov.get(mov.size() - 1);
                                if (fin.getPiece().equals(cap)) {
                                    if (fin.getFiny() == fin.getPrevy()) {
                                        if (fin.getFinx() == fin.getPrevx() + 2) {
                                            locations.add(new Location(getLocation().getX() - 1, getLocation().getY() + 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY())) {
                if (Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY())) {
                    locations.add(new Location(getLocation().getX() + 1, getLocation().getY()));
                    if (getMoves() == 0 && Board.getBoard().isEmpty(getLocation().getX() + 2, getLocation().getY())) {
                        locations.add(new Location(getLocation().getX() + 2, getLocation().getY()));
                    }
                }
            }
            if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY() - 1)) {
                if (!Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY() - 1)) {
                    if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY() - 1).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() + 1, getLocation().getY() - 1));
                    }
                } else {
                    if (!Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() - 1)) {
                        Piece cap = Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() - 1);
                        if (cap.getColor() != getColor()) {
                            if (cap.getMoves() == 1) {
                                List<Move> mov = Board.getBoard().getMoves(cap.getColor());
                                Move fin = mov.get(mov.size() - 1);
                                if (fin.getPiece().equals(cap)) {
                                    if (fin.getFiny() == fin.getPrevy()) {
                                        if (fin.getFinx() == fin.getPrevx() - 2) {
                                            locations.add(new Location(getLocation().getX() + 1, getLocation().getY() - 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (Board.getBoard().isValidSpace(getLocation().getX() + 1, getLocation().getY() + 1)) {
                if (!Board.getBoard().isEmpty(getLocation().getX() + 1, getLocation().getY() + 1)) {
                    if (Board.getBoard().getPiece(getLocation().getX() + 1, getLocation().getY() + 1).getColor() != getColor()) {
                        locations.add(new Location(getLocation().getX() + 1, getLocation().getY() + 1));
                    }
                } else {
                    if (!Board.getBoard().isEmpty(getLocation().getX(), getLocation().getY() + 1)) {
                        Piece cap = Board.getBoard().getPiece(getLocation().getX(), getLocation().getY() + 1);
                        if (cap.getColor() != getColor()) {
                            if (cap.getMoves() == 1) {
                                List<Move> mov = Board.getBoard().getMoves(cap.getColor());
                                Move fin = mov.get(mov.size() - 1);
                                if (fin.getPiece().equals(cap)) {
                                    if (fin.getFiny() == fin.getPrevy()) {
                                        if (fin.getFinx() == fin.getPrevx() - 2) {
                                            locations.add(new Location(getLocation().getX() + 1, getLocation().getY() + 1));
                                        }
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
