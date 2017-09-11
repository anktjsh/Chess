/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.views;

import app.chess.Chess;
import static app.chess.Chess.GAME_VIEW;
import app.chess.core.Piece;
import app.chess.core.Side;
import com.gluonhq.charm.glisten.animation.FadeInTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author aniket
 */
public class OptionView extends View {

    private final TextField one, two;
    static boolean computer;
    private final VBox box;
    private final ImageView im1, im2;
    private final Slider difficulty;

    public OptionView(String string) {
        super(string);
        box = new VBox(15);
        HBox hb1, hb2, hb3;
        im2 = new ImageView(Piece.getBlackKing());
        im1 = new ImageView(Piece.getWhiteKing());
        box.getChildren().addAll(
                hb1 = new HBox(15,
                        new Label("Player" + (computer ? "" : " 1")),
                        one = new TextField("Player 1"), im1),
                hb3 = new HBox(15, MaterialDesignIcon.SWAP_VERT.button((e) -> {
                    Image i = im1.getImage();
                    im1.setImage(im2.getImage());
                    im2.setImage(i);
                })),
                hb2 = new HBox(15,
                        new Label((computer ? "Computer" : "Player 2")),
                        two = new TextField("Player 2"), im2));
        setCenter(box);
        box.setPadding(new Insets(80, 10, 10, 10));
        box.setAlignment(Pos.TOP_CENTER);
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        one.setStyle("-fx-text-fill:white;");
        two.setStyle("-fx-text-fill:white;");
        im1.setPreserveRatio(true);
        im2.setPreserveRatio(true);
        im1.setFitHeight(50);
        im2.setFitWidth(50);
        difficulty = new Slider(1, 4, 3);
        difficulty.setSnapToTicks(true);
        difficulty.setShowTickLabels(true);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(0);
        difficulty.setBlockIncrement(1);
        if (computer) {
            box.getChildren().add(difficulty);
        }
        setShowTransitionFactory(FadeInTransition::new);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Options");
        ((Label) ((HBox) box.getChildren().get(0)).getChildren().get(0))
                .setText("Player" + (computer ? "" : " 1"));
        ((Label) ((HBox) box.getChildren().get(2)).getChildren().get(0))
                .setText((computer ? "Computer" : "Player 2"));
        if (computer) {
            if (!box.getChildren().contains(difficulty)) {
                box.getChildren().add(difficulty);
            }
        } else {
            box.getChildren().remove(difficulty);
        }
        appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button((e) -> Chess.getInstance().switchToPreviousView()));
        appBar.getActionItems().add(MaterialDesignIcon.PLAY_ARROW.button((e) -> {
            if (computer) {
                GameView.computer = true;
                GameView.playerSide = im1.getImage() == Piece.getWhiteKing() ? Side.WHITE : Side.BLACK;
                GameView.AI_LEVEL = (int) difficulty.getValue();
                GameView.whitePlayer = im1.getImage() == Piece.getWhiteKing() ? one.getText() : two.getText();
                GameView.blackPlayer = im2.getImage() == Piece.getBlackKing() ? two.getText() : one.getText();
                Chess.getInstance().switchView(GAME_VIEW);
            } else {
                GameView.computer = false;
                GameView.whitePlayer = im1.getImage() == Piece.getWhiteKing() ? one.getText() : two.getText();
                GameView.blackPlayer = im2.getImage() == Piece.getBlackKing() ? two.getText() : one.getText();
                Chess.getInstance().switchView(GAME_VIEW);
            }
        }));
    }

}
