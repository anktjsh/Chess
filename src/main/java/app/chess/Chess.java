package app.chess;

import app.chess.views.MenuView;
import app.chess.views.GameView;
import app.chess.views.OptionView;
import app.chess.views.Preferences;
import app.chess.views.Settings;
import app.chess.views.Tutorial;
import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.license.License;
import com.gluonhq.charm.glisten.mvc.SplashView;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Chess extends MobileApplication {

    public static final String MENU_VIEW = HOME_VIEW;
    public static final String GAME_VIEW = "Game View";
    public static final Image ICON = new Image(Chess.class.getResourceAsStream("/game.png"));
    public static Scene chess;

    @Override
    public void init() {
        addViewFactory(SPLASH_VIEW, () -> {
            ImageView im = new ImageView(ICON);
            im.setPreserveRatio(true);
            im.setFitWidth(300);
            return new SplashView(HOME_VIEW, im, Duration.seconds(2));
        });
        Preferences.loadAll();
        addViewFactory(MENU_VIEW, () -> new MenuView(MENU_VIEW));
        addViewFactory(GAME_VIEW, () -> new GameView(GAME_VIEW));
        addViewFactory("Settings", () -> new Settings("Settings"));
        addViewFactory("Options", () -> new OptionView("Options"));
        addViewFactory("Tutorial", () -> new Tutorial("Tutorial"));
    }

    @Override
    public void postInit(Scene scene) {
        chess = scene;
        Swatch.INDIGO.assignTo(scene);
        Theme.DARK.assignTo(scene);
        scene.getStylesheets().add(Chess.class.getResource("style.css").toExternalForm());
        if (Platform.isDesktop()) {
            ((Stage) scene.getWindow()).getIcons().add(new Image(Chess.class.getResourceAsStream("/icon.png")));
            ((Stage) scene.getWindow()).setResizable(false);
        }
    }
}
