
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author jake3660
 */
public abstract class PlayerHitbox {

    private final Rectangle rect; // Player rectangle

    protected final int X_SCREEN_SIZE = 1140; 
    protected final int Y_SCREEN_SIZE = 1048;

    private int playerXLocation; // Properties
    private int playerYLocation;
    private int playerWidth;
    private int playerHeight;

    public PlayerHitbox(int x, int y, int w, int h) {
        this.playerXLocation = x; // Initialize the player properties
        this.playerYLocation = y;
        this.playerWidth = w;
        this.playerHeight = h;

        rect = new Rectangle(playerXLocation, playerYLocation, playerWidth, playerHeight);

    }

    public final Rectangle getRect() {
        return rect;
    }
    
    public abstract void setSpeed(int d);

    
    // Below methods are inherited by Player and Bullet
    // Detects if either Player or Bullet hitboxes are hitting a specific zombie or wall
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
