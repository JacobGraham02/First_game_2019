
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Jacob
 */
public abstract class ZombieHitbox {

    private Rectangle monsterHitbox; // Zombie stuff
    private Rectangle monsterHealthBar;
    private ArrayList<Rectangle> monsterHealthBars = new ArrayList<>();
    private ArrayList<Rectangle> monsters = new ArrayList<>();

    protected final int ZOMBIE_HITBOX_WIDTH = 32; // Width and height of zombies
    protected final int ZOMBIE_HITBOX_HEIGHT = 32;

    private int currentRoundZombieHp; 

    public ZombieHitbox(int x, int y) {
        monsterHitbox = new Rectangle(x, y, ZOMBIE_HITBOX_WIDTH, ZOMBIE_HITBOX_HEIGHT); // Create zombie hitboxes
        monsterHealthBar = new Rectangle(x, y, currentRoundZombieHp, 10);

        monsters.add(monsterHitbox); // Add the hitboxes to arraylist
        monsterHealthBars.add(monsterHealthBar);
    }

    public ArrayList<Rectangle> createHitbox(float x, float y) {
        monsters.add(new Rectangle(x, y, ZOMBIE_HITBOX_WIDTH, ZOMBIE_HITBOX_HEIGHT)); 
        return monsters;
    }

    public ArrayList<Rectangle> getMonsterHealthBar(int x, int y) {
        // Setting the properties of the monster health bar based on the current round properties for zombies
        currentRoundZombieHp = Round.getCurrentRound();
        monsterHealthBars.add(new Rectangle(x, y, Round.setHealthOnRound(currentRoundZombieHp), 10));
        return monsterHealthBars;
    }
    public boolean isHittingWall(Rectangle i, ArrayList<Rectangle> d) {
        for (Rectangle j : d) { // Exclusive is hitting wall method for zombies
            if (i.intersects(j)) {
                return true;
            }
        }
        return false;
    }
}
