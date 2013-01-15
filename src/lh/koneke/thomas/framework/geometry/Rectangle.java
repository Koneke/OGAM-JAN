package lh.koneke.thomas.framework.geometry;

import lh.koneke.thomas.framework.Shape;


public class Rectangle implements Shape {
	public float x;
	public float y;
	public float w;
	public float h;
	
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Rectangle(Rectangle rectangle) {
		this.x = rectangle.x;
		this.y = rectangle.y;
		this.w = rectangle.w;
		this.h = rectangle.h;
	}
	
	public Rectangle(Vector2f position, Vector2f size) {
		if(position != null) {
			this.x = position.x;
			this.y = position.y;
		}
		if(size != null) {
			this.w = size.x;
			this.h = size.y;
		}
	}
	
	public Rectangle scaleSize(float xscale, float yscale) {
		Rectangle r = new Rectangle(this);
		r.w = r.w*xscale;
		r.h = r.h*yscale;
		return r;
	}
	
	public Rectangle scale(float xscale, float yscale) {
		Rectangle r = new Rectangle(this);
		r.x = r.x*xscale;
		r.y = r.y*yscale;
		r.w = r.w*xscale;
		r.h = r.h*yscale;
		return r;
	}
	
	public Rectangle add(float w, float h) {
		Rectangle r = new Rectangle(this);
		r.w += w;
		r.h += h;
		return r;
	}
	
	public Rectangle offset(int x, int y) {
		Rectangle r = new Rectangle(this);
		r.x -= x;
		r.y -= y;
		return r;
	}
	
	public Rectangle flip() {
		return new Rectangle(
			x+w, y+h, -w, -h
		);
	}
	
	public Rectangle xflip() {
		return new Rectangle(
			x+w, y, -w, h
		);
	}
	
	public Rectangle yflip() {
		return new Rectangle(
			x, y+h, w, -h
		);
	}
	
	public boolean containsPoint(Vector2f point) {
		return point.x > x && point.x < x+w && point.y > y && point.y < y+h;
	}
	
	public Vector2f getPosition() {
		return new Vector2f(x,y);
	}
	
	public void setPosition(Vector2f position) {
		x = position.x;
		y = position.y;
	}
	
	public Vector2f getSize() {
		return new Vector2f(w,h);
	}
	
	public void setSize(int w, int h) {
		this.setSize(w, h);
	}
}
