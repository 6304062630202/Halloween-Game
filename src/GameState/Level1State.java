package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.Studio;
import Players.Enemy;
import Players.EnemyProjectile;
import Players.EnergyParticle;
import Players.Explosion;
import Players.Mark;
import Players.Player;
import Players.PlayerLife;
import Players.Entrance;
import Players.Title;
import Enemies.Spider;
import Enemies.Monster;
import Handlers.Keys;
import Main.GamePanel;
import Map.Background;
import Map.TileMap;

public class Level1State extends GameState {

	private Background bg1;
	private Background bg2;
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;
	
	private Mark mark;
	private BufferedImage Text;
	private Title title;
	private Title subtitle;
	private Entrance entrance;
	
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// background
		bg1 = new Background("/Backgrounds/bg2.PNG", 0);
		bg2 = new Background("/Backgrounds/bg1.PNG", 0.1);
		
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Backgrounds/Tileset.PNG");
		tileMap.loadMap("/Maps/level1.map");
		tileMap.setPosition(140, 0);
		tileMap.setBounds(
			tileMap.getWidth() - 1 * tileMap.getTileSize(),
			tileMap.getHeight() - 2 * tileMap.getTileSize(),
			0, 0
		);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(300, 161);
		player.setHealth(PlayerLife.getHealth());
		player.setLives(PlayerLife.getLife());
		player.setTime(PlayerLife.getTime());
		
		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();
		
		// init player
		player.init(enemies, energyParticles);
		
		// explosions
		explosions = new ArrayList<Explosion>();
		
		// mark
		mark = new Mark(player);
		
		// title and subtitle
		try {
			Text = ImageIO.read(
				getClass().getResourceAsStream("/Backgrounds/Level1.PNG")
			);
			title = new Title(Text.getSubimage(0, 0, 178, 30));
			title.sety(55);
			subtitle = new Title(Text.getSubimage(0, 20, 90, 26));
			subtitle.sety(70);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// entrance
		entrance = new Entrance(tileMap);
		entrance.setPosition(3700, 131);
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		// เสียง
		Studio.load("/sounds/entrance.mp3", "entrance");
		Studio.load("/sounds/explode.mp3", "explode");
		Studio.load("/sounds/enemyhit.mp3", "enemyhit");

		Studio.load("/sounds/game.mp3", "game");
		Studio.loop("game", 600, Studio.getFrames("game") - 2200);
		
	}
	
	private void populateEnemies() {
		enemies.clear();

		Monster ms;
		Spider sd;

		ms = new Monster(tileMap, player);
		ms.setPosition(720, 80);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(740, 80);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(900, 40);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1300, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1320, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1340, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1660, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1680, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1700, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(2175, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(2960, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(2980, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(3000, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(3150, 110);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(3540, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(3560, 100);
		enemies.add(ms);

		sd = new Spider(tileMap);
		sd.setPosition(1800, 60);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(2280, 60);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(2600, 100);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(3480, 100);
		enemies.add(sd);
	}
	
	public void update() {
		
		// check keys
		handleInput();
		
		// ถ้าจบ level start
		if(entrance.contains(player)) {
			eventFinish = blockInput = true;
		}
		
		// ถ้าผู้เล่นตาย
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		// move title and subtitle
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}
		
		// move background
		bg1.setPosition(tileMap.getx(), tileMap.gety());
		bg2.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		
		// update tilemap
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();
		
		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}
		
		// update enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if(ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		// update entrance
		entrance.update();
		
	}
	
	public void draw(Graphics2D g) {

		bg1.draw(g);
		bg2.draw(g);

		// draw tilemap
		tileMap.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);
		
		// draw entrance
		entrance.draw(g);
		
		// draw mark
		mark.draw(g);
		
		// draw title
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		if(Keys.isPressed(Keys.BUTTON2)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON3)) player.setCharging();
	}

//////////////////// EVENTS ////////////////////
	
	// reset level
	private void reset() {
		player.reset();
		player.setPosition(300, 161);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(Text.getSubimage(0, 0, 178, 30));
		title.sety(55);
		subtitle = new Title(Text.getSubimage(0, 20, 90, 26));
		subtitle.sety(70);
	}
	
	// level started
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}
	
	// player dead
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.ENDSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}
	
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			Studio.play("entrance");
			player.setTeleporting(true);
			player.stop();
		}
		else if(eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			Studio.stop("entrance");
		}
		if(eventCount == 180) {
			PlayerLife.setHealth(player.getHealth());
			PlayerLife.setLife(player.getLives());
			PlayerLife.setTime(player.getTime());
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		
	}

}