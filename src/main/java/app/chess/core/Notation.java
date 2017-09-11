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
public class Notation {

    private String white, black;
    private final int step;

    public Notation(int a) {
        white = "";
        black = "";
        step = a;
    }

    public String getWhite() {
        return white;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    @Override
    public String toString() {
        return step + ". " + white + "    " + black;
    }

}
