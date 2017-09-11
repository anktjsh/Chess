package app.chess.views;

import app.chess.Chess;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleEvent;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.animation.FadeInTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MenuView extends View {

    public MenuView(String name) {
        super(name);

        getStylesheets().add(MenuView.class.getResource("menu.css").toExternalForm());

        Label label = new Label("CHESS");
        label.setFont(GameView.font);
        Button comp = new Button("1 Player", MaterialDesignIcon.COMPUTER.graphic());
        comp.setOnAction((E) -> {
            OptionView.computer = true;
            Chess.getInstance().switchView("Options");
        });
        Button button = new Button("2 Player", MaterialDesignIcon.PEOPLE.graphic());
        Button settings = new Button("Settings", MaterialDesignIcon.SETTINGS.graphic());
        settings.setOnAction((e) -> {
            Chess.getInstance().switchView("Settings");
        });
        Button tut = new Button("Tutorial", MaterialDesignIcon.SCHOOL.graphic());
        tut.setOnAction((e) -> {
            Chess.getInstance().switchView("Tutorial");
        });
        button.setOnAction((e) -> {
            OptionView.computer = false;
            Chess.getInstance().switchView("Options");
        });

        VBox controls = new VBox(20);
        controls.setAlignment(Pos.CENTER);
        ImageView icon = new ImageView(Chess.ICON);
        icon.setPreserveRatio(true);
        icon.setFitHeight(75);
        controls.getChildren().addAll(icon, label, new Separator(), comp, button, tut, settings);
        for (Node n : controls.getChildren()) {
            if (n instanceof Label) {
                Label l = (Label) n;
                l.setAlignment(Pos.CENTER);
                l.setTextAlignment(TextAlignment.CENTER);
                n.setStyle("-fx-background-radius: 10 10 10 10;");
            } else if (n instanceof Button) {
                Button b = (Button) n;
                b.setAlignment(Pos.CENTER);
                b.setFont(GameView.font);
                b.setTextAlignment(TextAlignment.CENTER);
                b.setStyle("-fx-font-size:30;"
                        + "-fx-background-radius: 30;\n"
                        + "    -fx-background-insets: 0,1,2,3,0;\n"
                        + "    -fx-font-weight: bold;\n"
                        + "    -fx-padding: 10 20 10 20;");
            }

        }

        setCenter(controls);
        setShowTransitionFactory(FadeInTransition::new);
        Services.get(LifecycleService.class).ifPresent((e) -> {
            e.addListener(LifecycleEvent.RESUME, () -> {
                //check for loaded game
            });
        });
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setVisible(false);
    }

}
