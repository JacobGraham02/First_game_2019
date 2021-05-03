
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Jacob
 */
public class Bullet extends PlayerHitbox {

    Player p; // Class declarations
    Zombie m;
    Terrain terrain;
    SpriteSheet bullet;
    Image bulletImageToDraw;

    boolean alreadyExecuted; // Properties of bullet 
    private Rectangle bulletHitbox;
    private int xCoordinate, yCoordinate;
    private int directionPlayerFacing;
    private int bulletSpeed;
    
    private final static int BULLET_DAMAGE = 25; // Sets damage per bullet

    public Bullet(int x, int y) throws SlickException {
        super(x, y, 12, 12); // Creates a new bullet hitbox at certain location
        bulletHitbox = super.getRect();

        terrain = new Terrain();

        bullet = new SpriteSheet("images/bullets.png", 32, 25);

        bullet.startUse();
        bulletImageToDraw = bullet.getSubImage(1, 4); // Creates a copy of the bullet image
        bulletImageToDraw = bulletImageToDraw.getScaledCopy(0.5f);
        bullet.endUse();

    }

    public Rectangle getBulletHitbox() {
        return bulletHitbox; // Getter method for the bullet 
    }

    public void teleportBullet(int x, int y) { 
        bulletHitbox.setX(x);
        bulletHitbox.setY(y);
    }

    public int getBulletHitboxX() {
        return (int) bulletHitbox.getX();
    }

    public int getBulletHitboxY() {
        return (int) bulletHitbox.getY();
    }

    public int getBulletHitboxWidth() {
        return (int) bulletHitbox.getWidth();
    }

    public int getBulletHitboxHeight() {
        return (int) bulletHitbox.getHeight();
    }

    public void draw(Graphics g) {

        g.draw(bulletHitbox);
        g.drawImage(bulletImageToDraw, bulletHitbox.getX(), bulletHitbox.getY()); // Draws the bullet image at bullet hitbox X and Y location
        
        movementOfBullet(terrain.getWalls()); // Generates movement of bullet based on the map walls
    }

    public boolean isHittingMonster(Rectangle i) { // Exclusive method if the bullet intersects a zombie
        if (bulletHitbox.intersects(i)) {
            return true;
        }
        return false;
    }

    private void movementOfBullet(ArrayList<Rectangle> walls) { 
        xCoordinate = (int) bulletHitbox.getX();
        yCoordinate = (int) bulletHitbox.getY();
        int originalX = xCoordinate, originalY = yCoordinate;

        if (!alreadyExecuted && directionPlayerFacing == 3) { // Shoots bullet in certain direction and allows only one bullet to move at a given time
            do {
                xCoordinate += bulletSpeed; // Continuously add bullet speed to certain coordinate until the bullet is out of bounds of the map
            } while (outOfBounds());
        }
        if (!alreadyExecuted && directionPlayerFacing == 1) {
            do {
                xCoordinate -= bulletSpeed;
            } while (outOfBounds());
        }
        if (!alreadyExecuted && directionPlayerFacing == 2) {
            do {
                yCoordinate -= bulletSpeed;
            } while (outOfBounds());
        }
        if (!alreadyExecuted && directionPlayerFacing == 0) {
            do {
                yCoordinate += bulletSpeed;
            } while (outOfBounds());

        }
        bulletHitbox.setX(xCoordinate); // Always update X and Y coordinates of the bullet hitbox
        bulletHitbox.setY(yCoordinate);

        if (super.isHittingWall(this.getBulletHitbox(), walls)) {
            bulletHitbox.setX(originalX); // Reset the location of the bullet for aesthetic purposes
            bulletHitbox.setY(originalY);
            alreadyExecuted = true; // Set to true when player is allowed to shoot another bullet
            bulletHitbox.setLocation(-12, -12);
        }

    }

    public void setDirectionPlayerFacing(int d) {
        this.directionPlayerFacing = d; 
    }

    public final int getDirectionPlayerFacing() {
        return directionPlayerFacing;
    }

    public boolean outOfBounds() {
        if (xCoordinate <= 0 || xCoordinate >= 1148 || yCoordinate <= 0 || yCoordinate >= 1018) { // Detects if bullet is out of bounds
            return true;
        }
        return false;
    }

    @Override
    public void setSpeed(int d) {
        bulletSpeed = d;
    }
    
    public static int getBulletDamage() {
        return BULLET_DAMAGE;
    }
}
