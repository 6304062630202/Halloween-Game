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
import Enemies.Ghost;
import Handlers.Keys;
import Main.GamePanel;
import Map.Background;
import Map.TileMap;


public class Level2State extends GameState {
	
	private Background cellar;
	
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
	private boolean eventQuake;
	
	public Level2State(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// background
		cellar = new Background("/Backgrounds/bg3.PNG", 0.5, 0);
		
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Backgrounds/Tileset.PNG");
		tileMap.loadMap("/Maps/level2.map");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(300, 131);
		player.setHealth(PlayerLife.getHealth());
		player.setLives(PlayerLife.getLife());
		player.setTime(PlayerLife.getTime());
		
		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();
		
		player.init(enemies, energyParticles);
		
		// explosions
		explosions = new ArrayList<Explosion>();
		
		// mark
		mark = new Mark(player);
		
		// title and subtitle
		try {
			Text = ImageIO.read(
				getClass().getResourceAsStream("/Backgrounds/Level2.PNG")
			);
			title = new Title(Text.getSubimage(0, 0, 178, 30));
			title.sety(65);
			subtitle = new Title(Text.getSubimage(0, 20, 90, 26));
			subtitle.sety(80);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// entrance
		entrance = new Entrance(tileMap);
		entrance.setPosition(2850, 371);
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		// เสียง
		Studio.load("/sounds/entrance.mp3", "entrance");
		Studio.load("/sounds/explode.mp3", "explode");
		Studio.load("/sounds/enemyhit.mp3", "enemyhit");
		
	}
	
	private void populateEnemies() {
		enemies.clear();
		Monster ms;
		Spider sd;
		Ghost gg;

		ms = new Monster(tileMap, player);
		ms.setPosition(600, 160);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(620, 160);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(740, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(750, 100);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(900, 150);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1000, 250);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1080, 250);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1220, 250);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1320, 250);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1570, 160);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1590, 160);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1610, 160);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(1800, 160);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(2600, 370);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(2620, 370);
		enemies.add(ms);
		ms = new Monster(tileMap, player);
		ms.setPosition(2640, 370);
		enemies.add(ms);
		
		sd = new Spider(tileMap);
		sd.setPosition(900, 130);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(1070, 270);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(1190, 270);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(1315, 200);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(1705, 300);
		enemies.add(sd);
		sd = new Spider(tileMap);
		sd.setPosition(2080, 550);
		enemies.add(sd);
		
		gg = new Ghost(tileMap, player, enemies);
		gg.setPosition(1900, 580);
		enemies.add(gg);
		gg = new Ghost(tileMap, player, enemies);
		gg.setPosition(2330, 550);
		enemies.add(gg);
		gg = new Ghost(tileMap, player, enemies);
		gg.setPosition(2400, 490);
		enemies.add(gg);
		gg = new Ghost(tileMap, player, enemies);
		gg.setPosition(2460, 430);
		enemies.add(gg);
		gg = new Ghost(tileMap, player, enemies);
		gg.setPosition(2680, 370);
		enemies.add(gg);
	}
	
	public void update() {
		
		// check keys
		handleInput();
		
		// check if quake event should start
		if(player.getx() > 2175 && !tileMap.isShaking()) {
			eventQuake = blockInput = true;
		}
		
		// check if end of level event should start
		if(entrance.contains(player)) {
			eventFinish = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventQuake) eventQuake();
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
		
		// move backgrounds
		cellar.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
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
				explosions.add(
					new Explosion(tileMap, e.getx(), e.gety()));
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
		
		// draw background
		cellar.draw(g);
		
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
		player.loseLife();
		player.reset();
		player.setPosition(300, 131);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(Text.getSubimage(0, 0, 178, 30));
		title.sety(65);
		subtitle = new Title(Text.getSubimage(0, 20, 90, 26));
		subtitle.sety(80);
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
		if(eventCount == 1) player.setDead();
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
				reset();
			}
		}
	}
	
	// earthquake
	private void eventQuake() {
		eventCount++;
		if(eventCount == 1) {
			player.stop();
			player.setPosition(2175, player.gety());
		}
		if(eventCount == 60) {
			player.setEmote(Player.CONFUSED);
		}
		if(eventCount == 120) player.setEmote(Player.NONE);
		if(eventCount == 150) tileMap.setShaking(true, 10);
		if(eventCount == 180) player.setEmote(Player.SURPRISED);
		if(eventCount == 300) {
			player.setEmote(Player.NONE);
			eventQuake = blockInput = false;
			eventCount = 0;
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
			gsm.setState(GameStateManager.FINISHSTATE);
		}
		
	}

}