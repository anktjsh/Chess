/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 *
 * @author aniket
 */
public class Board {

//    private final static char TRUE_CHESS_BOARD[][] = {
//        {'r', 'k', 'b', 'q', 'a', 'b', 'k', 'r'},
//        {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
//        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
//        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
//        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
//        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
//        {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
//        {'R', 'K', 'B', 'Q', 'A', 'B', 'K', 'R'}};

    private static boolean AI_PROCESSING = false;
    private static Board board;

    public static void print() {
        getBoard().printAll();
    }

    public void printAll() {
        for (Piece[] p : pieces) {
            System.out.println(Arrays.toString(p));
        }
    }
    private final Piece[][] pieces;
    private GameListener game;
    private final ObservableList<Piece> white, black;
    private final ObservableList<Move> whiteMoves, blackMoves;
    private final ObservableList<Piece> captures;
    private final ObservableList<Notation> notation;
    private boolean whiteInCheck, blackInCheck;
//    private char[][] boardRep;

    public boolean isInCheck(Side s) {
        if (s == Side.WHITE) {
            return whiteInCheck;
        }
        return blackInCheck;
    }

    public ObservableList<Notation> getMoves() {
        return notation;
    }

    public ObservableList<Move> getMoves(Side s) {
        if (s == Side.WHITE) {
            return whiteMoves;
        }
        return blackMoves;
    }

    public static Board getBoard() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public King getKing(Side s) {
        if (s == Side.WHITE) {
            for (Piece p : white) {
                if (p instanceof King) {
                    return (King) p;
                }
            }
        } else {
            for (Piece p : black) {
                if (p instanceof King) {
                    return (King) p;
                }
            }
        }
        return null;
    }

    public ObservableList<Piece> getPieces(Side s) {
        if (s == Side.WHITE) {
            return white;
        }
        return black;
    }

    public void setGameListener(GameListener g) {
        game = g;
    }

    public boolean isValidSpace(int x, int y) {
        return (x >= 0 && x <= 7) && (y >= 0 && y <= 7);
    }

    public boolean isValidSpace(Location l) {
        return isValidSpace(l.getX(), l.getY());
    }

    public boolean isEmpty(Location l) {
        return isEmpty(l.getX(), l.getY());
    }

    public boolean isEmpty(int x, int y) {
        return pieces[x][y] == null;
    }

    public List<Piece> getCaptures() {
        return captures;
    }

    private Board() {
        pieces = new Piece[8][8];
        white = FXCollections.observableArrayList();
        black = FXCollections.observableArrayList();
        whiteMoves = FXCollections.observableArrayList();
        blackMoves = FXCollections.observableArrayList();
        captures = FXCollections.observableArrayList();
        notation = FXCollections.observableArrayList();
//        boardRep = new char[8][8];
    }

    public void reset() {
        white.clear();
        black.clear();
        notation.clear();
        whiteMoves.clear();
        blackMoves.clear();
        for (Piece p[] : pieces) {
            Arrays.fill(p, null);
        }
//        for (int x = 0; x < TRUE_CHESS_BOARD.length; x++) {
//            System.arraycopy(TRUE_CHESS_BOARD[x], 0, boardRep[x], 0, TRUE_CHESS_BOARD[x].length);
//        }
    }

