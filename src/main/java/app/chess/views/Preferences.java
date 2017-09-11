/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.chess.views;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author aniket
 */
public class Preferences extends BorderPane {

    private static final BooleanProperty showAll = new SimpleBooleanProperty(true),
            timeLimit = new SimpleBooleanProperty(false),
            flipBoard = new SimpleBooleanProperty(false);
    private static final IntegerProperty timeProperty = new SimpleIntegerProperty(10);
    private final VBox box;
    private final CheckBox possible, time, flip;
    private final Slider timeProp;

    public Preferences() {
        setCenter(box = new VBox(10));
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        possible = new CheckBox("Show All Possible Moves");
        possible.setSelected(showAll.get());
        showAll.addListener((ob, older, newer) -> {
            saveAll();
        });
        possible.selectedProperty().addListener((ob, older, newer) -> {
            showAll.set(newer);
        });
        possible.setStyle("-fx-text-fill:white;");
        time = new CheckBox("Time Limit");
        time.setStyle("-fx-text-fill:white;");
        time.setSelected(timeLimit.get());
        timeProp = new Slider(5, 35, 10);
        timeProp.setShowTickLabels(true);
        timeProp.setShowTickMarks(true);
        timeProp.setMajorTickUnit(10);
        timeProp.setMinorTickCount(4);
        timeProp.setBlockIncrement(1);
        timeProperty.addListener((ob, older, newer) -> {
            saveAll();
        });
        timeProp.valueProperty().addListener((ob, older, newer) -> {
            if (!timeProp.isValueChanging()) {
                timeProperty.set(newer.intValue());
            } else if (newer.intValue() == newer.doubleValue()) {
                timeProperty.set(newer.intValue());
            }
        });
        timeLimit.addListener((ob, older, newer) -> {
            saveAll();
            if (newer) {
                if (!box.getChildren().contains(timeProp)) {
                    box.getChildren().add(timeProp);
                }
            } else {
                box.getChildren().remove(timeProp);
            }
        });
        flip = new CheckBox("Flip Board");
        flip.setStyle("-fx-text-fill:white;");
        flip.setSelected(flipBoard.get());
        flip.selectedProperty().addListener((ob, older, newer) -> {
            flipBoard.set(newer);
        });
        flipBoard.addListener((ob, older, newer) -> {
            saveAll();
        });
        time.selectedProperty().addListener((ob, older, newer) -> {
            timeLimit.set(newer);
        });
        box.getChildren().addAll(possible, flip, time);
        if (timeLimit.get()) {
            if (!box.getChildren().contains(timeProp)) {
                box.getChildren().add(timeProp);
            }
        }
    }

    public static void loadAll() {
        Services.get(StorageService.class).ifPresent((storage) -> {
            storage.getPrivateStorage().ifPresent((file) -> {
                File dir = new File(file, "chess");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    File f = new File(dir, "chess_preferences.txt");
                    Scanner in = new Scanner(f);
                    if (in.hasNextLine()) {
                        String s = in.nextLine();
                        showAll.set(Boolean.parseBoolean(s));
                    }
                    if (in.hasNextLine()) {
                        String s = in.nextLine();
                        timeLimit.set(Boolean.parseBoolean(s));
                    }
                    if (in.hasNextLine()) {
                        String s = in.nextLine();
                        timeProperty.set(Integer.parseInt(s));
                    }
                    if (in.hasNextLine()) {
                        String s = in.nextLine();
                        flipBoard.set(Boolean.parseBoolean(s));
                    }
                } catch (FileNotFoundException ex) {
                }
            });
        });
    }

    public static void saveAll() {
        Services.get(StorageService.class).ifPresent((storage) -> {
            storage.getPrivateStorage().ifPresent((file) -> {
                File dir = new File(file, "chess");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File f = new File(dir, "chess_preferences.txt");
                write(f, FXCollections.observableArrayList(showAll.get() + "",
                        timeLimit.get() + "", timeProperty.get() + "",
                        flipBoard.get() + ""));
            });
        });
    }

    private static void write(File f, List<String> al) {
        try (FileWriter fw = new FileWriter(f); PrintWriter pw = new PrintWriter(fw)) {
            for (String s : al) {
                pw.println(s);
            }
        } catch (IOException ex) {
        }
    }

    public static boolean showAllMoves() {
        return showAll.get();
    }

    public static boolean hasTimeLimit() {
        return timeLimit.get();
    }

    public static int getTimeLimit() {
        return timeProperty.get();
    }

    public static boolean flipBoard() {
        return flipBoard.get();
    }
}
