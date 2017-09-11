/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.views;

import com.gluonhq.charm.glisten.control.Dialog;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 *
 * @author Aniket
 */
public class GlistenChoiceDialog {

    private final Dialog<String> dialog;
    private final ToggleGroup group;
    private final VBox box;

    public GlistenChoiceDialog() {
        super();
        dialog = new Dialog<>(false);
        box = new VBox();
        group = new ToggleGroup();
        dialog.setContent((box));
        Button yesButton = new Button("OK");
        Button noButton = new Button("Cancel");
        yesButton.setOnAction(event2 -> {
            dialog.setResult(((RadioButton) group.getSelectedToggle()).getText());
            dialog.hide();
        });
        yesButton.setDisable(true);
        group.selectedToggleProperty().addListener((ob, older, newer) -> {
            if (newer != null) {
                yesButton.setDisable(false);
            } else {
                yesButton.setDisable(true);
            }
        });
        noButton.setOnAction(event2 -> {
            dialog.setResult(null);
            dialog.hide();
        });
        dialog.getButtons().addAll(yesButton, noButton);
    }

    public void setItems(ObservableList<String> al) {
        for (String s : al) {
            RadioButton rb = new RadioButton(s);
            group.getToggles().add(rb);
            box.getChildren().add(rb);
        }
    }

    public void setTitle(String s) {
        dialog.setTitleText(s);
    }

    public Optional<String> showAndWait() {
        return dialog.showAndWait();
    }

}
