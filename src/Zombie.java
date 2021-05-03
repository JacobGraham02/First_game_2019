
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
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
public class Zombie extends ZombieHitbox {

    private Terrain terrain; // Class declaration

    private SpriteSheet Zombies; // Saving various images for zombies
    private Image stopImgZombie[] = new Image[3];
    private Image directionZombieFacing[] = new Image[4];

    private int zombieHitting;

    private ArrayList<Rectangle> hitboxMonsterArray = new ArrayList<>();
    private ArrayList<Rectangle> monsterHealthBars = new ArrayList<>();

    private Random r = new Random();

    private int zombieDir; // Direction zombie facing and speed monster travels at
    private float monsterSpeed;

    private final int X_OFFSET = 2; // The X and Y offsets for zombie image when drawing it
    private final int Y_OFFSET = 28;
    private final int BULLET_DAMAGE_PER_SHOT = 20; // Damage per shot when bullet hits

    public Zombie(int x, int y) throws SlickException {
        super(x, y);

        hitboxMonsterArray = super.createHitbox(x, y); // Assign the arraylist of zombies to superclass creation of zombies arraylist
        monsterHealthBars = super.getMonsterHealthBar(x, y); // Assign arraylist of zombie healthbars to superclass creation of healthbars arraylist

        terrain = new Terrain(); // Class declaration

        Zombies = new SpriteSheet("images/Monsters.png", 32, 64); // New spritesheet for use to get zombie images
        Zombies.startUse();

        for (int i = 0; i < 3; i++) { // Loops to collect zombie images
            stopImgZombie[i] = Zombies.getSubImage(i, 0);
        }

        for (int i = 0; i < 4; i++) {
            directionZombieFacing[i] = Zombies.getSubImage(1, i);
        }

        Zombies.endUse();

    }

    public ArrayList<Rectangle> addMonster(int x, int y) {
        return hitboxMonsterArray = super.createHitbox(x, y); // Get the array of monster hitboxes
    }

    public ArrayList<Rectangle> getHealthBars(int x, int y) {
        return monsterHealthBars = super.getMonsterHealthBar(x, y); // Get the array of monster healthbars
    }

    public void setHealthBars(ArrayList<Rectangle> zombieList) {
        this.monsterHealthBars = zombieList;
    }

    public int hitZombie(Iterator<Rectangle> zombie, Iterator<Rectangle> zombieHp, Rectangle currentZombie, Rectangle currentZombieHealth, Bullet hitbox) {
        if (hitbox.isHittingMonster(currentZombie)) { // If the bullet is hitting a zombie

            // Set the current zombie that you are hitting
            zombieHitting = hitboxMonsterArray.indexOf(currentZombie);

            // Subtract the bullet damage off the width of the zombie healthbar
            monsterHealthBars.get(zombieHitting).setWidth(monsterHealthBars.get(zombieHitting).getWidth() - Bullet.getBulletDamage());

            // If the healthbar is 0 or below delete both the zombie and zombie healthbar
            if (monsterHealthBars.get(zombieHitting).getWidth() <= 0.0f) {
                zombie.remove();
                zombieHp.remove();
                return 100; // Returns the amount of points gotten per zombie kill
            }
        }
        hitbox.teleportBullet(576, 76); // Sets the bullet back to default location
        return 25; // Return the amount of points gotten per zombie hit
    }

    public void draw(Graphics g) {
        monsterSpeed = Round.setSpeedOnRound(Round.getCurrentRound()); // Set monster speed

        for (Rectangle i : hitboxMonsterArray) {
            g.drawImage(directionZombieFacing[zombieDir], i.getX() - X_OFFSET, i.getY() - Y_OFFSET);
            // Subtracting X and Y offsets off zombies hitbox to make zombie image centered
        }
    }
    // For each position of the zombie on the level screen, draw a health hitbox above the zombie

    public ArrayList<Rectangle> getMonsters() {
        return hitboxMonsterArray;
    }

    public void easyPathfindingAlgorithm(Input kb, int xHitboxCoord, int yHitboxCoord) {

        for (Rectangle i : hitboxMonsterArray) {

            monsterCollidesWithWall(terrain.getWalls(), xHitboxCoord, yHitboxCoord); // The zombies teleport through walls

            /* The following if statements cause the zombies to move towards the player by taking the difference between their X and Y locations and the 
             player's X and Y coordinates.
            */
            
            if (i.getX() < xHitboxCoord) {
                i.setX(i.getX() + (float) monsterSpeed);
                zombieDir = 2;
            }
            if (i.getX() > xHitboxCoord) {
                i.setX(i.getX() - (float) monsterSpeed);
                zombieDir = 1;
            }
            if (i.getY() < yHitboxCoord) {
                i.setY(i.getY() + (float) monsterSpeed);
                zombieDir = 0;

            }
            if (i.getY() > yHitboxCoord) {
                i.setY(i.getY() - (float) monsterSpeed);
                zombieDir = 3;
            }
        }
    }

    public void monsterCollidesWithWall(ArrayList<Rectangle> barriers, int xCoord, int yCoord) {
        for (Rectangle i : hitboxMonsterArray) { // Triggers if a monster hits a wall
            for (Rectangle j : barriers) { // If monster collides with wall, quickly move through wall as if teleporting through the wall
                if (i.intersects(j)) {
                    if (i.getX() < xCoord) {
                        i.setX(i.getX() + 2.0f); // 0
                    }
                    if (i.getX() > xCoord) {
                        i.setX(i.getX() - 2.0f); // 1
                    }
                    if (i.getY() < yCoord) {
                        i.setY(i.getY() + 2.0f); // 2
                    }
                    if (i.getY() > yCoord) {
                        i.setY(i.getY() - 2.0f); // 3
                    }

                }
            }

        }
    }
}
