package GameState;

import Audio.Studio;
import Main.GamePanel;

import java.awt.*;

//ตั้งค่าตัวแปร
public class GameStateManager {

	private GameState[] gameStates;
	private int current;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 16;
	public static final int HOMESTATE = 0;
	public static final int ENDSTATE = 1;
	public static final int HELPSTATE = 2;
	public static final int LEVEL1STATE = 3;
	public static final int LEVEL2STATE = 4;
	public static final int FINISHSTATE = 5;
	
	public GameStateManager() {
		
		Studio.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		current = HOMESTATE;
		loadState(current);

	}
	
	private void loadState(int state) {
		if(state == HOMESTATE)
			gameStates[state] = new HomeState(this);
		else if (state == ENDSTATE)
			gameStates[state] = new EndState(this);
		else if (state == HELPSTATE)
			gameStates[state] = new HelpState(this);
		else if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		else if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		else if (state == FINISHSTATE)
			gameStates[state] = new FinishState(this);

	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(current);
		current = state;
		loadState(current);
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[current] != null) gameStates[current].update();
	}
	
	public void draw(Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[current] != null) gameStates[current].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}