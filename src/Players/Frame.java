package Players;

import java.awt.image.BufferedImage;

public class Frame {
	
	private BufferedImage[] frames;
	private int current;
	private int num;
	private int count;
	private int delay;
	private int timesPlayed;
	
	public Frame() {
		timesPlayed = 0;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		current = 0;
		count = 0;
		timesPlayed = 0;
		delay = 2;
		num = frames.length;
	}
	
	public void setDelay(int i) { delay = i; }
	public void setFrame(int i) { current = i; }
	public void setNum(int i) { num = i; }
	
	public void update() {
		
		if(delay == -1) return;
		
		count++;
		
		if(count == delay) {
			current++;
			count = 0;
		}
		if(current == num) {
			current = 0;
			timesPlayed++;
		}
		
	}
	
	public int getFrame() { return current; }
	public int getCount() { return count; }
	public BufferedImage getImage() { return frames[current]; }
	public boolean hasPlayedOnce() { return timesPlayed > 0; }
	public boolean hasPlayed(int i) { return timesPlayed == i; }
	
}