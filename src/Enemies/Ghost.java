package Enemies;

import Handlers.Content;
import Map.TileMap;
import Players.Enemy;
import Players.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//ศัตรู ผี
public class Ghost extends Enemy {
	
	private Player player;
	private ArrayList<Enemy> enemies;
	
	private BufferedImage[] idleGhost;
	private BufferedImage[] jumpGhost;
	private BufferedImage[] attackGhost;
	
	private boolean jumping;
	
	private static final int IDLE = 0;
	private static final int JUMPING = 1;
	private static final int ATTACKING = 2;
	
	private int attackTick;
	private int attackDelay = 30;
	private int step;
	
	public Ghost(TileMap tm, Player p, ArrayList<Enemy> en) {
		
		super(tm);
		player = p;
		enemies = en;
		
		health = maxHealth = 4;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		damage = 1;
		moveSpeed = 1;
		fallSpeed = 0.15;
		maxFallSpeed = 5.0;
		jumpStart = -5.5;
		
		idleGhost = Content.Ghost[0];
		jumpGhost = Content.Ghost[1];
		attackGhost = Content.Ghost[2];
		
		frame.setFrames(idleGhost);
		frame.setDelay(-1);
		
		attackTick = 0;
		
	}
	
	private void getNextPosition() {
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
	}
	
	public void update() {
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// update animation
		frame.update();
		
		if(player.getx() < x) facingRight = false;
		else facingRight = true;
		
		// idle
		if(step == 0) {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				frame.setFrames(idleGhost);
				frame.setDelay(-1);
			}
			attackTick++;
			if(attackTick >= attackDelay && Math.abs(player.getx() - x) < 60) {
				step++;
				attackTick = 0;
			}
		}
		// jump away
		if(step == 1) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				frame.setFrames(jumpGhost);
				frame.setDelay(-1);
			}
			jumping = true;
			if(facingRight) left = true;
			else right = true;
			if(falling) {
				step++;
			}
		}
		// attack
		if(step == 2) {
			if(dy > 0 && currentAction != ATTACKING) {
				currentAction = ATTACKING;
				frame.setFrames(attackGhost);
				frame.setDelay(3);
				Energy de = new Energy(tileMap);
				de.setPosition(x, y);
				if(facingRight) de.setVector(3, 3);
				else de.setVector(-3, 3);
				enemies.add(de);
			}
			if(currentAction == ATTACKING && frame.hasPlayedOnce()) {
				step++;
				currentAction = JUMPING;
				frame.setFrames(jumpGhost);
				frame.setDelay(-1);
			}
		}
		// done attacking
		if(step == 3) {
			if(dy == 0) step++;
		}
		// land
		if(step == 4) {
			step = 0;
			left = right = jumping = false;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}
	
}
