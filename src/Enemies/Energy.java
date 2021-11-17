package Enemies;

import Handlers.Content;
import Map.TileMap;
import Players.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

//ลูกพลัง
public class Energy extends Enemy {

	private BufferedImage[] startEnergy;
	private BufferedImage[] Energy;

	private boolean start;
	private boolean last;

	private int type = 0;
	private int count = 0;
	public static int VECTOR = 0;
	public static int GRAVITY = 1;
	public static int BOUNCE = 2;

	public Energy(TileMap tm) {

		super(tm);

		health = maxHealth = 1;

		width = 10;
		height = 10;
		cwidth = 10;
		cheight = 10;

		damage = 1;
		moveSpeed = 5;

		startEnergy = Content.Fire2[0];
		Energy = Content.Fire2[1];

		frame.setFrames(startEnergy);
		frame.setDelay(2);

		start = true;
		flinching = true;
		last = false;

	}

	public void setType(int i) { type = i; }
	public void setLast(boolean b) { last = b; }

	public void update() {

		if(start) {
			if(frame.hasPlayedOnce()) {
				frame.setFrames(Energy);
				frame.setNum(3);
				frame.setDelay(2);
				start = false;
			}
		}

		if(type == VECTOR) {
			x += dx;
			y += dy;
		}
		else if(type == GRAVITY) {
			dy += 0.2;
			x += dx;
			y += dy;
		}
		else if(type == BOUNCE) {
			double dx2 = dx;
			double dy2 = dy;
			checkTileMapCollision();
			if(dx == 0) {
				dx = -dx2;
				count++;
			}
			if(dy == 0) {
				dy = -dy2;
				count++;
			}
			x += dx;
			y += dy;
		}

		// update animation
		frame.update();

		if(!last) {
			if(x < 0 || x > tileMap.getWidth() || y < 0 || y > tileMap.getHeight()) {
				remove = true;
			}
			if(count == 3) {
				remove = true;
			}
		}

	}

	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
