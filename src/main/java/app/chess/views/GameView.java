package app.chess.views;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import app.chess.Chess;
import app.chess.core.Bishop;
import app.chess.core.Board;
import app.chess.core.InCheckException;
import app.chess.core.Knight;
import app.chess.core.Location;
import app.chess.core.Notation;
import app.chess.core.Pawn;
import app.chess.core.Piece;
import app.chess.core.Queen;
import app.chess.core.Rook;
import app.chess.core.Side;
import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleEvent;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.down.plugins.StorageService;
import com.gluonhq.charm.glisten.animation.FadeInTransition;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.CardCell;
import com.gluonhq.charm.glisten.control.CardPane;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

public class GameView extends View implements EventHandler<MouseEvent> {

    static boolean computer;
    static Side playerSide;
    static int AI_LEVEL;
    static String whitePlayer, blackPlayer;
    private boolean gameOver = false;

    public static Font font = Font.loadFont(MenuView.class.getResourceAsStream("/Roboto-Black.ttf"), 40);
    private ObjectProperty<Side> turn = new SimpleObjectProperty<>();
    private GamePiece selected;
    private final Button undo;
    private final GamePiece[][] pieces = new GamePiece[8][8];
    private final GlistenChoiceDialog gcd = new GlistenChoiceDialog();
    private final CardPane<Notation> not = new CardPane<>();
    private final BorderPane popupPane = new BorderPane(not);
    private final GridPane grid;
    private final SidePopupView notation = new SidePopupView(popupPane);
    private final Timeline white, black;
    private final DecimalFormat form = new DecimalFormat("00"),
            minute = new DecimalFormat("##");
    private int whiteSeconds, blackSeconds = 0;
    private final Label whiteTime, blackTime;
    private static final GlistenChoiceDialog PROMOTION;

    static {
        PROMOTION = new GlistenChoiceDialog();
        PROMOTION.setTitle("Pawn Promotion!");
        PROMOTION.setItems(FXCollections.observableArrayList("Knight", "Bishop",
                "Rook", "Queen"));
    }

    {
        gcd.setItems(FXCollections.observableArrayList("New Game", "Main Menu"));
        popupPane.setTop(label("Moves", "-fx-text-fill:black;"));
        popupPane.setStyle("-fx-background-color:white;");
        BorderPane.setAlignment(popupPane.getTop(), Pos.CENTER);
        BorderPane.setMargin(popupPane.getTop(), new Insets(15, 0, 0, 0));
    }

    private Label label(String s, String style) {
        Label label = new Label(s);
        label.setStyle("-fx-padding:10;" + style);
        return label;
    }

