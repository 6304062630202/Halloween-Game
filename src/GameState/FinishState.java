package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Handlers.Keys;
import Main.GamePanel;

//คลาสเมื่อจบเกม
class FinishState extends GameState {

    private float turn;
    private Color color;

    private double bone;
    private BufferedImage image;

    public FinishState(GameStateManager gsm) {
        super(gsm);
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Players/PlayerBone.PNG"
                    )).getSubimage(0, 0, 40, 40);
        }
        catch(Exception e) {}
    }

    public void init() {}

    public void update() {
        handleInput();
        color = Color.getHSBColor(turn, 1f, 1f);
        turn += 0.01;
        if(turn > 1) turn = 0;
        bone += 0.1;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
        at.rotate(bone);
        g.drawImage(image, at, null);
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.ENTER)) gsm.setState(GameStateManager.ENDSTATE);
    }

}