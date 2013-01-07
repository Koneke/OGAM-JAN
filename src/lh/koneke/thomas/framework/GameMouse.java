package lh.koneke.thomas.framework;


public class GameMouse {
	static Vector2f position = new Vector2f(0,0);
	static float x = 0;
	static float y = 0;
	public static boolean left = false;
	public static boolean right = false;
	public static boolean wheel = false;
	public static boolean prevLeft = false;
	public static boolean prevRight = false;
	public static boolean prevWheel = false;
	
	public static void setPosition(Vector2f v) {
		position = v;
		x = v.x;
		y = v.y;
	}
	
	public static void setx(float inx) {
		x = inx;
		position.x = inx;
	}
	
	public static void sety(float iny) {
		y = iny;
		position.y = iny;
	}
	
	public static Vector2f getPosition() {
		return position;
	}
	
	public static float getx() {
		return x;
	}
	
	public static float gety() {
		return y;
	}
}
