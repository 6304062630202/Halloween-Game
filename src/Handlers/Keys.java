package Handlers;

import java.awt.event.KeyEvent;

// คลาสกำหนดปุ่มทำงาน
public class Keys {
	
	public static final int NUM_KEY = 10;
	
	public static boolean keyState[] = new boolean[NUM_KEY];
	public static boolean KeyState1[] = new boolean[NUM_KEY];
	
	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON1 = 4;
	public static int BUTTON2 = 5;
	public static int BUTTON3 = 6;
	public static int ENTER = 7;
	public static int ESCAPE = 8;
	
	public static void keySet(int i, boolean k) {

		if(i == KeyEvent.VK_UP) keyState[UP] = k;
		else if(i == KeyEvent.VK_LEFT) keyState[LEFT] = k;
		else if(i == KeyEvent.VK_DOWN) keyState[DOWN] = k;
		else if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = k;
		else if(i == KeyEvent.VK_E) keyState[BUTTON1] = k;
		else if(i == KeyEvent.VK_G) keyState[BUTTON2] = k;
		else if(i == KeyEvent.VK_F) keyState[BUTTON3] = k;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = k;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = k;

	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEY; i++) {
			KeyState1[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return keyState[i] && !KeyState1[i];
	}
	
	public static boolean anyKeyPress() {
		for(int i = 0; i < NUM_KEY; i++) {
			if(keyState[i]) return true;
		}
		return false;
	}
	
}
