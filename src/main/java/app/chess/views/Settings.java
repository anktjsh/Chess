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
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.Node;

/**
 *
 * @author aniket
 */
public class Settings extends View {

    private Node settings, credits;

    public Settings(String string) {
        super(string);
        setShowTransitionFactory(FadeInTransition::new);
        setBottom(createBottomNavigation());
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Settings");
        appBar.setNavIcon(MaterialDesignIcon.HOME.button((e) -> {
            Chess.getInstance().switchView(HOME_VIEW);
        }));
    }

    private BottomNavigation createBottomNavigation() {
        BottomNavigation bottomNavigation = new BottomNavigation();
        BottomNavigationButton easy = new BottomNavigationButton("Settings", MaterialDesignIcon.SETTINGS.graphic());
        BottomNavigationButton har = new BottomNavigationButton("Credits", MaterialDesignIcon.CREDIT_CARD.graphic());
        easy.setOnAction((e) -> {
            setCenter(getSettings());
        });
        har.setOnAction((E) -> {
            setCenter(getCredits());
        });
        bottomNavigation.getActionItems().addAll(easy, har);
        easy.setSelected(true);
        return bottomNavigation;
    }

    private Node getSettings() {
        if (settings == null) {
            settings = new Preferences();
        }
        return settings;
    }

    private Node getCredits() {
        if (credits == null) {
            credits = new Credits();
        }
        return credits;
    }

}
