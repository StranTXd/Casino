package roulette.application;

import java.util.*;
import javafx.scene.paint.Color;
import java.util.Arrays;
import java.util.ArrayList;
/**
 * First get a random number that will be a winner
 * and after this, make roulette land on this number to show user a number that won.
 *
 * User can bet on number or on color.
 * Build using JavaFX. You need JavaFX to run it. You have to import JavaFX library to your libraries for it to work.
 * Built on Java 25 SDK.
 */


public class Roulette {

    Set<Integer> NUMBERS = Set.of(0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26);
    Set<Integer> REDS = Set.of(32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3);
    Set<Integer> BLACKS = Set.of(	15, 4, 2, 17, 6, 13, 11, 8, 10, 24, 33, 20, 31, 22, 29, 28, 35, 26);

    private static final int GREEN = 0;

    // Return winner from SpinResult();
    public Winner spin() {
        SpinResult result = new SpinResult();
        return result.winner;
    }

    static class Winner {
        Color color;
        int winnerNumber;

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public int getWinnerNumber() {
            return winnerNumber;
        }

        public void setWinnerNumber(int winnerNumber) {
            this.winnerNumber = winnerNumber;
        }
    }

    class SpinResult {
        Winner winner;
        int randomResult;


        SpinResult() {
            Random rand = new Random();
            randomResult = rand.nextInt(37);

            winner = new Winner();
            winner.setWinnerNumber(randomResult);
            // If randomResult is 0, it is green. Set winnerNumber to 0 and color is green
            if (randomResult == 0) {
                winner.setColor(Color.GREEN);
            }
            // If number in BLACKS, winner color is black and number is black
            else if (BLACKS.contains(randomResult)) {
                winner.setColor(Color.BLACK);
            }
            // Else, we got red
            else {
                winner.setColor(Color.RED);
            }
        }
    }




    class Bet {
        //BetType type; // onko bet värelle vai numerolle
        int amount;
        Integer number; // Jos betti on tietylle numerolle
    }
}