    public void init() {
        black.addAll(
                new Pawn(1, 0, Side.BLACK), new Pawn(1, 1, Side.BLACK),
                new Pawn(1, 2, Side.BLACK), new Pawn(1, 3, Side.BLACK),
                new Pawn(1, 4, Side.BLACK), new Pawn(1, 5, Side.BLACK),
                new Pawn(1, 6, Side.BLACK), new Pawn(1, 7, Side.BLACK),
                new Rook(0, 0, Side.BLACK), new Knight(0, 1, Side.BLACK),
                new Bishop(0, 2, Side.BLACK), new Queen(0, 3, Side.BLACK),
                new King(0, 4, Side.BLACK), new Bishop(0, 5, Side.BLACK),
                new Knight(0, 6, Side.BLACK), new Rook(0, 7, Side.BLACK));
        white.addAll(
                new Pawn(6, 0, Side.WHITE), new Pawn(6, 1, Side.WHITE),
                new Pawn(6, 2, Side.WHITE), new Pawn(6, 3, Side.WHITE),
                new Pawn(6, 4, Side.WHITE), new Pawn(6, 5, Side.WHITE),
                new Pawn(6, 6, Side.WHITE), new Pawn(6, 7, Side.WHITE),
                new Rook(7, 0, Side.WHITE), new Knight(7, 1, Side.WHITE),
                new Bishop(7, 2, Side.WHITE), new Queen(7, 3, Side.WHITE),
                new King(7, 4, Side.WHITE), new Bishop(7, 5, Side.WHITE),
                new Knight(7, 6, Side.WHITE), new Rook(7, 7, Side.WHITE));
        for (Piece p : black) {
            addPiece(p, p.getLocation());
        }
        for (Piece p : white) {
            addPiece(p, p.getLocation());
        }
    }

    public void move(Piece p, Location l) throws InCheckException {
        move(p, l.getX(), l.getY());
    }

    public void move(Piece p, int x, int y) throws InCheckException {
        //can be removed properly
        if (!p.inCheckCheck(x, y)) {
            throw new InCheckException("moving into check");
        }
        Move m = p.move(x, y);
        if (m instanceof Castle) {
            if (m.getFiny() > m.getPrevy()) {
                for (int i = m.getPrevy(); i < m.getFiny(); i++) {
                    if (!p.inCheckCheck(x, i)) {
                        throw new InCheckException("castle");
                    }
                }
            } else {
                for (int i = m.getFiny() - 1; i >= m.getPrevy(); i--) {
                    if (!p.inCheckCheck(x, i)) {
                        throw new InCheckException("castle");
                    }
                }
            }
            move((Castle) m);
        } else if (m instanceof Promotion) {
            move((Promotion) m);
        } else {
            move(m);
        }
    }

    private void addNotation(String s) {
        if (!AI_PROCESSING) {
            if (notation.isEmpty()) {
                Notation n = new Notation(notation.size() + 1);
                n.setWhite(s);
                notation.add(n);
            } else if (notation.get(notation.size() - 1).getBlack().isEmpty()) {
                Notation remove = notation.remove(notation.size() - 1);
                remove.setBlack(s);
                notation.add(remove);
            } else {
                Notation n = new Notation(notation.size() + 1);
                n.setWhite(s);
                notation.add(n);
            }
        }
    }

    private void move(Castle c) {
        removePiece(c.getPiece().getLocation());
        removePiece(c.getCapture().getLocation());
        c.getCapture().addMoves();
        c.getPiece().addMoves();
        c.getCapture().getLocation().moveTo(c.getRookx0(), c.getRooky0());
        c.getPiece().getLocation().moveTo(c.getFinx(), c.getFiny());
        addPiece(c.getPiece(), c.getPiece().getLocation());
        addPiece(c.getCapture(), c.getCapture().getLocation());
        if (c.getPiece().getColor() == Side.WHITE) {
            whiteMoves.add(c);
        } else {
            blackMoves.add(c);
        }
        String add;
        if (Math.abs(c.getRooky0() - c.getRooky()) == 3) {
            add = ("0-0-0");
        } else {
            add = ("0-0");
        }
        addNotation(add);
    }

