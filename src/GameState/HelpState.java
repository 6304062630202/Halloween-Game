package GameState;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Audio.Studio;
import Players.PlayerLife;
import Handlers.Keys;

//คลาสบอกวิธีเล่น
public class HelpState extends GameState{
    private BufferedImage bg;
    private BufferedImage button;
    private int current = 0;
    private String[] options = {
            "Back"
    };

    private Color titleColor;
    private Font font;

    public HelpState(GameStateManager gsm) {

        super(gsm);

        try {
            //background และ ปุ่มหัวส้ม
            bg = ImageIO.read(
                    getClass().getResourceAsStream("/Backgrounds/howto.PNG")
            ).getSubimage(0, 0, 320, 240);

            button = ImageIO.read(
                    getClass().getResourceAsStream("/Players/Mark.PNG")
            ).getSubimage(0, 12, 12, 11);

            // สีและขนาดตัวอักษร
            titleColor = Color.WHITE;
            font = new Font("2006_iannnnnPDA", Font.PLAIN, 28);

            // load เสียง
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

        // วาดตัวอักษร
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Back", 50, 210);

        // วาดปุ่ม
        if(current == 0) g.drawImage(button, 30, 200, null);
    }

    private void select() {
        if(current == 0) {
            Studio.play("menuselect");
            PlayerLife.init();
            gsm.setState(GameStateManager.HOMESTATE);
        }
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.ENTER)) select();
    }
}
