package Players;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Map.TileMap;

//ทางเข้าด่านต่อไป
public class Entrance extends MapObject {
	
	private BufferedImage[] entrance;
	
	public Entrance(TileMap tm) {
		super(tm);
		facingRight = true;
		width = height = 40;
		cwidth = 20;
		cheight = 40;
		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Players/End.PNG")
			);
			entrance = new BufferedImage[9];
			for(int i = 0; i < entrance.length; i++) {
				entrance[i] = spritesheet.getSubimage(
					i * width, 0, width, height
				);
			}
			frame.setFrames(entrance);
			frame.setDelay(1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		frame.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