    public GameView(String name) {
        super(name);
        BorderPane top = new BorderPane();
        setTop(top);
        BorderPane bottom;
        not.setCellFactory((CardPane<Notation> param) -> {
            return new NotationCell();
        });
        BorderPane border = new BorderPane();
        Board.getBoard().getMoves().addListener((ListChangeListener.Change<? extends Notation> c1) -> {
            if (c1.next()) {
                if (c1.wasRemoved()) {
                    for (Notation s : c1.getRemoved()) {
                        not.getItems().remove(s);
                    }
                } else if (c1.wasAdded()) {
                    for (Notation n : c1.getAddedSubList()) {
                        not.getItems().add(n);
                    }
                }
            }
        });
        bottom = new BorderPane(MaterialDesignIcon.TEXT_FORMAT.button((e) -> {
            notation.show();
        }));
        setBottom(bottom);
        Label la;
        top.setCenter(la = new Label("CHESS"));
        la.setFont(font);
        top.setPadding(new Insets(30, 15, 60, 15));
        top.setLeft(MaterialDesignIcon.MENU.button((e) -> {
            gcd.showAndWait().ifPresent((ef) -> {
                if (ef.equals("New Game")) {
                    turn.set(null);
                    updateAppBar(Chess.getInstance().getAppBar());
                } else if (ef.equals("Main Menu")) {
                    Chess.getInstance().switchView(HOME_VIEW);
                }
            });
        }));
        top.setRight(undo = MaterialDesignIcon.UNDO.button((e) -> {
            for (GamePiece gpa : selectPieces) {
                gpa.unselect();
            }
            selected = null;
            Board.getBoard().undo(turn.get());
            if (turn.get() == Side.BLACK) {
                flipturn();
            }
        }));

        /*
        player notification
         */
        BorderPane players = new BorderPane();
        whiteTime = new Label("0:00");
        blackTime = new Label("0:00");
        white = new Timeline();
        black = new Timeline();

        bottom.setTop(players);
        BorderPane.setMargin(bottom.getTop(), new Insets(0, 0, 15, 0));
        ImageView whiteImage, blackImage;
        whiteTime.setGraphic(whiteImage = new ImageView(Piece.getWhiteKing()));
        blackTime.setGraphic(
                blackImage = new ImageView(Piece.getBlackKing()));
        whiteImage.setPreserveRatio(true);
        blackImage.setPreserveRatio(true);
        whiteImage.setFitHeight(50);
        blackImage.setFitHeight(50);
        players.setLeft(whiteTime);
        players.setRight(blackTime);
        blackTime.setContentDisplay(ContentDisplay.RIGHT);
        Glow gl = new Glow(1);
        turn.addListener((ob, older, newer) -> {
            if (newer != null) {
                if (newer == Side.WHITE) {
                    whiteTime.getGraphic().setEffect(gl);
                    blackTime.getGraphic().setEffect(null);
                    white.play();
                    black.pause();
                } else {
                    whiteTime.getGraphic().setEffect(null);
                    blackTime.getGraphic().setEffect(gl);
                    white.pause();
                    black.play();
                }
                if (computer) {
                    /*
                    testing purposes
                     */
                    if (newer != playerSide) {
                        activateAI();
                    }
                }
            }
        });
        showingProperty().addListener((ob, older, newer) -> {
            if (!newer) {
                white.stop();
                black.stop();
                turn.set(null);
            }
        });

        BorderPane.setAlignment(border, Pos.TOP_CENTER);
        border.setStyle("-fx-border-color:#3F51B5;-fx-border-width:5;");
        setCenter(border);
        border.setMaxHeight(Platform.isDesktop() ? 335 - 10 : Chess.getInstance().getScreenWidth() - 10);
        grid = new GridPane();
        border.setCenter(grid);
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(12.5);
        grid.getRowConstraints().addAll(rc, rc, rc, rc, rc, rc, rc, rc);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(12.5);
        grid.getColumnConstraints().addAll(cc, cc, cc, cc, cc, cc, cc, cc);
        for (int x = 0; x < 8; x++) {
            GamePiece[] al = new GamePiece[8];
            for (int y = 0; y < 8; y++) {
                GamePiece l = new GamePiece(x, y);
                l.setMaxWidth(Integer.MAX_VALUE);
                l.setMaxHeight(Integer.MAX_VALUE);
                l.setStyle("-fx-border-color:black;-fx-border-width:0.25;-fx-background-color:" + ((x + y) % 2 == 0 ? "white" : "#3F51B5"));
                al[y] = l;
            }
            grid.addRow(x, al);
            pieces[x] = al;
        }
        setShowTransitionFactory(FadeInTransition::new);
        Services.get(LifecycleService.class).ifPresent((e) -> {
            e.addListener(LifecycleEvent.PAUSE, () -> {
                white.pause();
                black.pause();
                //save game
            });
            e.addListener(LifecycleEvent.RESUME, () -> {
                if (turn.get() == Side.WHITE) {
                    white.play();
                } else {
                    black.play();
                }
            });
        });
    }

