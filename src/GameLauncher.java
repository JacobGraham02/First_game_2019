
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author jake3660
 */
public class GameLauncher extends StateBasedGame {

    public GameLauncher(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new IntroScreen());
        this.addState(new MainGame());
        this.addState(new EndScreen());
        this.addState(new EndScreen2());
        
        // Player must get 5000 points to win the game

    }

    public static void main(String args[]) throws SlickException {
        GameLauncher game = new GameLauncher("Testing Game");
        AppGameContainer app = new AppGameContainer(game);
        app.setDisplayMode(1140, 1048, false);
        app.setShowFPS(false);
        app.setTargetFrameRate(100);
        app.start();
    }

}
