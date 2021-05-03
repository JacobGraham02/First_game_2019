
/**
 *
 * @author Jacob
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JOptionPane;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import static org.newdawn.slick.Input.KEY_SPACE;
import static org.newdawn.slick.Input.MOUSE_LEFT_BUTTON;
import org.newdawn.slick.geom.Rectangle;

public class MainGameV2 extends BasicGame {

    TerrainV2 terrainV2;
    Image map;
    Player bob;
    Monster monster;
    Bullet hitbox;
    Random r = new Random();

    private int timer;
    private int points;
    private ArrayList<Rectangle> zombies = new ArrayList<>();
    private Iterator<Rectangle> zombieIterator;

    boolean alreadyPressed, allowedToShoot, directionHasChanged;

    private Font font;

    int directionPlayerFacing, shootingTimer, numberOfZombies;

    public MainGameV2(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        zombieIterator = zombies.iterator();
        points = 100;
        monster = new Monster(300, 200);
        terrainV2 = new TerrainV2();
        bob = new Player(752, 340);
        hitbox = new Bullet(578, 76);

        directionPlayerFacing = 0;
        alreadyPressed = true;
        allowedToShoot = true;
        directionHasChanged = true;

        font = new TrueTypeFont(new java.awt.Font("Impact", 1, 16), true);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Iterator<Rectangle> zombieIterator = zombies.iterator(); // Iterator used to cycle through the zombie arraylist
        Input kb = gc.getInput();

        bob.move(kb, terrainV2.getWalls(), monster.getMonsters());
        monster.easyPathfindingAlgorithm(kb, bob.getXHitboxCoord(), bob.getYHitboxCoord());
        // Sets current direction player is facing

        if (kb.isKeyPressed(KEY_SPACE) && allowedToShoot) {
            hitbox = new Bullet(bob.getXHitboxCoord(), bob.getYHitboxCoord());
            hitbox.setDirectionPlayerFacing(bob.getDirection());
            directionHasChanged = false;
            allowedToShoot = false;
        }

        if (directionHasChanged) {
            hitbox.setDirectionPlayerFacing(bob.getDirection());
        }

        if (hitbox.getBulletHitboxX() == -12 && hitbox.getBulletHitboxY() == -12) { // If bullet is off screen . . .
            directionHasChanged = true;
            allowedToShoot = true;
        }

        while (zombieIterator.hasNext()) { // If zombie loop hasNext zombie...
            if (hitbox.isHittingMonster(zombieIterator.next())) {
                points += monster.hitZombie(zombieIterator, hitbox); // Returns a 100 if a zombie is killed (deleted from screen)
            }
        }

        if (numberOfZombies < 10) {
            timer++;
            if (timer == 200) { // Every 100 updates spawn a zombie at random location
                zombies = monster.addMonster(r.nextInt(900), r.nextInt(900));
                timer = 0;
                numberOfZombies += 1;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        terrainV2.draw(g);
        bob.draw(g);
        terrainV2.drawBarriers(g);
        monster.draw(g);
        hitbox.draw(g);

        g.setFont(font);
        g.setColor(Color.yellow);
        g.drawString("Points: " + String.valueOf(points), 20, 10);
    }

    public static void main(String args[]) throws SlickException {
        MainGameV2 game = new MainGameV2("Testing Game");
        AppGameContainer app = new AppGameContainer(game);
        app.setDisplayMode(1148, 1014, false);
        app.setShowFPS(false);
        app.setTargetFrameRate(100);
        app.start();
    }

}
