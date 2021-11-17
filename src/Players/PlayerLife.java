package Players;

public class PlayerLife {
	
	private static int life = 3;
	private static int health = 5;
	private static long time = 0;
	
	public static void init() {
		life = 3;
		health = 5;
		time = 0;
	}

	public static int getLife() { return life; }
	public static void setLife(int i) { life = i; }
	
	public static int getHealth() { return health; }
	public static void setHealth(int i) { health = i; }
	
	public static long getTime() { return time; }
	public static void setTime(long t) { time = t; }
	
}
