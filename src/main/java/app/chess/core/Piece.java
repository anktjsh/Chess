/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author aniket
 */
public abstract class Piece {

    protected int moves;
    protected final Location loc;
    private final Side color;
    private final long identifier;

    public Piece(int a, int b, Side co) {
        moves = 0;
        color = co;
        loc = new Location(a, b);
        identifier = PieceUtils.getNewIndentifier();
    }

    public int getMoves() {
        return moves;
    }

    public Side getColor() {
        return color;
    }

    public void minusMoves() {
        moves--;
    }

    public void addMoves() {
        moves++;
    }

    public boolean inCheckCheck(int x, int y) {
        Set<Location> locat = new HashSet<>();
        int a = getLocation().getX();
        int b = getLocation().getY();
        getLocation().moveTo(x, y);
        Board.getBoard().removeObject(a, b);
        Board.getBoard().placeObject(this, x, y);
        List<Piece> pieces = Board.getBoard().getPieces(getColor().opposite());
        for (int i = pieces.size() - 1; i >= 0; i--) {
            Piece p = pieces.get(i);
            if (p.getLocation().getX() != x || p.getLocation().getY() != y) {
                locat.addAll(p.getValidMoves());
            }
        }
        getLocation().moveTo(a, b);
        Board.getBoard().removeObject(x, y);
        Board.getBoard().placeObject(this, a, b);
        Board.getBoard().checkPlacement();

        King k = Board.getBoard().getKing(getColor());

        //remember this was false
        if (k == null) {
            return true;
        }
        int xcomp = k.getLocation().getX();
        int ycomp = k.getLocation().getY();
        for (Location la : locat) {
            if (la.getX() == xcomp && la.getY() == ycomp) {
                return false;
            }
        }
        return true;
    }

    Move move(int x, int y) {
        Piece cap = Board.getBoard().getPiece(x, y);
        Move m = new Move(this, getLocation().getX(), getLocation().getY(), x, y, cap);
        return m;
    }

    public Location getLocation() {
        return loc;
    }

    public abstract ObservableList<Location> getValidMoves();

    protected boolean checkCapture(int a, int b) {
        if (Board.getBoard().isEmpty(a, b)) {
            return inCheckCheck(a, b);
        }
        Piece s = Board.getBoard().getPiece(a, b);
        if (s.getColor() != getColor()) {
            return inCheckCheck(a, b);
        }
        return false;
    }

    public abstract boolean validMove(int x, int y);

    private static final ArrayList<Image> IMAGES = new ArrayList<>();

    static {
        IMAGES.add(new Image(Piece.class.getResourceAsStream("blackpawn.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("blackrook.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("blackknight.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("blackbishop.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("blackqueen.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("blackking.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("whitepawn.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("whiterook.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("whiteknight.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("whitebishop.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("whitequeen.png")));
        IMAGES.add(new Image(Piece.class.getResourceAsStream("whiteking.png")));
    }

    public static Image getWhiteKing() {
        return IMAGES.get(11);
    }

    public static Image getBlackKing() {
        return IMAGES.get(5);
    }

    public static Image getImage(Piece p) {
        if (p.getColor() == Side.WHITE) {
            if (p instanceof Rook) {
                return IMAGES.get(7);
            } else if (p instanceof Bishop) {
                return IMAGES.get(9);
            } else if (p instanceof Queen) {
                return IMAGES.get(10);
            } else if (p instanceof King) {
                return IMAGES.get(11);
            } else if (p instanceof Knight) {
                return IMAGES.get(8);
            } else if (p instanceof Pawn) {
                return IMAGES.get(6);
            }
        } else {
            if (p instanceof Rook) {
                return IMAGES.get(1);
            } else if (p instanceof Bishop) {
                return IMAGES.get(3);
            } else if (p instanceof Queen) {
                return IMAGES.get(4);
            } else if (p instanceof King) {
                return IMAGES.get(5);
            } else if (p instanceof Knight) {
                return IMAGES.get(2);
            } else if (p instanceof Pawn) {
                return IMAGES.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Piece) {
            Piece p = (Piece) obj;
            return (identifier == p.identifier);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.moves;
        hash = 47 * hash + Objects.hashCode(this.loc);
        hash = 47 * hash + Objects.hashCode(this.color);
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getName().replaceAll("app.chess.core.code.", "") + " : " + getColor().name() + " : " + getLocation().toString();
    }

}
