
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Jacob
 */
public class Player extends PlayerHitbox {

    private Image walk[][] = new Image[4][4]; // Following display the aesthetic look of the player on screen
    private Image stopImage[] = new Image[4];
    private Animation ani[] = new Animation[4];
    private SpriteSheet gsprite;

    private Terrain terrain;
    private Rectangle hitboxOfPlayer, playerHealthBar;

    private boolean playerHasStopped; // If player has stopped moving

    protected int directionPlayerTravelling;

    private int healthBarWidth, healthBarHeight;

    private int walkSpeed; // Speed of player
    
    private Font font = new TrueTypeFont(new java.awt.Font("Impact", 1, 24), true); // Font to draw "points" and "current round" in top left of screen

    public Player(int x, int y) throws SlickException {
        super(x, y, 26, 30);

        healthBarWidth = 300; // Initialize width and height of player health bar
        healthBarHeight = 30;

        hitboxOfPlayer = super.getRect();

        terrain = new Terrain();

        gsprite = new SpriteSheet("images/george.png", 48, 48);

        gsprite.startUse(); // Start using the gpsprite to collect images for use

        for (int i = 0; i < 4; i++) { // Collects the player stop images
            stopImage[i] = gsprite.getSubImage(i, 0);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                walk[i][j] = gsprite.getSubImage(i, j); // Get all animations
            }
            ani[i] = new Animation(walk[i], 40); // Get animation for walking
        }

        gsprite.endUse();

        playerHealthBar = new Rectangle(super.X_SCREEN_SIZE / 3, 0, healthBarWidth, healthBarHeight); // Create new healthbar for player at top of screen

    }

    public int getXHitboxCoord() {
        return (int) hitboxOfPlayer.getX();
    }

    public int getYHitboxCoord() {
        return (int) hitboxOfPlayer.getY();
    }

    public void move(Input kb, ArrayList<Rectangle> walls, ArrayList<Rectangle> zombies) {
        playerHasStopped = false;

        int xCoordinate = (int) hitboxOfPlayer.getX(); // Gets X and Y of player
        int yCoordinate = (int) hitboxOfPlayer.getY();

        int originalX = xCoordinate, originalY = yCoordinate; // Sets previous X and Y values for player
        
        // Changes travelling direction of player based on which keyboard key is pressed
        if (kb.isKeyDown(Input.KEY_RIGHT)) { 
            xCoordinate += walkSpeed;

            directionPlayerTravelling = 3; 

        } else if (kb.isKeyDown(Input.KEY_LEFT)) {
            xCoordinate -= walkSpeed;

            directionPlayerTravelling = 1;

        } else if (kb.isKeyDown(Input.KEY_DOWN)) {
            yCoordinate += walkSpeed;

            directionPlayerTravelling = 0;

        } else if (kb.isKeyDown(Input.KEY_UP)) {
            yCoordinate -= walkSpeed;

            directionPlayerTravelling = 2;

        } else {
            playerHasStopped = true; // If no key is pressed
        }

      
        // Set the hitbox X and Y locations (the imaginary rectangle around player)  
        hitboxOfPlayer.setX(xCoordinate);
        hitboxOfPlayer.setY(yCoordinate);

        if (super.isHittingWall(this.getPlayer(), walls)) { // If the player is hitting a wall, reset the X and Y locations to before the wall
            hitboxOfPlayer.setX(originalX);
            hitboxOfPlayer.setY(originalY);
        }
    }

    public boolean hitsMonster_CheckIfDead(ArrayList<Rectangle> zombies) { 
        if (this.getHealth() > 0) { // If the player is not dead, keep looping
            if (super.isHittingMonster(this.getPlayer(), zombies)) { // If the player is hitting zombie, lower player health by 1
                decrementHealth(1);
            }
            return true; 

        } else { // If player has health <= 0, return false and end game
            return false;
        }

    }

    public int getDirection() { // Returns direction player is facing
        return this.directionPlayerTravelling;
    }

    public void setDirection(int d) {
        this.directionPlayerTravelling = d;
    }

    public Rectangle getPlayer() {
        return hitboxOfPlayer;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red); // Sets the health bar color and font of the health text
        g.setFont(font);
        g.drawString("Health Left: ",playerHealthBar.getX() - 130, playerHealthBar.getY()); // Draw easy to see health indicator
        g.fillRect(playerHealthBar.getX(), playerHealthBar.getY(), healthBarWidth, healthBarHeight); // Creatse health rectangle
        g.draw(playerHealthBar); 

        if (playerHasStopped) {
            ani[directionPlayerTravelling].stop();
            // Stop drawing the animation image at array location 
            stopImage[directionPlayerTravelling].draw(hitboxOfPlayer.getX() - 12, hitboxOfPlayer.getY() - 12);
            /*
            Draw the stop image in array at location and subtract the offset
             */

        } else {
            ani[directionPlayerTravelling].start();
            // Start drawing the animation image at correct location in array
            ani[directionPlayerTravelling].draw(hitboxOfPlayer.getX() - 12, hitboxOfPlayer.getY() - 12);
            /*
            Draw animation in array at direction location and subtract offset
             */

        }
    }

    public void incrementHealth(int d) {
        healthBarWidth += d;
    }

    public int decrementHealth(int d) {
        return healthBarWidth -= d;
    }

    private int getHealth() {
        return healthBarWidth;
    }

    @Override
    public void setSpeed(int d) {
        this.walkSpeed = d;
    }

}
