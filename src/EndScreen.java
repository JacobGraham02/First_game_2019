
/**
 *
 * @author Jacob
 */
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EndScreen extends BasicGameState {

    Image img;
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
       img = new Image("images/lostendscreen.jpg");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException { 

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       img.draw(0,0);
    }
    
    @Override
    public int getID() {
       return 2;  //this id will be different for each screen
    }

    
}
