
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Jacob
 */
public class Player extends PlayerHitbox {

    private Image walk[][] = new Image[4][4];
    private Image stopImage[] = new Image[4];
    private SpriteSheet gsprite;
    private boolean stopped;
    private Animation ani[] = new Animation[4];
    private Rectangle hitbox;
    protected int direction;
    private TerrainV2 terrain;

    public Player(int x, int y) throws SlickException {
        super(x, y, 26, 30);
        
        hitbox = super.getRect();

        terrain = new TerrainV2();

        gsprite = new SpriteSheet("images/george.png", 48, 48);

        gsprite.startUse(); // Start using the gpsprite only to collect images

        for (int i = 0; i < 4; i++) {
            stopImage[i] = gsprite.getSubImage(i, 0);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                walk[i][j] = gsprite.getSubImage(i, j); // Get all animations
            }
            ani[i] = new Animation(walk[i], 40); // Get animation for walking
        }

        gsprite.endUse();

    }

    public int getXHitboxCoord() {
        return (int) hitbox.getX();
    }

    public int getYHitboxCoord() {
        return (int) hitbox.getY();
    }

//    @Override
//    public boolean isHittingMonster(Rectangle hitbox, ArrayList<Rectangle> Zombies) {
//        for (Rectangle zombie : Zombies) {
//            if (hitbox.intersects(zombie)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isHittingWall(Rectangle hitbox, ArrayList<Rectangle> walls) {
//        for (Rectangle wall : walls) {
//            if (hitbox.intersects(wall)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void move(Input kb, ArrayList<Rectangle> walls, ArrayList<Rectangle> zombies) {
        stopped = false;

        int xCoordinate = (int) hitbox.getX();
        int yCoordinate = (int) hitbox.getY();
        int originalX = xCoordinate, originalY = yCoordinate;

        if (kb.isKeyDown(Input.KEY_RIGHT)) {
            xCoordinate += 2;
            direction = 3; // Sets int direction for use in animation array

        } else if (kb.isKeyDown(Input.KEY_LEFT)) {

            xCoordinate -= 2;
            direction = 1;

        } else if (kb.isKeyDown(Input.KEY_DOWN)) {

            yCoordinate += 2;
            direction = 0;

        } else if (kb.isKeyDown(Input.KEY_UP)) {

            yCoordinate -= 2;
            direction = 2;

        } else {
            stopped = true;
        }

        // If no key is being pressed, assume player has stopped moving
        // Set the hitbox X and Y locations (the imaginary rectangle around player)  
        hitbox.setX(xCoordinate);
        hitbox.setY(yCoordinate);
        // The hitbox is the thing that keeps the sprite "moving" around the screen

        if (super.isHittingMonster(this.getPlayer(), walls) || super.isHittingMonster(this.getPlayer(), zombies)) {
            hitbox.setX(originalX);
            hitbox.setY(originalY);
        }

    }

    public int getDirection() { // Returns direction player is facing
        return this.direction;
    }

    public void setDirection(int d) {
        this.direction = d;
    }

    public Rectangle getPlayer() {
        return hitbox;
    }

    public void draw(Graphics g) {
        if (stopped) {
            ani[direction].stop();
            // Stop drawing the animation image at array location 
            stopImage[direction].draw(hitbox.getX() - 12, hitbox.getY() - 12);
            /*
            Draw the stop image in array at location and subtract the offset
             */

        } else {
            ani[direction].start();
            // Start drawing the animation image at correct location in array
            ani[direction].draw(hitbox.getX() - 12, hitbox.getY() - 12);
            /*
            Draw animation in array at direction location and subtract offset
             */

        }
    }
    @Override
    public int setSpeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