    private void move(Promotion m) {
        Piece p = null;
        if (AI_PROCESSING) {
            p = new Queen(m.getFinx(), m.getFiny(), m.getPiece().getColor());
        } else if (game != null) {
            p = game.promotion((Pawn) m.getPiece());
        }
        if (p != null) {
            removePiece(m.getPiece().getLocation());
            if (m.getPiece().getColor() == Side.WHITE) {
                whiteMoves.add(m);
                white.remove(m.getPiece());
                white.add(p);
                if (m.getCapture() != null) {
                    black.remove(m.getCapture());
                    captures.add(m.getCapture());
                    removePiece(m.getCapture().getLocation());
                }
            } else {
                blackMoves.add(m);
                black.remove(m.getPiece());
                black.add(p);
                if (m.getCapture() != null) {
                    white.remove(m.getCapture());
                    captures.add(m.getCapture());
                    removePiece(m.getCapture().getLocation());
                }
            }
            p.moves = m.getPiece().getMoves() + 1;
            p.getLocation().moveTo(m.getFinx(), m.getFiny());
            addPiece(p, p.getLocation());
            m.setPromotion(p);
            addNotation(getNotation(m.getPiece(), m.getPrevx(), m.getPrevy(), m.getFinx(), m.getFiny(), m.getCapture() != null) + getLetter(p));
            if (!AI_PROCESSING) {
                inCheck(p.getColor());
            }
        }
    }

    private void move(Move m) {
        removePiece(m.getPiece().getLocation());
        if (m.getPiece().getColor() == Side.WHITE) {
            whiteMoves.add(m);
            if (m.getCapture() != null) {
                black.remove(m.getCapture());
                captures.add(m.getCapture());
                removePiece(m.getCapture().getLocation());
            }
        } else {
            blackMoves.add(m);
            if (m.getCapture() != null) {
                white.remove(m.getCapture());
                captures.add(m.getCapture());
                removePiece(m.getCapture().getLocation());
            }
        }
        m.getPiece().addMoves();
        m.getPiece().getLocation().moveTo(m.getFinx(), m.getFiny());
        addPiece(m.getPiece(), m.getPiece().getLocation());
        addNotation(getNotation(m.getPiece(), m.getPrevx(), m.getPrevy(), m.getFinx(), m.getFiny(), m.getCapture() != null));
        if (!AI_PROCESSING) {
            inCheck(m.getPiece().getColor());
        }
    }

    private String getNotation(Piece p, int x, int y, int x0, int y0, boolean capture) {
        if (capture) {
            if (p instanceof Pawn) {
                return (char) (97 + x) + "x" + (char) (97 + y0) + (8 - x0) + "";
            } else {
                return getLetter(p) + "x" + (char) (97 + y0) + (8 - x0);
            }
        } else {
            if (p instanceof Pawn) {
                return (char) (97 + y0) + "" + (8 - x0);
            } else {
                return "" + getLetter(p) + (char) (97 + y0) + (8 - x0);
            }
        }
    }

    private char getLetter(Piece p) {
        if (p instanceof Queen) {
            return 'Q';
        } else if (p instanceof King) {
            return 'K';
        } else if (p instanceof Rook) {
            return 'R';
        } else if (p instanceof Bishop) {
            return 'B';
        } else if (p instanceof Knight) {
            return 'N';
        }
        return ' ';
    }

    void removeObject(int x, int y) {
        pieces[x][y] = null;
    }

    private void removePiece(Location l) {
        removePiece(l.getX(), l.getY());
    }

    private void removePiece(int a, int b) {
        pieces[a][b] = null;
        if (game != null && !AI_PROCESSING) {
            game.pieceRemoved(a, b);
        }
    }

    void placeObject(Piece p, int x, int y) {
        pieces[x][y] = p;
    }

    private void addPiece(Piece p, int x, int y) {
        pieces[x][y] = p;
        if (game != null && !AI_PROCESSING) {
            game.pieceAdded(p, x, y);
        }
    }

    private void addPiece(Piece p, Location l) {
        addPiece(p, l.getX(), l.getY());
    }

    public Piece getPiece(int a, int b) {
        return pieces[a][b];
    }

