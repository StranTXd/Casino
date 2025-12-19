package roulette.application;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Set;


public class RouletteApp  extends Application {

        Roulette roulette = new Roulette();


        static final int[] NUMBERS = {0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26};
        final double DEGREES_PER_NUMBERS = 360.0 / 37;

        private double angleForNumber(int number) {
            for(int i = 0; i < NUMBERS.length; i++) {
                if (NUMBERS[i] == number) {
                    return i * DEGREES_PER_NUMBERS;
                }
            }
            throw new IllegalArgumentException("INVALID NUMBER !!");
        }

        private Color getColorForNumber(int number) {
            if (number == 0){
                return Color.GREEN;
            }
            int[] reds  = { 1, 3, 5, 7, 9, 12, 14, 16, 18,
                    19, 21, 23, 25, 27, 30, 32, 34, 36};

            for( int red : reds) {
                if (red == number){
                    return Color.RED;
                }
            }
            return Color.BLACK;
        }

    private Canvas createRouletteCanvas(double size) {

        Canvas canvas = new Canvas(size, size);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double center = size / 2;
        double radius = size / 2 - 10;

        double startAngle = 90; // 0 сверху

        for (int i = 0; i < 37; i++) {
            int number = NUMBERS[i];
            Color color = getColorForNumber(number);
            gc.setFill(color);

            gc.fillArc(
                    10, 10,
                    radius * 2, radius * 2,
                    startAngle - i * DEGREES_PER_NUMBERS,
                    -DEGREES_PER_NUMBERS,
                    ArcType.ROUND
            );

            double textAngle = Math.toRadians(startAngle - (i + 0.5) * DEGREES_PER_NUMBERS);
            double textRadius = radius * 0.75;

            double x = center + Math.cos(textAngle) * textRadius;
            double y = center - Math.sin(textAngle) * textRadius;

            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(number), x - 6, y + 4);

        }

        return canvas;
    }

    private Node createRoulettePointer() {

        Polygon base = new Polygon(
                0.0, 0.0,
                12.0, 14.0,
                0.0, 28.0,
                -12.0, 14.0
        );

        base.setFill(new LinearGradient(
                0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#FFD700")),
                new Stop(0.5, Color.web("#C9A227")),
                new Stop(1, Color.web("#FFF1A8"))
        ));

        base.setStroke(Color.web("#8B7500"));
        base.setStrokeWidth(1.5);

        Circle rivet = new Circle(0, 14, 2.5, Color.web("#6B5B00"));

        return new Group(base, rivet);
    }

        private void spin(Canvas wheel, int winningNumber){
            double targetAngle = angleForNumber(winningNumber);
            double spins = 5 * 360;

            RotateTransition rotate = new RotateTransition(Duration.seconds(4), wheel);
            rotate.setFromAngle(0);
            rotate.setToAngle(spins - targetAngle);
            rotate.setInterpolator(Interpolator.EASE_OUT);

            rotate.play();
        }


        @Override
        public void start(Stage stage) throws IOException {

            stage.show();

            AnchorPane anchor_pane = new AnchorPane();

            Button startTheRoulette = new Button("Start the Roulette");
            Label resultLabel = new Label("Result:");
            Label wantBetColorLabel = new Label("Do you want to bet on Color?");
            Label wantBetNumberLabel = new Label("Do you want to bet on Number?");

            TextField amountTF = new TextField(""); //how much money
            amountTF.setPromptText("Enter Amount to Bet");
            Label ammountLabel  = new Label("How much money do you want to bet?");

            TextField betNumberTF = new TextField();
            betNumberTF.setPromptText("On what number do you want to bet?");
            Button acceptNumberBetButton = new Button("Bet on Number");

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
            HBox buttonBoxHBox = new HBox(10);
            buttonBoxHBox.getChildren().addAll(redButton, greenButton, blackButton);
            buttonBoxHBox.setAlignment(Pos.CENTER_LEFT);
            AnchorPane.setLeftAnchor(buttonBoxHBox, 30.0);
            AnchorPane.setBottomAnchor(buttonBoxHBox, 30.0);

            // Add buttonBox with buttons and text to VBox so elements would be alligned perfectly with Label
            VBox buttonVBox = new VBox(10);
            buttonVBox.setAlignment(Pos.CENTER);
            buttonVBox.getChildren().addAll(wantBetColorLabel, buttonBoxHBox);
            AnchorPane.setLeftAnchor(buttonVBox, 30.0);
            AnchorPane.setBottomAnchor(buttonVBox, 30.0);

            // Start the roulette button (wheel starts to spin after user clicks)
            // Located on center bottom
            HBox startBoxHBox = new HBox(startTheRoulette);
            startBoxHBox.setAlignment(Pos.CENTER);

            VBox startBoxVBox = new VBox(10);
            startBoxVBox.setAlignment(Pos.CENTER);
            startBoxVBox.getChildren().addAll(ammountLabel, amountTF, startBoxHBox);
            AnchorPane.setBottomAnchor(startBoxVBox, 30.0);
            AnchorPane.setLeftAnchor(startBoxVBox, 0.0);
            AnchorPane.setRightAnchor(startBoxVBox, 0.0);
            startBoxVBox.setFillWidth(false);


            // Result on top left
            AnchorPane.setTopAnchor(resultLabel, 30.0);
            AnchorPane.setLeftAnchor(resultLabel, 20.0);

            HBox numberInputHBox = new HBox(8);
            numberInputHBox.getChildren().addAll(betNumberTF, acceptNumberBetButton);
            numberInputHBox.setAlignment(Pos.CENTER);

            // If user wants to bet on number
            VBox betOnNumberVBox = new VBox(6);
            betOnNumberVBox.getChildren().addAll(wantBetNumberLabel, numberInputHBox);
            betOnNumberVBox.setAlignment(Pos.CENTER);

            AnchorPane.setRightAnchor(betOnNumberVBox, 30.0);
            AnchorPane.setBottomAnchor(betOnNumberVBox, 30.0);


            // Roulette
            Canvas rouletteCanvas = createRouletteCanvas(350);
            Node pointer = createRoulettePointer();

            StackPane rouletteStackPane = new StackPane(rouletteCanvas, pointer);
            rouletteStackPane.setAlignment(Pos.CENTER);
            rouletteStackPane.setAlignment(pointer, Pos.TOP_CENTER);
            pointer.setTranslateY(-25);
            pointer.setTranslateX(7);

            AnchorPane.setTopAnchor(rouletteStackPane, 80.0);
            AnchorPane.setLeftAnchor(rouletteStackPane, 0.0);
            AnchorPane.setRightAnchor(rouletteStackPane, 0.0);


            // Add all to anchor pane
            anchor_pane.getChildren().addAll(welcomeToRoulette, startBoxVBox, resultLabel, betOnNumberVBox, buttonVBox,rouletteStackPane);
            // Start Scene
            Scene scene = new Scene(anchor_pane, 1280, 720);
            stage.setScene(scene);
            stage.show();

            startTheRoulette.setOnAction(e -> {
                String colorName;

                Roulette.Winner winner = roulette.spin();
                spin(rouletteCanvas, winner.getWinnerNumber());

                if (Color.RED.equals(winner.getColor())) {
                    colorName = "Red";
                }
                else if (Color.BLACK.equals(winner.getColor())) {
                    colorName = "Black";
                }
                else {
                    colorName = "Green";
                }

                resultLabel.setText("Result: \n" + "Winning Number: " + winner.getWinnerNumber()
                        + "\nWinning Color: " + colorName);
            });
        }

}
