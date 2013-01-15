package lh.koneke.thomas.framework.geometry;

public class Vector2f extends Vector {
	public float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f position) {
		this.x = position.x;
		this.y = position.y;
	}
	
	public Vector2f scale(float scalar) {
		return new Vector2f(this.x*scalar, this.y*scalar);
	}
	
	public Vector2f scale(float xscalar, float yscalar) {
		return new Vector2f(this.x*xscalar, this.y*yscalar);
	}
	
	public Vector2f scale(Vector2f scalar) {
		return new Vector2f(this.x*scalar.x, this.y*scalar.y);
	}
	
	public Vector2f subtract(Vector2f otherVector) {
		return new Vector2f(this.x - otherVector.x, this.y - otherVector.y);
	}
	
	public Vector2f add(Vector2f otherVector) {
		return new Vector2f(this.x + otherVector.x, this.y + otherVector.y);
	}
	
	public void setx(float x) {
		this.x = x;
	}
	public void sety(float y) {
		this.y = y;
	}
	
	public float getx() {
		return x;
	}
	public float gety() {
		return y;
	}
	
	public int intx() {
		return (int) x;
	}
	public int inty() {
		return (int) y;
	}
}
