
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author jake3660
 */
public abstract class PlayerHitbox {

    private Rectangle rect;
    private int health;
    private int speed;
    private int pointsPerKill;
    private int x, y, w, h;

    public PlayerHitbox(int x, int y, int w, int h) {

        rect = new Rectangle(x, y, w, h);

    }

    public Rectangle getRect() {
        return rect;
    }

    public int setHealth() {
        return 0;
    }

    public int setSpeed() {
        return 0;
    }


    public boolean isHittingMonster(Rectangle hitbox, ArrayList<Rectangle> Zombies) {
        for (Rectangle zombie : Zombies) {
            if (hitbox.intersects(zombie)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHittingWall(Rectangle hitbox, ArrayList<Rectangle> walls) {
        for (Rectangle wall : walls) {
            if (hitbox.intersects(wall)) {
                return true;
            }
        }
        return false;
    }

}
