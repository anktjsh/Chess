/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.core;

import java.util.Random;
import java.util.TreeSet;

/**
 *
 * @author aniket
 */
public class PieceUtils {

    public static double getValue(Piece p) {
        if (p instanceof Queen) {
            return 90 + evalQueen[p.getLocation().getX()][p.getLocation().getY()];
        } else if (p instanceof King) {
            return 900 + (p.getColor() == Side.WHITE
                    ? kingEvalWhite[p.getLocation().getX()][p.getLocation().getY()]
                    : kingEvalBlack[p.getLocation().getX()][p.getLocation().getY()]);
        } else if (p instanceof Rook) {
            return 50 + (p.getColor() == Side.WHITE
                    ? rookEvalWhite[p.getLocation().getX()][p.getLocation().getY()]
                    : rookEvalBlack[p.getLocation().getX()][p.getLocation().getY()]);
        } else if (p instanceof Bishop) {
            return 30 + (p.getColor() == Side.WHITE
                    ? bishopEvalWhite[p.getLocation().getX()][p.getLocation().getY()]
                    : bishopEvalBlack[p.getLocation().getX()][p.getLocation().getY()]);
        } else if (p instanceof Knight) {
            return 30 + knightEval[p.getLocation().getX()][p.getLocation().getY()];
        }
        return 10 + (p.getColor() == Side.WHITE
                ? pawnEvalWhite[p.getLocation().getX()][p.getLocation().getY()]
                : pawnEvalBlack[p.getLocation().getX()][p.getLocation().getY()]);
    }

    final static double[][] pawnEvalWhite = new double[][]{
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0},
        {1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 1.0},
        {0.5, 0.5, 1.0, 2.5, 2.5, 1.0, 0.5, 0.5},
        {0.0, 0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 0.0},
        {0.5, -0.5, -1.0, 0.0, 0.0, -1.0, -0.5, 0.5},
        {0.5, 1.0, 1.0, -2.0, -2.0, 1.0, 1.0, 0.5},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
    };

    final static double[][] pawnEvalBlack = reverse(pawnEvalWhite);

    final static double[][] knightEval = new double[][]{
        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
        {-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0},
        {-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0},
        {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
        {-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0},
        {-3.0, 0.5, 1.0, 1.5, 1.5, 1.0, 0.5, -3.0},
        {-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0},
        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
    };

    final static double[][] bishopEvalWhite = new double[][]{
        {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
        {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0},
        {-1.0, 0.0, 0.5, 1.0, 1.0, 0.5, 0.0, -1.0},
        {-1.0, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, -1.0},
        {-1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, -1.0},
        {-1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, -1.0},
        {-1.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.5, -1.0},
        {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
    };

    final static double[][] bishopEvalBlack = reverse(bishopEvalWhite);

    final static double[][] rookEvalWhite = new double[][]{
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5},
        {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
        {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
        {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
        {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
        {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
        {0.0, 0.0, 0.0, 0.5, 0.5, 0.0, 0.0, 0.0}
    };

    final static double[][] rookEvalBlack = reverse(rookEvalWhite);

    final static double[][] evalQueen = new double[][]{
        {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
        {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0},
        {-1.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0},
        {-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5},
        {0.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5},
        {-1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0},
        {-1.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, -1.0},
        {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
    };

    final static double[][] kingEvalWhite = new double[][]{
        {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        {-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
        {-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
        {2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0},
        {2.0, 3.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0}
    };

    final static double[][] kingEvalBlack = reverse(kingEvalWhite);

    final static double[][] reverse(double[][] a) {
        double[][] ar = new double[a.length][];
        int len, index;
        for (int i = 0; i < a.length; i++) {
            len = a[i].length;
            ar[i] = new double[len];
            index = len - 1;
            for (int j = 0; j < len; j++) {
                ar[i][j] = a[i][index];
                index--;
            }
        }
        return ar;
    }

    private final static TreeSet<Long> hashes = new TreeSet<>();
    private final static Random r = new Random();

    public static Long getNewIndentifier() {
        long l = r.nextLong();
        while (hashes.contains(l)) {
            l = r.nextLong();
        }
        hashes.add(l);
        return l;
    }
}
