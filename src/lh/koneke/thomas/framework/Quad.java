package lh.koneke.thomas.framework;


public class Quad implements Shape {
	public Vector2f topleft;
	public Vector2f topright;
	public Vector2f bottomleft;
	public Vector2f bottomright;
	
	public Quad(Vector2f topleft, Vector2f topright, Vector2f bottomleft, Vector2f bottomright) {
		this.topleft = topleft;
		this.topright = topright;
		this.bottomleft = bottomleft;
		this.bottomright = bottomright;
	}
	
	public Quad(Rectangle rectangle) {
		this.topleft = new Vector2f(rectangle.x,  rectangle.y);
		this.topright = new Vector2f(rectangle.x + rectangle.w , rectangle.y);
		this.bottomleft = new Vector2f(rectangle.x , rectangle.y + rectangle.h);
		this.bottomright = new Vector2f(rectangle.x + rectangle.w , rectangle.y + rectangle.h);
	}
	
	public Quad(Quad Q) {
		this.topleft = new Vector2f(Q.topleft);
		this.topright = new Vector2f(Q.topright);
		this.bottomleft = new Vector2f(Q.bottomleft);
		this.bottomright = new Vector2f(Q.bottomright);
	}
	
	public void move(Vector2f distance) {
		this.topleft.x += distance.x; this.topleft.y += distance.y;
		this.topright.x += distance.x; this.topright.y += distance.y;
		this.bottomleft.x += distance.x; this.bottomleft.y += distance.y;
		this.bottomright.x += distance.x; this.bottomright.y += distance.y;
	}
	
	public Quad offset(Vector2f distance) {
		Quad Q = new Quad(this);
		Q.move(distance);
		return Q;
	}
	
	public void setTopLeft(Vector2f position) {
		Vector2f trdiff = topright.subtract(topleft);
		Vector2f bldiff = bottomleft.subtract(topleft);
		Vector2f brdiff = bottomright.subtract(topleft);
		topleft = position;
		topright = position.add(trdiff);
		bottomleft = position.add(bldiff);
		bottomright = position.add(brdiff);
	}
	
	public void setTopRight(Vector2f position) {
		Vector2f tldiff = topleft.subtract(topright);
		Vector2f bldiff = bottomleft.subtract(topright);
		Vector2f brdiff = bottomright.subtract(topright);
		topright = position;
		topleft = position.add(tldiff);
		bottomleft = position.add(bldiff);
		bottomright = position.add(brdiff);
	}
	
	public void setBottomLeft(Vector2f position) {
		Vector2f tldiff = topleft.subtract(bottomleft);
		Vector2f trdiff = topright.subtract(bottomleft);
		Vector2f brdiff = bottomright.subtract(bottomleft);
		bottomleft = position;
		topleft = position.add(tldiff);
		topright = position.add(trdiff);
		bottomright = position.add(brdiff);
	}
	
	public void setBottomRight(Vector2f position) {
		Vector2f tldiff = topleft.subtract(bottomright);
		Vector2f trdiff = topright.subtract(bottomright);
		Vector2f bldiff = bottomleft.subtract(bottomright);
		bottomright = position;
		topleft = position.add(tldiff);
		topright = position.add(trdiff);
		bottomleft = position.add(bldiff);
	}
	
	public Quad scale(Vector2f pivot, float scalar) throws Exception {
		throw new Exception("Not implemented yet");
	}
	
	public Vector2f getCenter() {
		return topleft.add(topright.add(bottomleft.add(bottomright))).scale(1/4f);
	}
}