    public void inCheck(Side s) {
        King king = getKing(Side.BLACK);
        Set<Location> loc = new HashSet<>();
        for (Piece pa : white) {
            loc.addAll(pa.getValidMoves());
        }
        blackInCheck = false;
        if (king == null) {
            blackInCheck = true;
        } else {
            for (Location l : loc) {
                if (l.getX() == king.getLocation().getX() && l.getY() == king.getLocation().getY()) {
                    blackInCheck = true;
                    break;
                }
            }
        }
        if (blackInCheck) {
            List<Piece> other = Board.getBoard().getPieces((Side.BLACK));
            Set<Location> locations = new HashSet<>();
            for (Piece pa : other) {
                ObservableList<Location> valid = pa.getValidMoves();
                for (int x = valid.size() - 1; x >= 0; x--) {
                    if (!pa.inCheckCheck(valid.get(x).getX(), valid.get(x).getY())) {
                        valid.remove(x);
                    }
                }
                locations.addAll(valid);
            }
            if (locations.isEmpty()) {
                if (game != null) {
                    game.gameOver(Side.BLACK, "CHECKMATE");
                }
                notation.get(notation.size() - 1).setWhite(
                        notation.get(notation.size() - 1).getWhite() + "++");
                Notation n = new Notation(notation.size() + 1);
                n.setWhite("1-0");
                notation.add(n);
            } else {
                if (game != null) {
                    game.inCheck(Side.BLACK);
                }
                notation.get(notation.size() - 1).setWhite(
                        notation.get(notation.size() - 1).getWhite() + "+");
            }
        }
        king = getKing(Side.WHITE);
        loc.clear();
        for (Piece pa : black) {
            loc.addAll(pa.getValidMoves());
        }
        whiteInCheck = false;
        if (king == null) {
            whiteInCheck = true;
        } else {
            for (Location l : loc) {
                if (l.getX() == king.getLocation().getX() && l.getY() == king.getLocation().getY()) {
                    whiteInCheck = true;
                    break;
                }
            }
        }
        if (whiteInCheck) {
            List<Piece> other = Board.getBoard().getPieces((Side.WHITE));
            Set<Location> locations = new HashSet<>();
            for (Piece pa : other) {
                ObservableList<Location> valid = pa.getValidMoves();
                for (int x = valid.size() - 1; x >= 0; x--) {
                    if (!pa.inCheckCheck(valid.get(x).getX(), valid.get(x).getY())) {
                        valid.remove(x);
                    }
                }
                locations.addAll(valid);
            }
            if (locations.isEmpty()) {
                if (game != null) {
                    game.gameOver(Side.WHITE, "CHECKMATE");
                }
                notation.get(notation.size() - 1).setBlack(
                        notation.get(notation.size() - 1).getBlack() + "++");
                Notation n = new Notation(notation.size() + 1);
                n.setWhite("0-1");
                notation.add(n);
            } else {
                if (game != null) {
                    game.inCheck(Side.WHITE);
                }
                notation.get(notation.size() - 1).setWhite(
                        notation.get(notation.size() - 1).getBlack() + "+");
            }
        }
        if (!whiteInCheck && !blackInCheck) {
            checkStale(s);
        }
    }

    private void checkStale(Side s) {
        if (s == Side.WHITE) {
            Set<Location> loc = new HashSet<>();
            for (int y = black.size() - 1; y >= 0; y--) {
                Piece p = black.get(y);
                ObservableList<Location> valid = p.getValidMoves();
                for (int x = valid.size() - 1; x >= 0; x--) {
                    if (!p.inCheckCheck(valid.get(x).getX(), valid.get(x).getY())) {
                        valid.remove(x);
                    }
                }
                loc.addAll(valid);
            }
            if (loc.isEmpty()) {
                if (game != null) {
                    game.gameOver(null, "Stalemate");
                }
                Notation n = new Notation(notation.size() + 1);
                n.setWhite("1/2-1/2");
                notation.add(n);
            }
        } else {
            Set<Location> loc = new HashSet<>();
            for (int y = white.size() - 1; y >= 0; y--) {
                Piece p = white.get(y);
                ObservableList<Location> valid = p.getValidMoves();
                for (int x = valid.size() - 1; x >= 0; x--) {
                    if (!p.inCheckCheck(valid.get(x).getX(), valid.get(x).getY())) {
                        valid.remove(x);
                    }
                }
                loc.addAll(valid);
            }
            if (loc.isEmpty()) {
                if (game != null) {
                    game.gameOver(null, "Stalemate");
                }
                Notation n = new Notation(notation.size() + 1);
                n.setWhite("1/2-1/2");
                notation.add(n);
            }
        }
    }

