package lh.koneke.thomas.framework;

import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.DrawingObject;
import lh.koneke.thomas.graphics.SpriteSheet;
import lh.koneke.thomas.graphics.Texture2d;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureImpl;

public class Game {
	
	public int dt;
	public Vector2f mousePosition;
	
	public Time t;
	
	public void setDisplay(int w, int h) {
		if (Display.isCreated()) Display.destroy();
		try {
			Display.setDisplayMode(new DisplayMode(w,h));
			Display.create();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, w, h, 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glClearColor(0.5f, 0.5f, 1f, 1f);
		}
		catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	/*
	public Texture loadTexture(String path) {
		return loadTexture(path, "PNG", false);
	}
	
	public Texture loadTexture(String path, boolean hotswap) {
		return loadTexture(path, "PNG", hotswap);
	}
	
	public Texture loadTexture(String path, String format, boolean hotswap) {
		try {
			Texture t = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(path));
			if(interpolationMode == "none") {
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			}
			
			if(!hotswap) {
				if (Texture2d.loaded(path, t)) { //if the texture wasn't loaded already, return it, else, null
					return t;
				}
				else { 
					return null;
				}
			} else {
				return t;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	/*public void drawQuad(DrawingObject d, Quad Q, float Scale) {
		if(d instanceof Texture2d) { drawQuad((Texture2d)d, Q, Scale); }
		if(d instanceof Colour) { drawQuad((Colour)d, Q, Scale); }
		if(d instanceof SpriteSheet) {
				SpriteSheet s = (SpriteSheet)d;
				drawQuad(s.getTexture(), s.getTexCoords(), Q, Scale);
			}
		//drawQuad(d, Q);
	}*/
	/*
	public void drawQuad(Texture2d texture, Quad Q) {
		texture.Bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2d(Q.topleft.x, Q.topleft.y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2d(Q.topright.x, Q.topright.y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2d(Q.bottomright.x, Q.bottomright.y);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2d(Q.bottomleft.x, Q.bottomleft.y);
		GL11.glEnd();
	}*/
	
	public void drawQuad(DrawingObject d, Rectangle source, Quad Q, Float scale) {
		if(d instanceof Texture2d) {
			Texture2d texture = (Texture2d)d;
			texture.Bind();
		}
		if(d instanceof Colour) {
			Colour C = (Colour)d;
			TextureImpl.bindNone();
			GL11.glColor4f(C.getRed(), C.getGreen(), C.getBlue(), C.getAlpha());
		}
		if(d instanceof SpriteSheet) {
			SpriteSheet s = (SpriteSheet)d;
			s.getTexture().Bind();
			source = new Rectangle(s.getTexCoords());
		}
		
		float Scale;
		if (scale == null) { 
			Scale = 1;
		} else {
			Scale = scale;
		}
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(
				(source == null ? 0 : source.x),
				(source == null ? 0 : source.y)
			);
			GL11.glVertex2f(Q.topleft.x * Scale, Q.topleft.y * Scale);
			GL11.glTexCoord2f(
				(source == null ? 1 : (source.x+source.w)),
				(source == null ? 0 : source.y)
			);
			GL11.glVertex2f(Q.topright.x * Scale, Q.topright.y * Scale);
			GL11.glTexCoord2f(
				(source == null ? 1 : (source.x+source.w)),
				(source == null ? 1 : (source.y+source.h))
			);
			GL11.glVertex2f(Q.bottomright.x * Scale, Q.bottomright.y * Scale);
			GL11.glTexCoord2f(
				(source == null ? 0 : source.x),
				(source == null ? 1 : (source.y+source.h))
			);
			GL11.glVertex2f(Q.bottomleft.x * Scale, Q.bottomleft.y * Scale);
		GL11.glEnd();
		GL11.glColor4f(1,1,1,1);
	}
	/*
	public void drawQuad(Texture2d texture, Quad Q, float Scale) {
		texture.Bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(Q.topleft.x * Scale, Q.topleft.y * Scale);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(Q.topright.x * Scale, Q.topright.y * Scale);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(Q.bottomright.x * Scale, Q.bottomright.y * Scale);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(Q.bottomleft.x * Scale, Q.bottomleft.y * Scale);
		GL11.glEnd();
	}
	
	public void drawQuad(Texture2d texture, Rectangle source, Quad Q, float Scale) {
		texture.Bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(source.x, source.y);
			GL11.glVertex2f(Q.topleft.x * Scale, Q.topleft.y * Scale);
			GL11.glTexCoord2f(source.x+source.w, source.y);
			GL11.glVertex2f(Q.topright.x * Scale, Q.topright.y * Scale);
			GL11.glTexCoord2f(source.x+source.w, source.y+source.h);
			GL11.glVertex2f(Q.bottomright.x * Scale, Q.bottomright.y * Scale);
			GL11.glTexCoord2f(source.x, source.y+source.h);
			GL11.glVertex2f(Q.bottomleft.x * Scale, Q.bottomleft.y * Scale);
		GL11.glEnd();
	}
	
	public void drawQuad(Colour C, Quad Q) {
		TextureImpl.bindNone();
		GL11.glColor4f(C.getRed(), C.getGreen(), C.getBlue(), C.getAlpha());
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(Q.topleft.x, Q.topleft.y);
			GL11.glVertex2f(Q.topright.x, Q.topright.y);
			GL11.glVertex2f(Q.bottomright.x, Q.bottomright.y);
			GL11.glVertex2f(Q.bottomleft.x, Q.bottomleft.y);
		GL11.glEnd();
		
		GL11.glColor4f(1,1,1,1);
	}
	
	public void drawQuad(Colour c, Quad q, float scale) {
		TextureImpl.bindNone();
		GL11.glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(q.topleft.x * scale, q.topleft.y * scale);
			GL11.glVertex2f(q.topright.x * scale, q.topright.y * scale);
			GL11.glVertex2f(q.bottomright.x * scale, q.bottomright.y * scale);
			GL11.glVertex2f(q.bottomleft.x * scale, q.bottomleft.y * scale);
		GL11.glEnd();
		
		GL11.glColor4f(1,1,1,1);
	}*/
	
	public void start() {
		preInitialize();
		initialize();
		postInitialize();
		
		preLoad();
		load();
		postLoad();
		
		while(!Display.isCloseRequested()) {
			preUpdate();
			update();
			postUpdate();
			
			preDraw();
			draw();
			postDraw();
		}
		
		preUnload();
		unload();
		postUnload();
		
		Display.destroy();
	}
	
	private void preInitialize() {
		setDisplay(800, 600);
		t = new Time();
		//lastFrame = getTime();
		/*
		GameMouse.setx(0);
		GameMouse.sety(0);*/
		
		System.out.println("pre init OK");
	}
	
	public void initialize() { } //to be overridden
	private void postInitialize() {
		System.out.println("post init OK");
	}
	
	private void preLoad() {
		System.out.println("pre load OK");
	}
	public void load() {} //to be overridden
	private void postLoad() {
		System.out.println("post load OK");
	}
	
	private void preUpdate() {
		dt = t.getDT();
		GameMouse.setx(Mouse.getX());
		GameMouse.sety(Display.getHeight()-Mouse.getY());
		GameMouse.left = Mouse.isButtonDown(0);
		GameMouse.right = Mouse.isButtonDown(1);
	}
	public void update() {} //to be overridden
	private void postUpdate() {
		GameMouse.prevLeft = GameMouse.left;
		GameMouse.prevRight = GameMouse.right;
		GameMouse.prevWheel = GameMouse.wheel;
		Display.update();
	}
	
	private void preDraw() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	public void draw() {} //to be overridden
	private void postDraw() {}
	
	private void preUnload() {
		System.out.println("pre unload OK");
	}
	public void unload() {} //to be overridden
	private void postUnload() {
		System.out.println("post unload OK");
	}
}
