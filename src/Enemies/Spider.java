package Enemies;

import Handlers.Content;
import Map.TileMap;
import Players.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

//ศัตรู แมงมุม
public class Spider extends Enemy {
	
	private BufferedImage[] idleSpider;
	
	private int tick;
	private double a;
	private double b;
	
	public Spider(TileMap tm) {
		
		super(tm);
		
		health = maxHealth = 2;
		
		width = 39;
		height = 20;
		cwidth = 25;
		cheight = 15;
		
		damage = 1;
		moveSpeed = 5;
		
		idleSpider = Content.Spider[0];
		
		frame.setFrames(idleSpider);
		frame.setDelay(4);
		
		tick = 0;
		a = Math.random() * 0.06 + 0.07;
		b = Math.random() * 0.06 + 0.07;
		
	}
	
	public void update() {
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		tick++;
		x = Math.sin(a * tick) + x;
		y = Math.sin(b * tick) + y;
		
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
