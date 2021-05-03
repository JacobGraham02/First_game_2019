
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Jacob
 */

/*

    Most of this class are static and/or final methods because of not needing to create an instance of this class to call the methods
    A lot of the methods in this class depend on one another to function correctly

 */
public class Round {

    private static int currentRound; // Properties of the current round
    private static int monstersPerRound;

    TrueTypeFont font;

    public Round() {

        monstersPerRound = 5;
        currentRound = 1;

        font = new TrueTypeFont(new java.awt.Font("Impact", 1, 16), true); // Creates font to use for round stuff

    }

    public static int getCurrentRound() {
        return currentRound;
    }

    public static void setCurrentRound(int d) {
        currentRound = d;
    }

    public void incrementCurrentRound(int d) {
        currentRound += d;
    }

    public static final int getMonstersOnRound(int n) {
        if (n == 1) { // n being the current round
            return monstersPerRound;
        } else {
            return monstersPerRound + 3;
        }

    }

    public static float setSpeedOnRound(int n) {
        if (n == 1) { // n being the current monster speed
            return 0.5f;
        } else {
            return 0.5f + 0.15f;
        }
    }

    public static final int setHealthOnRound(int n) {
        if (n <= 1) { // n being the current monster health
            return 20;
        } else {
            return 15 + setHealthOnRound(n - 1);
        }
    }

    public void draw(Graphics g) {
        g.scale(2, 2); // Upsize the round text
        g.setFont(font); 
        g.setColor(Color.yellow);
        g.drawString("Current Round: " + String.valueOf(currentRound), 20, 50); // Draw the current round
    }
}
