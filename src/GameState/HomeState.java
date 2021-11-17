package GameState;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Audio.Studio;
import Players.PlayerLife;
import Handlers.Keys;

//หน้าแรก
public class HomeState extends GameState {

	private BufferedImage bg;
	private BufferedImage button;
	private int choice = 0;
	private String[] options = {
		"Start",
		"Help",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public HomeState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			//background และ ปุ่มหัวส่ง
			bg = ImageIO.read(
					getClass().getResourceAsStream("/Backgrounds/Bg.PNG")
			).getSubimage(0, 0, 320, 240);

			button = ImageIO.read(
				getClass().getResourceAsStream("/Players/Mark.PNG")
			).getSubimage(0, 12, 12, 11);

			// สีและขนาดตัวอักษร
			titleColor = Color.RED;
			titleFont = new Font("2006_iannnnnPDA", Font.PLAIN, 58);
			font = new Font("2006_iannnnnPDA", Font.PLAIN, 28);
			
			// load เสียง
			Studio.load("/sounds/menuoption.mp3", "menuoption");
			Studio.load("/sounds/menuselect.mp3", "menuselect");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		
		// เช็ค keys
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		// วาดพื้นหลัง
		g.drawImage(bg,0,0,null);

		// วาดหัวข้อ
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("HELLO", 125, 45);
		g.drawString("HALLOWEEN", 85, 75);

		// วาดตัวเลือก
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString("Start", 150, 105);
		g.drawString("Help", 150, 125);
		g.drawString("Quit", 150, 145);

		// วาดตำแหน่งของปุ่มหัวส้ม
		if(choice == 0) g.drawImage(button, 130, 95, null);
		else if(choice == 1) g.drawImage(button, 130, 115, null);
		else if(choice == 2) g.drawImage(button, 130, 135, null);
		
	}
	
	private void select() {
		if(choice == 0) {
			Studio.play("menuselect");
			PlayerLife.init();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		else if(choice == 1){
			Studio.play("menuselect");
			PlayerLife.init();
			gsm.setState(GameStateManager.HELPSTATE);
		}
		else if(choice == 2) {
			System.exit(0);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(choice > 0) {
				Studio.play("menuoption", 0);
				choice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(choice < options.length - 1) {
				Studio.play("menuoption", 0);
				choice++;
			}
		}
	}
}
