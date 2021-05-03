
/**
 *
 * @author Jacob
 */
public class Round {

    private int currentRound;
    private int monstersPerRound;

    public Round() {

        currentRound = 1;
        monstersPerRound = 5;

    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int d) {
        currentRound += d;
    }

    public final int increaseMonstersOnRound() {
        if (currentRound == 1) {
            monstersPerRound = 5;
        }
        return monstersPerRound += (currentRound += 4);
    }
}
