/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.views;

import app.chess.Chess;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author aniket
 */
public class Credits extends BorderPane {

    private static final Image GLUON = new Image(Credits.class.getResourceAsStream("/gluon.png"));

    public Credits() {
        VBox imageCont = new VBox(5);
        imageCont.setAlignment(Pos.CENTER);
        setCenter(imageCont);
        Label one = new Label();
        one.setTextAlignment(TextAlignment.CENTER);
        ImageView image = new ImageView(GLUON);
        image.setFitWidth(250);
        image.setPreserveRatio(true);
        Label label = new Label();
        label.setText("Created by Aniket Joshi\nv1.0.0");
        label.setStyle("-fx-text-fill:white");
        label.setTextAlignment(TextAlignment.CENTER);
        one.setText("Built using Gluon Mobile, JavaFxPorts, and \nGluon open source libraries");
        one.setStyle("-fx-text-fill:white");
        Separator serp = new Separator();
        ImageView co = new ImageView(Chess.ICON);
        co.setFitWidth(100);
        co.setPreserveRatio(true);
        imageCont.getChildren().addAll(one, image, serp, co, label);
    }

}
