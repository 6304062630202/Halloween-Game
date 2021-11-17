package Handlers;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// คลาสนี้ไว้ load ข้อมูล
public class Content {

	public static BufferedImage[][] Fire = load("/Players/Fire.PNG", 30, 30);
	public static BufferedImage[][] Energy = load("/Players/Energy.PNG", 5, 5);
	public static BufferedImage[][] Spider = load("/Players/Spider.PNG", 39, 20);
	public static BufferedImage[][] Ghost = load("/Players/Ghost.PNG", 30, 30);
	public static BufferedImage[][] Monster = load("/Players/Monster.PNG", 25, 25);
	public static BufferedImage[][] Fire2 = load("/Players/Fire1.PNG", 20, 20);
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] bfi;
		try {
			BufferedImage sheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = sheet.getWidth() / w;
			int height = sheet.getHeight() / h;
			bfi = new BufferedImage[height][width];

			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					bfi[i][j] = sheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return bfi;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics!!!");
			System.exit(0);
		}
		return null;
	}
	
}