    private Piece showPromotion(Piece p) {
        Optional<String> show = PROMOTION.showAndWait();
        if (show.isPresent()) {
            switch (show.get()) {
                case "Rook":
                    Rook r = new Rook(p.getLocation().getX(), p.getLocation().getY(), p.getColor());
                    return r;
                case "Bishop":
                    Bishop b = new Bishop(p.getLocation().getX(), p.getLocation().getY(), p.getColor());
                    return b;
                case "Knight":
                    Knight n = new Knight(p.getLocation().getX(), p.getLocation().getY(), p.getColor());
                    return n;
                case "Queen":
                    Queen q = new Queen(p.getLocation().getX(), p.getLocation().getY(), p.getColor());
                    return q;
            }
        } else {
            return showPromotion(p);
        }
        return null;
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setVisible(false);
        for (GamePiece gpa : selectPieces) {
            gpa.unselect();
        }
        white.stop();
        black.stop();
        grid.setRotate(0);
        for (GamePiece gp[] : pieces) {
            for (GamePiece g : gp) {
                g.setRotate(0);
            }
        }
        selected = null;
        Board.getBoard().reset();
        Board.getBoard().setGameListener(new Board.GameListener() {
            @Override
            public void pieceRemoved(int a, int b) {
                pieces[a][b].setImage(null);
                pieces[a][b].setPiece(null);
            }

            @Override
            public void pieceAdded(Piece p, int a, int b) {
                pieces[a][b].setImage((Piece.getImage(p)));
                pieces[a][b].setPiece(p);
            }

            @Override
            public Piece promotion(Pawn p) {
                if (computer) {
                    if (turn.get() != playerSide) {
                        return new Queen(p.getLocation().getX(), p.getLocation().getY(), p.getColor());
                    }
                }
                return showPromotion(p);
            }

            @Override
            public void inCheck(Side s) {
                Toast t = new Toast(s.name() + " is in Check!");
                t.setDuration(Toast.LENGTH_SHORT);
                t.show();
            }

            @Override
            public void gameOver(Side s, String reason) {
                gameOver = true;
                Toast t;
                if (reason.equals("CHECKMATE")) {
                    t = new Toast("CHECKMATE! " + (s.opposite() == Side.WHITE ? whitePlayer : blackPlayer) + " Wins");
                } else {
                    t = new Toast("Draw! " + reason + "!");
                }
                undo.setDisable(true);
                for (GamePiece[] gp : pieces) {
                    for (GamePiece g : gp) {
                        g.setOnMouseClicked(null);
                    }
                }
                t.setDuration(Toast.LENGTH_SHORT);
                t.show();
                white.stop();
                black.stop();
                Services.get(StorageService.class).ifPresent((storage) -> {
                    storage.getPrivateStorage().ifPresent((file) -> {
                        File dir = new File(file, "chess");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File fi = new File(dir, "saved_game.txt");
                        if (fi.exists()) {
                            fi.delete();
                        }
                        File history = new File(dir, "history");
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        File save = new File(history, "Game_" + timeStamp + ".txt");
                        //save game in history
                    });
                });
            }
        });
        for (GamePiece[] gp : pieces) {
            for (GamePiece g : gp) {
                g.setPiece(null);
                g.setImage(null);
                g.setOnMouseClicked(GameView.this);
            }
        }
        Board.getBoard().init();
        undo.setDisable(false);
        gameOver = false;
        whiteSeconds = 0;
        blackSeconds = 0;
        white.getKeyFrames().clear();
        black.getKeyFrames().clear();
        if (Preferences.hasTimeLimit()) {
            whiteTime.setText(whitePlayer + " " + Preferences.getTimeLimit() + ":00");
            blackTime.setText(blackPlayer + " " + Preferences.getTimeLimit() + ":00");
            white.getKeyFrames().add(new KeyFrame(Duration.seconds(1), (event) -> {
                whiteSeconds++;
                int n = Preferences.getTimeLimit() * 60 - whiteSeconds;
                double min = n / 60;
                double sec = n % 60;
                whiteTime.setText(whitePlayer + " " + minute.format(min) + ":" + form.format(sec));
            }));
            white.setCycleCount(Preferences.getTimeLimit() * 60);
            black.getKeyFrames().add(new KeyFrame(Duration.seconds(1), (event) -> {
                blackSeconds++;
                int n = Preferences.getTimeLimit() * 60 - blackSeconds;
                double min = n / 60;
                double sec = n % 60;
                blackTime.setText(blackPlayer + " " + minute.format(min) + ":" + form.format(sec));
            }));
            black.setCycleCount(Preferences.getTimeLimit() * 60);
            white.setOnFinished((ev) -> {
                Toast t = new Toast(whitePlayer + " ran out of time! " + blackPlayer + " Wins!");
                t.setDuration(Toast.LENGTH_SHORT);
                t.show();
                gameOver = true;
                //determine draw or mate
                undo.setDisable(true);
                for (GamePiece[] gp : pieces) {
                    for (GamePiece g : gp) {
                        g.setOnMouseClicked(null);
                    }
                }
                white.stop();
                black.stop();
            });
            black.setOnFinished((ev) -> {
                Toast t = new Toast(blackPlayer + " ran out of time! " + whitePlayer + " Wins!");
                t.setDuration(Toast.LENGTH_SHORT);
                t.show();
                gameOver = true;
                //determine draw or mate
                undo.setDisable(true);
                for (GamePiece[] gp : pieces) {
                    for (GamePiece g : gp) {
                        g.setOnMouseClicked(null);
                    }
                }
                white.stop();
                black.stop();
            });
        } else {
            whiteTime.setText(whitePlayer + " 0:00");
            blackTime.setText(blackPlayer + " 0:00");
            white.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1), (event) -> {
                        whiteSeconds++;
                        double min = whiteSeconds / 60;
                        double sec = whiteSeconds % 60;
                        whiteTime.setText(whitePlayer + " " + minute.format(min) + ":" + form.format(sec));
                    }));
            white.setCycleCount(Timeline.INDEFINITE);
            black.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1), (event) -> {
                        blackSeconds++;
                        double min = blackSeconds / 60;
                        double sec = blackSeconds % 60;
                        blackTime.setText(blackPlayer + " " + minute.format(min) + ":" + form.format(sec));
                    }));
            black.setCycleCount(Timeline.INDEFINITE);
        }
        turn.set(Side.WHITE);
    }

    private GamePiece getPiece(Location l) {
        return pieces[l.getX()][l.getY()];
    }

    ArrayList<GamePiece> selectPieces = new ArrayList<>();

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource() instanceof GamePiece) {
            GamePiece gp = (GamePiece) event.getSource();
            if (selected == null) {
                if (gp.getCurrentPiece() != null) {
                    if (gp.getCurrentPiece().getColor() == turn.get()) {
                        if ((computer && turn.get() == playerSide) || !computer) {
                            selected = gp;
                            gp.select();
                            selectPieces.clear();
                            selectPieces.add(gp);
                            if (Preferences.showAllMoves()) {
                                ObservableList<Location> validMoves = gp.getCurrentPiece().getValidMoves();
                                for (int x = validMoves.size() - 1; x >= 0; x--) {
                                    if (!gp.getCurrentPiece().inCheckCheck(validMoves.get(x).getX(), validMoves.get(x).getY())) {
                                        validMoves.remove(x);
                                    }
                                }
                                for (Location l : validMoves) {
                                    GamePiece piece = getPiece(l);
                                    piece.select();
                                    selectPieces.add(piece);
                                }
                            }
                        }
                    }
                }
            } else {
                if (selected.getCurrentPiece().getLocation().getX() == gp.getX() && selected.getCurrentPiece().getLocation().getY() == gp.getY()) {
                    for (GamePiece gpa : selectPieces) {
                        gpa.unselect();
                    }
                    selected = null;
                } else {
                    if (selected.getCurrentPiece().validMove(gp.getX(), gp.getY())) {
                        try {
                            Board.getBoard().move(selected.getCurrentPiece(), gp.getX(), gp.getY());
                        } catch (InCheckException ex) {
                            Toast t = new Toast("Illegal Move!");
                            t.setDuration(Toast.LENGTH_SHORT);
                            t.show();
                            return;
                        }
                        for (GamePiece gpa : selectPieces) {
                            gpa.unselect();
                        }
                        if (!gameOver) {
                            flipturn();
                        }
                        selected = null;
                    } else {
                        if (gp.getCurrentPiece() != null && gp.getCurrentPiece().getColor() == turn.get()) {
                            for (GamePiece gpa : selectPieces) {
                                gpa.unselect();
                            }
                            selected = gp;
                            selectPieces.clear();
                            gp.select();
                            selectPieces.add(gp);
                            if (Preferences.showAllMoves()) {
                                ObservableList<Location> validMoves = gp.getCurrentPiece().getValidMoves();
                                for (int x = validMoves.size() - 1; x >= 0; x--) {
                                    if (!gp.getCurrentPiece().inCheckCheck(validMoves.get(x).getX(), validMoves.get(x).getY())) {
                                        validMoves.remove(x);
                                    }
                                }
                                for (Location l : validMoves) {
                                    GamePiece piece = getPiece(l);
                                    piece.select();
                                    selectPieces.add(piece);
                                }
                            }
                        } else {
                            if (Board.getBoard().isInCheck(turn.get())) {
                                Toast t = new Toast("You are in check!");
                                t.setDuration(Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    }
                }
            }
        }
    }

    private void flipturn() {
        if (!gameOver) {
            turn.set(turn.get().opposite());
            if (Preferences.flipBoard() && !computer) {
                grid.setRotate(180);
                for (GamePiece gp[] : pieces) {
                    for (GamePiece g : gp) {
                        g.setRotate(180);
                    }
                }
            }
        }
    }

    private void activateAI() {
        move();
    }

    private void move() {
        (new Thread(() -> {
            long n = System.currentTimeMillis();
            Pair<Piece, Location> move = Board.getBoard().activeAI(turn.get(), AI_LEVEL);
            if (move == null) {
                if (gameOver) {
                    return;
                }
                move = Board.getBoard().activeAI(playerSide.opposite(), 1);
            }
            if (move != null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                if (move.getKey().getColor() != turn.get()) {
                    return;
                }
                final Pair<Piece, Location> movea = move;
                javafx.application.Platform.runLater(() -> {
                    try {
                        Board.getBoard().move(movea.getKey(), movea.getValue());
                    } catch (InCheckException ex) {
                        System.out.println(ex.getMessage());
                    }
                    flipturn();
                });
            } else {
                System.out.println("wtf");
            }

        })).start();
    }

    private class NotationCell extends CardCell<Notation> {

        private final Label label;

        public NotationCell() {
            label = label("", "");
        }

        @Override
        public void updateItem(Notation item, boolean empty) {
            if (item != null) {
                label.setText(item.toString());
                setGraphic(label);
            } else {
                setGraphic(null);
            }
        }
    }

}
