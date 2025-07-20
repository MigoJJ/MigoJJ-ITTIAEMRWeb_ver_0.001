package com.ittia.gds;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform; // Import Platform for exit

import com.ittia.gds.ui.GDSEMR_frame;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files; // For Files.write

public class EntryittiaFX extends Application {

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 400;
    
    // Common button style
    private static final String BUTTON_STYLE_NORMAL =
        "-fx-background-color: #FF8C00; " + // Darker Orange
        "-fx-text-fill: white; " +
        "-fx-font-size: 14px; " +
        "-fx-font-weight: bold; " +
        "-fx-background-radius: 5; " +
        "-fx-border-color: #FFA500; " +
        "-fx-border-width: 2; " +
        "-fx-border-radius: 5;";

    // Hover button style
    private static final String BUTTON_STYLE_HOVER =
        "-fx-background-color: #FFBF00; " + // Lighter Orange on hover
        "-fx-text-fill: white; " +
        "-fx-font-size: 14px; " +
        "-fx-font-weight: bold; " +
        "-fx-background-radius: 5; " +
        "-fx-border-color: #FFA500; " +
        "-fx-border-width: 2; " +
        "-fx-border-radius: 5;";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Entryittia Interface (JavaFX)");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ADD8E6, #87CEEB);");

        String[] buttonNames = {"Log In", "Prologue", "Version Information", "Rescue", "Ittia Start", "Quit"};

        for (String name : buttonNames) {
            Button button = new Button(name);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setPrefHeight(40);
            button.setStyle(BUTTON_STYLE_NORMAL); // Apply normal style

            // Add hover effect using constants
            button.setOnMouseEntered(e -> button.setStyle(BUTTON_STYLE_HOVER));
            button.setOnMouseExited(e -> button.setStyle(BUTTON_STYLE_NORMAL));

            button.setOnAction(event -> {
                try {
                    handleButtonPress(button.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to perform action: " + e.getMessage());
                }
            });
            root.getChildren().add(button);
        }

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonPress(String buttonText) throws IOException {
        String homeDir = System.getProperty("user.home");
        String docsPath = homeDir + File.separator + "entry_docs";
        
        switch (buttonText) {
            case "Prologue":
                openFileInEditor(docsPath, "Prologue.txt");
                break;
            case "Version Information":
                openFileInEditor(docsPath, "Version_Information.txt");
                break;
            case "Ittia Start":
                System.out.println("Attempting to start GDSEMR_frame...");
                showAlert("Ittia Start", "GDSEMR_frame would start here. Ensure it's JavaFX compatible.");
                GDSEMR_frame.main(null); // Assuming GDSEMR_frame is still a Swing app for now.
                break;
            case "Rescue":
                System.out.println("Rescue action triggered.");
                showAlert("Rescue", "Rescue functionality is a placeholder.");
                break;
            case "Log In":
                System.out.println("Log In action triggered.");
                showAlert("Log In", "Log In functionality is a placeholder.");
                break;
            case "Quit":
                System.out.println("Quitting application.");
                Platform.exit(); // Use Platform.exit() for clean JavaFX application shutdown
                break;
            default:
                System.err.println("Unrecognized action for button: " + buttonText);
                showAlert("Error", "Unrecognized action for button: " + buttonText);
        }
    }

    private void openFileInEditor(String directoryPath, String fileName) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Ensure directory exists
        }

        File file = new File(directory, fileName);

        if (!file.exists()) {
            try {
                Files.write(file.toPath(), ("This is content for " + fileName).getBytes());
            } catch (IOException e) {
                System.err.println("Could not create dummy file: " + e.getMessage());
                showAlert("File Creation Error", "Could not create dummy file: " + e.getMessage());
                return; // Exit if dummy file creation fails
            }
        }

        if (Desktop.isDesktopSupported() && file.exists()) {
            Desktop.getDesktop().open(file);
        } else {
            System.err.println("Desktop is not supported or file does not exist: " + file.getAbsolutePath());
            showAlert("File Error", "Cannot open file. Desktop not supported or file does not exist: " + file.getName());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}