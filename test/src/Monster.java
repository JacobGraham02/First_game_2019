
import java.util.ArrayList;
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
public class Monster extends ZombieHitbox {

    //private SpriteSheet Zombies;
    private Rectangle hitboxZombie;
    private TerrainV2 terrain;
    private Iterator<Rectangle> monsterIter;

    private SpriteSheet Zombies;
    private Image stopImgZombie[] = new Image[3];
    private Image AnimationZombie[][] = new Image[3][4];
    private ArrayList<Rectangle> hitboxMonster = new ArrayList<>();
    private Random r = new Random();

    private int healthAmount = 50;
    private final float monsterSpeed = 0.5f;

    // stopImgZombie array
    // animationZombie array
    // ArrayList hitbox 
    public Monster(int x, int y) throws SlickException {
        super(x, y);

        monsterIter = hitboxMonster.iterator();

        hitboxMonster = super.createHitbox(x, y);

        terrain = new TerrainV2();

        Zombies = new SpriteSheet("images/Monsters.png", 32, 64);

        Zombies.startUse();

        for (int i = 0; i < 3; i++) {
            stopImgZombie[i] = Zombies.getSubImage(i, 0);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                AnimationZombie[i][j] = Zombies.getSubImage(i, j);
            }
        }

        Zombies.endUse();
    }

    public ArrayList<Rectangle> addMonster(int x, int y) {
        return hitboxMonster = super.createHitbox(x, y);
    }

    public int hitZombie(Iterator<Rectangle> zombie, Bullet hitbox) {
        System.out.println(zombie);
        //zombie.remove();
        hitbox.deleteBullet(576, 76); // Sets the bullet back to default location
        return 100;
    }

    public void draw(Graphics g) {
        for (Rectangle i : hitboxMonster) {
            g.drawImage(stopImgZombie[0], i.getX() - 2, i.getY() - 28); // Subtracting X and Y offsets off zombies hitbox
        }
    }

    public ArrayList<Rectangle> getMonsters() {
        return hitboxMonster;
    }

    public void easyPathfindingAlgorithm(Input kb, int xHitboxCoord, int yHitboxCoord) {

        for (Rectangle i : hitboxMonster) {
            if (i.getX() < xHitboxCoord) {
                i.setX(i.getX() + (float) monsterSpeed);

            } else if (i.getX() > xHitboxCoord) {
                i.setX(i.getX() - (float) monsterSpeed);

            }

            if (i.getY() < yHitboxCoord) {
                i.setY(i.getY() + (float) monsterSpeed);

            } else if (i.getY() > yHitboxCoord) {
                i.setY(i.getY() - (float) monsterSpeed);

            }
            monsterCollidesWithWall(terrain.getWalls(), xHitboxCoord, yHitboxCoord);
        }
    }

    public void monsterCollidesWithWall(ArrayList<Rectangle> barriers, int xCoord, int yCoord) {
        for (Rectangle i : hitboxMonster) { // Triggers if a monster hits a wall
            for (Rectangle j : barriers) {
                if (i.intersects(j)) {
                    if (i.getX() < xCoord) {
                        i.setX(i.getX() + 2);
                    }
                    if (i.getX() > xCoord) {
                        i.setX(i.getX() - 2);
                    }
                    if (i.getY() < yCoord) {
                        i.setY(i.getY() + 2);
                    }
                    if (i.getY() > yCoord) {
                        i.setY(i.getY() - 2);
                    }

                }
            }

        }
    }

    @Override
    public int setSpeed(int a) {
        return 0;
    }

    @Override
    public int setHealth(int a) {
        return 0;
    }
}
