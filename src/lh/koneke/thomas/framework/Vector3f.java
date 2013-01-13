package lh.koneke.thomas.framework;

public class Vector3f extends Vector {
	public float x, y, z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
	}
	
	public Vector3f(Vector3f position) {
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
	}
	
	public Vector3f scale(float scalar) {
		return new Vector3f(
			this.x*scalar,
			this.y*scalar,
			this.z*scalar);
	}
	
	public Vector3f scale(float xscalar, float yscalar, float zscalar) {
		return new Vector3f(this.x*xscalar, this.y*yscalar, this.z*zscalar);
	}
	
	public Vector3f subtract(Vector3f otherVector) {
		return new Vector3f(
			this.x - otherVector.x,
			this.y - otherVector.y,
			this.z - otherVector.z);
	}
	
	public Vector3f add(Vector3f otherVector) {
		return new Vector3f(
			this.x + otherVector.x,
			this.y + otherVector.y,
			this.z + otherVector.z);
	}
	
	public void setx(float x) {
		this.x = x;
	}
	public void sety(float y) {
		this.y = y;
	}
	public void setz(float z) {
		this.z = z;
	}
	
	public float getx() {
		return x;
	}
	public float gety() {
		return y;
	}
	public float getz() {
		return z;
	}
	
	public int intx() {
		return (int) x;
	}
	public int inty() {
		return (int) y;
	}
	public int intz() {
		return (int) z;
	}
}
