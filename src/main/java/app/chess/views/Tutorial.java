/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.views;

import app.chess.Chess;
import com.gluonhq.charm.glisten.animation.FadeInTransition;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author aniket
 */
public class Tutorial extends View {

    private final ObservableList<Slide> slides;
    private final BorderPane bottom;
    private final Label state, title, caption;
    private final ImageView imageView;
    private final IntegerProperty currentIndex = new SimpleIntegerProperty(-1);

    public Tutorial(String string) {
        super(string);
        setShowTransitionFactory(FadeInTransition::new);
        slides = FXCollections.observableArrayList();
        setTop(title = new Label());
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(GameView.font);
        BorderPane.setAlignment(title, Pos.CENTER);
        setCenter(imageView = new ImageView());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(200);
        BorderPane.setMargin(getCenter(), new Insets(5));
        setBottom(bottom = new BorderPane());
        BorderPane.setMargin(bottom, new Insets(5));
        bottom.setTop(caption = new Label());
        caption.setWrapText(true);
        BorderPane.setAlignment(caption, Pos.CENTER);
        BorderPane.setMargin(caption, new Insets(5));
        caption.setTextAlignment(TextAlignment.CENTER);
        bottom.setCenter(state = new Label(""));
        bottom.setLeft(MaterialDesignIcon.ARROW_BACK.button((e) -> {
            currentIndex.set(currentIndex.get() - 1);
        }));
        bottom.setRight(MaterialDesignIcon.ARROW_FORWARD.button((e) -> {
            currentIndex.set(currentIndex.get() + 1);
        }));
        currentIndex.addListener((ob, older, newer) -> {
            state.setText((newer.intValue() + 1) + "/" + slides.size());
            imageView.setImage(slides.get(newer.intValue()).image);
            title.setText(slides.get(newer.intValue()).title);
            caption.setText(slides.get(newer.intValue()).caption);
            if (newer.intValue() == 0) {
                bottom.getLeft().setDisable(true);
                bottom.getRight().setDisable(false);
            } else if (newer.intValue() == slides.size() - 1) {
                bottom.getLeft().setDisable(false);
                bottom.getRight().setDisable(true);
            } else {
                bottom.getLeft().setDisable(false);
                bottom.getRight().setDisable(false);
            }
        });
        slides.addAll(new Slide("Objective", "The objective of this game is to place the opponent's King in a state of checkmate, "
                + "where it cannot move, it's current location on the board is being threatened, and no other piece can "
                + "either block the opponent's pieces or capture them.",
                image("save8.png")),
                new Slide("Gameplay", "The gameplay in chess consists of a turn-based system with white always starting the game. "
                        + "Players alternate turns. Every time it is a player's turn, they are required to move, even if it is disadvantageous to do so. "
                        + "Play continues until a player is checkmated, a player resigns, or a draw is declared.",
                        image("save9.png")),
                new Slide("Board", "The chess board consists of 64 squares of alternating color with each player controlling 16 "
                        + "pieces on each end of the board. The board is placed so that a lighter colored square is in each player's near-right corner. "
                        + "Each player has one king and queen, two rooks, bishops, and knights, and 8 pawns all arranged as shown above.",
                        image("save7.png")),
                new Slide("Pawn", "A pawn moves straight forward one square. If both squared in front of it are vacant and the pawn has not moved yet, "
                        + "it can move two squared forward. Pawns cannot move backwards. Pawns can capture a piece only if it is directly diagonal in front of the pawn. "
                        + "Pawns are also involved in two special moves, the en passant, and promotion, which will both be covered in later slides.",
                        image("save6.png")),
                new Slide("Knight", "The knight moves in an L pattern, either moving two spaces horizontally and one vertically or two spaces "
                        + "vertically and one horizontally. The knight is not blocked by other pieces, it simply jumps to its new location.",
                        image("save3.png")),
                new Slide("Bishop", "The bishop moves any number of vacant squared in any diagonal direction.",
                        image("save1.png")),
                new Slide("Rook", "A rook is capable of moving any number of vacant squares in a horizontal or vertical direction. "
                        + "It also is moved when castling, a topic covered in a few slides.",
                        image("save2.png")),
                new Slide("Queen", "The queen can move any number of squared in a horizontal, vertical, or diagonal direction.",
                        image("save4.png")),
                new Slide("King", "The king moves exactly one square vertically, horizontally, or diagonally. A special move the king can do "
                        + "is called castling that is allowed only once per game, per player. Castling will be covered in a later slide.",
                        image("save5.png")),
                new Slide("Enpassant", "When a pawn advances two squares from its original square and ends the turn adjacent to a pawn of the opponent's on the same rank, it may be captured by that pawn of the opponent's, as if it had moved only one square forward. "
                        + "This capture is only legal on the opponent's next move immediately following the first pawn's advance.",
                        image("save10.png")),
                new Slide("Castling", "Castling consists of moving the king two squares towards a rook, then placing the rook on the other side of the king, adjacent to it.Castling is only permissible if all of the following conditions hold: "
                        + "The king and rook involved in castling must not have previously moved;"
                        + "There must be no pieces between the king and the rook;"
                        + "The king may not currently be in check, nor may the king pass through or end up in a square that is under attack by an enemy piece (though the rook is permitted to be under attack and to pass over an attacked square);"
                        + "The king and the rook must be on the same row.",
                        image("save11.png")),
                new Slide("Promotion", "If a player advances a pawn to the other side of the board, the pawn is then promoted or converted "
                        + "into a queen, rook, bishop, or knight of the same color at the choice of the player. It is theoretically possible for a player to have "
                        + "up to nine queens. ",
                        image("save15.png")),
                new Slide("Check", "A king is in check when it is under attack by at least one enemy piece. "
                        + "A piece unable to move because it would place its own king in check (it is pinned against its own king) may still deliver check to the opposing player."
                        + " It is illegal to make a move that places or leaves one's king in check. The ways to get out of check include: "
                        + "moving the king to a square that is not threatened, capturing the threatening piece, possibly with the king, "
                        + "or blocking the check by placing a piece between the king and the threatening piece.",
                        image("save16.png")),
                new Slide("Draw", "The game is automatically a draw if the player to move is not in check but has no legal move. This situation is called a stalemate. "
                        + "The game is immediately drawn when there is no possibility of checkmate for either side with any series of legal moves. "
                        + "Both players can agree to a draw after one of the players makes such an offer. "
                        + "The player having the move may claim a draw by declaring either the "
                        + "Fifty-move rule: There has been no capture or pawn move in the last fifty moves by each player, "
                        + "or Threefold repetition: The same board position has occurred three times with the same player to move.",
                        image("save14.png")),
                new Slide("Checkmate", "If a player's king is placed in check and there is no legal move that player can make to escape check, "
                        + "then the king is said to be checkmated, the game ends, and that player loses. Unlike other pieces, the king is never actually captured or "
                        + "removed from the board because checkmate ends the game.",
                        image("save8.png")));
        currentIndex.set(0);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Tutorial");
        appBar.setNavIcon(MaterialDesignIcon.HOME.button((e) -> {
            Chess.getInstance().switchView(HOME_VIEW);
        }));
    }

    private Image image(String s) {
        return new Image(getClass().getResourceAsStream(s));
    }

    private class Slide {

        private final String title, caption;
        private final Image image;

        public Slide(String a, String b, Image i) {
            title = a;
            caption = b;
            image = i;
        }
    }

}
