package GameState;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Audio.Studio;
import Players.PlayerLife;
import Handlers.Keys;
import Main.GamePanel;

public class EndState extends GameState{
    private BufferedImage button;
    private int current = 0;
    private String[] options = {
            "Restart",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public EndState(GameStateManager gsm) {

        super(gsm);

        try {

            // ปุ่มหัวส้ม
            button = ImageIO.read(
                    getClass().getResourceAsStream("/Players/Mark.PNG")
            ).getSubimage(0, 12, 12, 11);

            // สีและขนาดตัวอักษร
            titleColor = Color.WHITE;
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

        // check keys
        handleInput();

    }

    public void draw(Graphics2D g) {

        // วาดพื้นหลัง
        g.setColor(Color.RED);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        // วาดหัวข้อ
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("GAME", 122, 60);
        g.drawString("OVER!!!", 116, 100);

        // วาดตัวเลือก
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Restart", 145, 145);
        g.drawString("Quit", 145, 165);

        // วาดตำแหน่งหัวส้ม
        if(current == 0) g.drawImage(button, 125, 135, null);
        else if(current == 1) g.drawImage(button, 125, 155, null);

    }

    private void select() {
        if(current == 0) {
            Studio.play("menuselect");
            PlayerLife.init();
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        else if(current == 1) {
            System.exit(0);
        }
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.ENTER)) select();
        if(Keys.isPressed(Keys.UP)) {
            if(current > 0) {
                Studio.play("menuoption", 0);
                current--;
            }
        }
        if(Keys.isPressed(Keys.DOWN)) {
            if(current < options.length - 1) {
                Studio.play("menuoption", 0);
                current++;
            }
        }
    }
}