    private void undoBlack(Move m) {
        if (m instanceof Promotion) {
            Promotion p = (Promotion) m;
            removePiece(p.getPromotion().getLocation());
            if (m.getCapture() != null) {
                captures.remove(m.getCapture());
                white.add(m.getCapture());
                addPiece(m.getCapture(), m.getCapture().getLocation());
            }
            addPiece(m.getPiece(), m.getPiece().getLocation());
            black.remove(((Promotion) m).getPromotion());
            black.add(((Promotion) m).getPiece());
        } else if (m instanceof Castle) {
            Castle c = (Castle) m;
            undoCastle(c);
        } else {
            removePiece(m.getPiece().getLocation());
            if (m.getCapture() != null) {
                captures.remove(m.getCapture());
                white.add(m.getCapture());
                addPiece(m.getCapture(), m.getCapture().getLocation());
            }
            m.getPiece().getLocation().moveTo(m.getPrevx(), m.getPrevy());
            m.getPiece().minusMoves();
            addPiece(m.getPiece(), m.getPiece().getLocation());
        }
    }

    private void undoWhite(Move m) {
        if (m instanceof Promotion) {
            Promotion p = (Promotion) m;
            removePiece(p.getPromotion().getLocation());
            if (m.getCapture() != null) {
                captures.remove(m.getCapture());
                black.add(m.getCapture());
                addPiece(m.getCapture(), m.getCapture().getLocation());
            }
            addPiece(m.getPiece(), m.getPiece().getLocation());
            white.remove(((Promotion) m).getPromotion());
            white.add(((Promotion) m).getPiece());
        } else if (m instanceof Castle) {
            Castle c = (Castle) m;
            undoCastle(c);
        } else {
            removePiece(m.getPiece().getLocation());
            if (m.getCapture() != null) {
                captures.remove(m.getCapture());
                black.add(m.getCapture());
                addPiece(m.getCapture(), m.getCapture().getLocation());
            }
            m.getPiece().getLocation().moveTo(m.getPrevx(), m.getPrevy());
            m.getPiece().minusMoves();
            addPiece(m.getPiece(), m.getPiece().getLocation());
        }
    }

    private void undoCastle(Castle c) {
        removePiece(c.getPiece().getLocation());
        removePiece(c.getCapture().getLocation());
        c.getPiece().getLocation().moveTo(c.getPrevx(), c.getPrevy());
        c.getCapture().getLocation().moveTo(c.getRookx(), c.getRooky());
        c.getPiece().minusMoves();
        c.getCapture().minusMoves();
        addPiece(c.getPiece(), c.getPiece().getLocation());
        addPiece(c.getCapture(), c.getCapture().getLocation());
    }

    public void undo(Side currentTurn) {
        if (currentTurn == Side.WHITE) {
            if (!blackMoves.isEmpty()) {
                Move m = blackMoves.remove(blackMoves.size() - 1);
                undoBlack(m);
                if (!AI_PROCESSING) {
                    Notation remove = notation.remove(notation.size() - 1);
                    remove.setBlack("");
                    notation.add(remove);
                }
            }
            if (!whiteMoves.isEmpty() && !AI_PROCESSING) {
                Move m = whiteMoves.remove(whiteMoves.size() - 1);
                undoWhite(m);
                notation.remove(notation.size() - 1);
                if (!blackMoves.isEmpty()) {
                    verifyUndoCheck(blackMoves.get(blackMoves.size() - 1));
                } else {
                    whiteInCheck = false;
                    blackInCheck = false;
                }
            } else {
                if (!whiteMoves.isEmpty()) {
                    verifyUndoCheck(whiteMoves.get(whiteMoves.size() - 1));
                } else {
                    whiteInCheck = false;
                    blackInCheck = false;
                }
            }
        } else {
            if (!whiteMoves.isEmpty()) {
                Move m = whiteMoves.remove(whiteMoves.size() - 1);
                undoWhite(m);
                if (!AI_PROCESSING) {
                    notation.remove(notation.size() - 1);
                }
            }
            if (!blackMoves.isEmpty()) {
                verifyUndoCheck(blackMoves.get(blackMoves.size() - 1));
            } else {
                whiteInCheck = false;
                blackInCheck = false;
            }
        }
    }

