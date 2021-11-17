package Main;

import GameState.GameStateManager;
import Handlers.Keys;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	// กำหนดขนาด
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int time = 60;
	private long targetTime = 1000 / time;
	
	// รูป
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	// ตัวแปรอื่นๆ
	private boolean record = false;
	private int recordCount = 0;
	private boolean screen;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void update() {
		gsm.update();
		Keys.update();
	}
	private void draw() {
		gsm.draw(g);
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
		if(screen) {
			screen = false;
			try {
				java.io.File out = new java.io.File("" + System.nanoTime() + "");
				javax.imageio.ImageIO.write(image, "", out);
			}
			catch(Exception e) {}
		}
		if(!record) return;
		try {
			java.io.File out = new java.io.File("" + recordCount + "");
			javax.imageio.ImageIO.write(image, "", out);
			recordCount++;
		}
		catch(Exception e) {}
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		if(key.isControlDown()) {
			if(key.getKeyCode() == KeyEvent.VK_R) {
				record = !record;
				return;
			}
			if(key.getKeyCode() == KeyEvent.VK_S) {
				screen = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

}