
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob
 */
public class TerrainV2 {

    private SpriteSheet rockFloor, rockFloor2, Map;
    private Random r = new Random();
    ArrayList<Rectangle> walls = new ArrayList<>();

    public TerrainV2() throws SlickException {
        //walls.add(new Rectangle(0, 0, 0, 0));
        Map = new SpriteSheet("images/MapBuilding.jpg", 1148, 1014);

        // x, y, width, height
        // Adding barriers that good old georgy can't cross
        walls.add(new Rectangle(578, 76, 3, 465));
        walls.add(new Rectangle(576, 536, 178, 3));
        walls.add(new Rectangle(578, 76, 338, 3));
        walls.add(new Rectangle(915, 76, 3, 504));
        walls.add(new Rectangle(752, 536, 3, 42));
        walls.add(new Rectangle(535, 576, 218, 3));
        walls.add(new Rectangle(534, 354, 3, 222));
        walls.add(new Rectangle(94, 348, 445, 3));
        walls.add(new Rectangle(94, 348, 3, 576));
        walls.add(new Rectangle(94, 924, 436, 3));
        walls.add(new Rectangle(527, 738, 3, 187));
        walls.add(new Rectangle(525, 735, 57, 3));
        walls.add(new Rectangle(580, 735, 3, 65));
        walls.add(new Rectangle(580, 798, 477, 3));
        walls.add(new Rectangle(1056, 581, 3, 217));
        walls.add(new Rectangle(914, 578, 141, 3));

    }

    public void draw(Graphics g) {
        Map.startUse();
        Image ZombieMap = Map.getSubImage(0, 0);
        ZombieMap.draw();
        Map.endUse();
    }

    public void drawBarriers(Graphics g) {
        g.setColor(Color.red);
        for (Rectangle r : walls) {
            g.draw(r);
        }
    }

    public ArrayList<Rectangle> getWalls() {
        return walls;
    }

}