    private void verifyUndoCheck(Move last) {
        if (!AI_PROCESSING) {
            inCheck(last.getPiece().getColor());
        }
    }

    void checkPlacement() {
        for (Piece p : white) {
            if (isEmpty(p.getLocation())) {
                addPiece(p, p.getLocation());
            }
        }
        for (Piece p : black) {
            if (isEmpty(p.getLocation())) {
                addPiece(p, p.getLocation());
            }
        }
    }

    public interface GameListener {

        public void pieceRemoved(int a, int b);

        public void pieceAdded(Piece p, int a, int b);

        public Piece promotion(Pawn p);

        public void inCheck(Side s);

        public void gameOver(Side s, String reason);

    }

    static int positionCount = 0;

    public Pair<Piece, Location> activeAI(Side s, int level) {
        AI_PROCESSING = true;
        switch (level) {
            case 1:
                ArrayList<Pair<Piece, Location>> allMoves = getAllAvailableMoves(s);
                Pair<Piece, Location> rand = allMoves.get((int) (allMoves.size() * Math.random()));
                AI_PROCESSING = false;
                return rand;
            case 2:
//                ArrayList<Pair<Piece, Location>> allMoves2 = getAllAvailableMoves(s);
//                Pair<Piece, Location> best = null;
//                int val = Integer.MIN_VALUE;
//                for (int x = 0; x < allMoves2.size(); x++) {
//                    Pair<Piece, Location> random = allMoves2.get(x);
//                    int eval = tempMove(random);
//                    if (eval > val) {
//                        val = eval;
//                        best = random;
//                    }
//                }
//                AI_PROCESSING = false;
//                return best;
            case 3:
            case 4:
                Pair<Piece, Location> root = minimaxRoot(level - 1, true, s, s);
                AI_PROCESSING = false;
                return root;
        }
        return null;
    }

    private int eval() {
        int total = 0;
        for (Piece p : white) {
            total += PieceUtils.getValue(p);
        }
        for (Piece p : black) {
            total -= PieceUtils.getValue(p);
        }
        return total;
    }

    private int tempMove(Pair<Piece, Location> random) {
        try {
            move(random.getKey(), random.getValue());
        } catch (InCheckException ex) {
            System.out.println(ex.getMessage());
        }
        int eval = Board.getBoard().eval();
        if (random.getKey().getColor() == Side.BLACK) {
            eval *= -1;
        }
        undo(random.getKey().getColor().opposite());
        return eval;
    }

    private ArrayList<Pair<Piece, Location>> getAllAvailableMoves(Side playerSide) {
        List<Piece> aiPieces = Board.getBoard().getPieces((playerSide));
        ArrayList<Pair<Piece, Location>> allOptions = new ArrayList<>();
        for (int y = aiPieces.size() - 1; y >= 0; y--) {
            Piece p = aiPieces.get(y);
            ObservableList<Location> validMoves = p.getValidMoves();
            for (int x = validMoves.size() - 1; x >= 0; x--) {
                if (!p.inCheckCheck(validMoves.get(x).getX(), validMoves.get(x).getY())) {
                    validMoves.remove(x);
                }
            }
            for (Location l : validMoves) {
                allOptions.add(new Pair<>(p, l));
            }
        }
        return allOptions;
    }

