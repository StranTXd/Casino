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

    ArrayList<Integer> REDS = new ArrayList<>(Arrays.asList(32,19,21,25,34,27,36,30,32,5,16,1,14,9,18,7,12,3));
    ArrayList<Integer> BLACKS = new ArrayList<>(Arrays.asList(15,4,2,17,6,13,11,8,10,24,33,20,31,22,29,28,35,26));

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

            // If randomResult is 0, it is green. Set winnerNumber to 0 and color is green
            if (randomResult == 0) {
                winner.setColor(Color.GREEN);
                winner.setWinnerNumber(0);
            }
            // If number in BLACKS, winner color is black and number is black
            else if (BLACKS.contains(randomResult)) {
                winner.setColor(Color.BLACK);
                winner.setWinnerNumber(randomResult);
            }
            // Else, we got red
            else if (REDS.contains(randomResult)) {
                winner.setColor(Color.RED);
                winner.setWinnerNumber(randomResult);
            }
        }
    }




    class Bet {
        //BetType type; // onko bet värelle vai numerolle
        int amount;
        Integer number; // Jos betti on tietylle numerolle
    }
}