
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Jacob
 */
public abstract class ZombieHitbox {

    private Rectangle monsterHitbox;
    private ArrayList<Rectangle> monsters = new ArrayList<>();

    public ZombieHitbox(int x, int y) {
        monsterHitbox = new Rectangle(x, y, 32, 32);
        monsters.add(monsterHitbox);
    }

    public ArrayList<Rectangle> createHitbox(int x, int y) {
        monsters.add(new Rectangle(x, y, 32, 32));
        return monsters;
    }

    public abstract int setSpeed(int a);

    public abstract int setHealth(int a);

    public boolean isHittingWall(Rectangle i, ArrayList<Rectangle> d) {
        for (Rectangle j : d) {
            if (i.intersects(j)) {
                return true;
            }
        }
        return false;
    }
}