    private Pair<Piece, Location> minimaxRoot(int depth, boolean isMaximisingPlayer, Side operation, Side playerSide) {
        ArrayList<Pair<Piece, Location>> allMoves = getAllAvailableMoves(operation);
        int bestMove = -9999;
//        Pair<Piece, Location> bestMoveFound = null;
        ArrayList<Pair<Piece, Location>> bestMoves = new ArrayList<>();
        for (int i = 0; i < allMoves.size(); i++) {
            Pair<Piece, Location> newGameMove = allMoves.get(i);
            if (activateMove(newGameMove)) {
                int eval = minimax(depth - 1, !isMaximisingPlayer, (operation).opposite(), playerSide, -10000, 10000);
                Board.getBoard().undo((newGameMove.getKey().getColor()).opposite());
//                System.out.println(eval);
                if (eval > bestMove) {
                    bestMoves.clear();
                    bestMoves.add(newGameMove);
                    bestMove = eval;
                } else if (eval == bestMove) {
                    bestMoves.add(newGameMove);
                }
            }
        }
//        System.out.println("Moves : " + bestMoves.size());
        return bestMoves.get((int) (bestMoves.size() * Math.random()));
    }

    private int minimax(int depth, boolean isMaximisingPlayer, Side operation, Side playerSide, int alpha, int beta) {
        if (depth == 0) {
            int eval = Board.getBoard().eval();
            if (playerSide == Side.BLACK) {
                eval *= -1;
            }
            return eval;
        }
        ArrayList<Pair<Piece, Location>> allMoves = getAllAvailableMoves(operation);
        if (isMaximisingPlayer) {
            int bestMove = -9999;
            for (int i = 0; i < allMoves.size(); i++) {
                Pair<Piece, Location> random = allMoves.get(i);
                if (activateMove(random)) {
                    bestMove = Math.max(bestMove, minimax(depth - 1, !isMaximisingPlayer, (operation).opposite(), playerSide, alpha, beta));
                    Board.getBoard().undo((random.getKey().getColor()).opposite());
                    alpha = Math.max(alpha, bestMove);
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
            return bestMove;
        } else {
            int bestMove = 9999;
            for (int i = 0; i < allMoves.size(); i++) {
                Pair<Piece, Location> random = allMoves.get(i);
                if (activateMove(random)) {
                    bestMove = Math.min(bestMove, minimax(depth - 1, !isMaximisingPlayer, (operation).opposite(), playerSide, alpha, beta));
                    Board.getBoard().undo((random.getKey().getColor()).opposite());
                    beta = Math.min(beta, bestMove);
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
            return bestMove;
        }
    }

    private boolean activateMove(Pair<Piece, Location> random) {
        positionCount++;
        try {
            move(random.getKey(), random.getValue());
        } catch (InCheckException ex) {
            return false;
        }
        return true;
    }

    static boolean inCheckCheck(King k, int x, int y) {
        Set<Location> locat = new HashSet<>();
        TempPiece pawn = new TempPiece(x, y, k.getColor());
        int a = k.getLocation().getX();
        int b = k.getLocation().getY();
        Board.getBoard().removeObject(a, b);
        Board.getBoard().placeObject(pawn, x, y);
        List<Piece> pieces = Board.getBoard().getPieces(k.getColor().opposite());
        for (int i = pieces.size() - 1; i >= 0; i--) {
            Piece p = pieces.get(i);
            if (p.getLocation().getX() != x || p.getLocation().getY() != y) {
                locat.addAll(p.getValidMoves());
            }
        }
        Board.getBoard().removeObject(x, y);
        Board.getBoard().placeObject(k, a, b);
        Board.getBoard().checkPlacement();
        int xcomp = pawn.getLocation().getX();
        int ycomp = pawn.getLocation().getY();
        for (Location la : locat) {
            if (la.getX() == xcomp && la.getY() == ycomp) {
                return false;
            }
        }
        return true;
    }
}
