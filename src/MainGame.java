
/**
 * @author Jacob
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JOptionPane;
import org.newdawn.slick.*;
import static org.newdawn.slick.Input.KEY_SPACE;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainGame extends BasicGameState {

    Terrain terrain; // Class declarations
    Image map;
    Player playerOnScreen;
    Zombie zombie;
    Bullet bullet;
    Round rnd;
    Random r = new Random();

    private int zombieSpawnTimer; // Properties 
    private int pointsPlayerHas;
    private int currentRoundTimer;
    private int numberOfZombies;
    private final int POINTS_TO_WIN = 4000;

    private int[] xLocationForZombie = {290, 361, 707, 75, 82, 928, 1073, 314, 766}; // X and Y locations for zombies to spawn
    private int[] yLocationForZombie = {333, 336, 66, 451, 814, 363, 693, 946, 811};

    private ArrayList<Rectangle> zombies = new ArrayList<>(); // Adds zombies to the gamefield
    private ArrayList<Rectangle> zombieHealth = new ArrayList<>(); // Adds the healthbars to zombies in order to draw above zombies heads (did not implement)

    private Iterator<Rectangle> zombieIterator = zombies.iterator(); // Iterators for both arraylists
    private Iterator<Rectangle> zombieHealthIterator = zombieHealth.iterator();

    boolean alreadyPressed, allowedToShoot, directionHasChanged; // Boolean values to control the bullet 

    private Font font;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        pointsPlayerHas = 100; // Initial amount of points player starts with

        zombie = new Zombie(300, 200); // Construct the initial game objects
        terrain = new Terrain();
        playerOnScreen = new Player(752, 340);
        bullet = new Bullet(578, 76);
        rnd = new Round();

        playerOnScreen.setSpeed(2); // Set the speed guy moves on screen

        // Variables that control the player bullet 
        alreadyPressed = true;
        allowedToShoot = true;
        directionHasChanged = true;

        font = new TrueTypeFont(new java.awt.Font("Impact", 1, 16), true); // Font to draw "points" and "current round" in top left of screen
    }

    @Override
    public int getID() { // Returns the current ID of the main game screen
        return 1;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        terrain.draw(grphcs); // Draw methods drawing all game objects on screen
        playerOnScreen.draw(grphcs);
        zombie.draw(grphcs);
        bullet.draw(grphcs);
        rnd.draw(grphcs); // Draws the current round

        grphcs.drawString("Points: " + String.valueOf(pointsPlayerHas), 20, 10);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        bullet.setSpeed(4); // Sets initial bullet speed

        Iterator<Rectangle> zombieIterator = zombies.iterator(); // Iterator used to cycle through the zombie arraylist
        Iterator<Rectangle> zombieHealthIterator = zombieHealth.iterator();

        Input kb = gc.getInput();

        if (kb.isMousePressed(MOUSE_LEFT_BUTTON)) { // Left mouse button clicked
            JOptionPane.showMessageDialog(null, " X: " + kb.getMouseX() + " Y: " + kb.getMouseY());
        }

        playerOnScreen.move(kb, terrain.getWalls(), zombie.getMonsters()); // Move the player and create hit detection for map walls and zombies
        zombie.easyPathfindingAlgorithm(kb, playerOnScreen.getXHitboxCoord(), playerOnScreen.getYHitboxCoord()); // Moves zombie according to player X and Y

        if (kb.isKeyPressed(KEY_SPACE) && allowedToShoot) { // If allowed to shoot gun and space key pressed
            bullet = new Bullet(playerOnScreen.getXHitboxCoord(), playerOnScreen.getYHitboxCoord()); // Create bullet at player X and Y location 
            bullet.setDirectionPlayerFacing(playerOnScreen.getDirection()); // Set the bullet travel direction to whatever direction player is facing

            directionHasChanged = false; // Disallows bullet to be shot again
            allowedToShoot = false;
        }

        if (directionHasChanged) { // Allows the bullet to change direction in which it is shooting
            bullet.setDirectionPlayerFacing(playerOnScreen.getDirection());
        }

        if (bullet.getBulletHitboxX() == -12 && bullet.getBulletHitboxY() == -12) { // If bullet is off screen allow another bullet to be shot
            directionHasChanged = true;
            allowedToShoot = true;
        }

        while (zombieIterator.hasNext()) {
            Rectangle currentZombie = zombieIterator.next(); // The new zombie is the next in arraylist
            Rectangle currentZombieHealthBox = zombieHealthIterator.next();

            if (bullet.isHittingMonster(currentZombie)) { // Sending next zombie from iterator into class, setting a current class
                pointsPlayerHas += zombie.hitZombie(zombieIterator, zombieHealthIterator, currentZombie, currentZombieHealthBox, bullet); // Returns a 100 if a zombie is killed
            }
        }

        int currentRound = Round.getCurrentRound(); // Get the current game round
        int maxZombies = Round.getMonstersOnRound(currentRound); // Get the maximum amount of zombies per round

        if (numberOfZombies < maxZombies) { // If number of zombies on current round is less than maximum amount increment spawn timer

            zombieSpawnTimer++;

            if (zombieSpawnTimer % 100 == 0) { // Every 100 milliseconds spawn a new zombie at a specific X and Y location chosen from 2 parallel arrays

                int zombieXLoc = xLocationForZombie[r.nextInt(9)];
                int zombieYLoc = yLocationForZombie[r.nextInt(9)];

                zombies = zombie.addMonster(zombieXLoc, zombieYLoc); // Zombies arraylist is return value of function that creates arraylist of zombies
                zombieHealth = zombie.getHealthBars(zombieXLoc, zombieYLoc); // Zombiehealthbars is return value of function that crates list of healhbars

                numberOfZombies++; // Increments number of zombies on screen

            }
        }

        currentRoundTimer++;
        
        if (currentRoundTimer % 1000 == 0) { // After 10 seconds, increment round and reset number of zombies
            rnd.incrementCurrentRound(1);
            numberOfZombies = 0;
        }

        if (!playerOnScreen.hitsMonster_CheckIfDead(zombies)) { // Shows the "you lost" end screen
            sbg.enterState(2, new FadeOutTransition(), new FadeInTransition()); 
        } else if (pointsPlayerHas >= POINTS_TO_WIN) { // Shows the "you won" end screen
            sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
