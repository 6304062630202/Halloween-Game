package Enemies;

import Handlers.Content;
import Main.GamePanel;
import Map.TileMap;
import Players.Enemy;
import Players.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

//ศัตรู ตัวหนอน
public class Monster extends Enemy {
	
	private BufferedImage[] monster;
	private Player player;
	private boolean active;
	
	public Monster(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 1;
		
		width = 25;
		height = 25;
		cwidth = 20;
		cheight = 20;
		
		damage = 1;
		moveSpeed = 1;
		fallSpeed = 0.15;
		maxFallSpeed = 5.0;
		jumpStart = -5;

		monster = Content.Monster[0];

		frame.setFrames(monster);
		frame.setDelay(4);
		
		left = true;
		facingRight = false;
		
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
		
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
		// update animation
		frame.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}
	
}
