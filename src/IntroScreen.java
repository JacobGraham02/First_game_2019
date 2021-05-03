
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Jacob
 */
public class IntroScreen extends BasicGameState {

    Image img;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        img = new Image("images/titlescreen.jpg"); 
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input in = gc.getInput();

        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition()); // Initializes game on mouse click
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        img.draw(0, 0);
    }

    @Override
    public int getID() {
        return 0;
    }

}
