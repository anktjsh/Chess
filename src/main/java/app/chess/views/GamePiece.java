/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.views;

import app.chess.Chess;
import app.chess.core.Piece;
import com.gluonhq.charm.down.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author aniket
 */
public class GamePiece extends Label {

    private final ImageView image;
    private Piece currentPiece;
    private final int x, y;

    public GamePiece(int a, int b) {
        setGraphic(image = new ImageView());
        image.setPreserveRatio(true);
        image.setFitWidth(Platform.isDesktop() ? 37.5 : Chess.getInstance().getScreenWidth() / 9);
        x = a;
        y = b;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setPiece(Piece p) {
        currentPiece = p;
    }

    public void setImage(Image im) {
        image.setImage(im);
    }

    private String previousStyle;

    public void select() {
        previousStyle = getStyle();
        setStyle("-fx-border-color:black;-fx-border-width:0.25;-fx-background-color:red");
    }

    public boolean isSelected() {
        return (getStyle().equals("-fx-border-color:black;-fx-border-width:0.25;-fx-background-color:red"));
    }

    public void unselect() {
        setStyle(previousStyle);
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }
}
