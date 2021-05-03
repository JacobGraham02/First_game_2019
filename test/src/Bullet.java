
import java.util.ArrayList;
import java.util.Iterator;
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

    Player p;
    Monster m;
    TerrainV2 terrain;
    SpriteSheet bullet;
    Image fireball;

    boolean alreadyExecuted;
    private Rectangle bulletHitbox;
    private int xCoordinate, yCoordinate;
    private int directionPlayerFacing;
    private ArrayList<Rectangle> arr = new ArrayList<>();

    public Bullet(int x, int y) throws SlickException {
        super(x, y, 12, 12);

        bulletHitbox = super.getRect();

        terrain = new TerrainV2();

        bullet = new SpriteSheet("images/Fireballs.jpg", 12, 12);

        bullet.startUse();
        fireball = bullet.getSubImage(0, 0);
        bullet.endUse();

    }

    public Rectangle getBulletHitbox() {
        return bulletHitbox;
    }
    
    public void deleteBullet(int x, int y) {
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
        g.drawImage(fireball, bulletHitbox.getX(), bulletHitbox.getY());
        movementOfBullet(terrain.getWalls());
    }

    public boolean isHittingMonster(Rectangle i) { // Method that only belongs to Bullet class
        if (bulletHitbox.intersects(i)) {
            return true;
        }
        return false;
    }

    private void movementOfBullet(ArrayList<Rectangle> walls) {
        xCoordinate = (int) bulletHitbox.getX();
        yCoordinate = (int) bulletHitbox.getY();
        int originalX = xCoordinate, originalY = yCoordinate;
        int bulletSpeed = 6;

        if (!alreadyExecuted && directionPlayerFacing == 3) {
            do {
                xCoordinate += bulletSpeed;
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
        bulletHitbox.setX(xCoordinate);
        bulletHitbox.setY(yCoordinate);

        if (super.isHittingWall(this.getBulletHitbox(), walls)) {
            bulletHitbox.setX(originalX);
            bulletHitbox.setY(originalY);
            alreadyExecuted = true;
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
        if (xCoordinate <= 0 || xCoordinate >= 1148 || yCoordinate <= 0 || yCoordinate >= 1018) {
            return true;
        }
        return false;
    }

    @Override
    public int setSpeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
