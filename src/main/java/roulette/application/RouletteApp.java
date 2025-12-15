package roulette.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;


    public class RouletteApp  extends Application {

        Roulette roulette = new Roulette();


        @Override
        public void start(Stage stage) throws IOException {
            stage.show();

            AnchorPane anchor_pane = new AnchorPane();

            Button startTheRoulette = new Button("Start the Roulette");
            Label resultLabel = new Label("Result:");
            Label wantBetColor = new Label("Do you want to bet on Color?");

            TextField amount = new TextField("Amount"); //how much money
            TextField betNumber = new TextField("On what number do you want to bet?");

            //If want to bet on color:
            Button redButton = new Button("Red");
            Button blackButton =  new Button("Black");
            Button greenButton = new Button("Green");
            Label welcomeToRoulette= new Label("Welcome to Roulette");

            // Welcome to Roulette Text to center
            AnchorPane.setTopAnchor(welcomeToRoulette, 30.0);
            AnchorPane.setLeftAnchor(welcomeToRoulette, 0.0);
            AnchorPane.setRightAnchor(welcomeToRoulette, 0.0);
            welcomeToRoulette.setAlignment(Pos.CENTER);

            //HBox for color buttons (user clicks these if wants to bet on color, not number)
            // Located on left bottom
            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(redButton, greenButton, blackButton);
            buttonBox.setAlignment(Pos.CENTER_LEFT);
            AnchorPane.setLeftAnchor(buttonBox, 30.0);
            AnchorPane.setBottomAnchor(buttonBox, 30.0);

            // Add buttonBox with buttons and text to VBox so elements would be alligned perfectly with Label
            VBox buttonVBox = new VBox(10);
            buttonVBox.setAlignment(Pos.CENTER);
            buttonVBox.getChildren().addAll(buttonBox, wantBetColor);
            AnchorPane.setLeftAnchor(buttonVBox, 30.0);
            AnchorPane.setBottomAnchor(buttonVBox, 30.0);

            // Start the roulette button (wheel starts to spin after user clicks)
            // Located on center bottom
            HBox startBox = new HBox(startTheRoulette);
            startBox.setAlignment(Pos.CENTER);
            AnchorPane.setBottomAnchor(startBox, 30.0);
            AnchorPane.setLeftAnchor(startBox, 0.0);
            AnchorPane.setRightAnchor(startBox, 0.0);


            // Result on top leftr
            AnchorPane.setTopAnchor(resultLabel, 30.0);
            AnchorPane.setLeftAnchor(resultLabel, 20.0);

            //



            //HBox wheelBox = new HBox();
            // Add all to anchor pane
            anchor_pane.getChildren().addAll(welcomeToRoulette, buttonBox, startBox, resultLabel); // pitää lisätä viel amount ja betnumber

            // Start Scene
            Scene scene = new Scene(anchor_pane, 1280, 720);
            stage.setScene(scene);
            stage.show();

            startTheRoulette.setOnAction(e -> {
                String colorName;

                Roulette.Winner winner = roulette.spin();

                if (winner.getColor().equals(Color.RED)) {
                    colorName = "Red";
                }
                else if (winner.getColor().equals(Color.BLACK)) {
                    colorName = "Black";
                }
                else {
                    colorName = "Green";
                }

                resultLabel.setText("Result: \n" + "Winning Number: " + winner.getWinnerNumber()
                        + "\nWinning Color: " + colorName);
            });
        }


    public static void main(String[] args) {
        launch();
    }
}
