package lh.koneke.thomas.framework;

import java.util.Random;

import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.DrawingObject;
import lh.koneke.thomas.graphics.Frame;
import lh.koneke.thomas.graphics.Texture2d;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.TextureImpl;

public class Game {
	protected TextureManager tm;
	public int dt;
	public Vector2f mousePosition;

	public static Random random = new Random();
	public static boolean verboseLogging = false;
	
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
	
	public void drawQuad(DrawingObject d, AnimationManager am, Rectangle source, Quad Q) {
		if(d instanceof Texture2d) {
			Texture2d texture = (Texture2d)d;
			texture.Bind();
		}
		if(d instanceof Colour) {
			Colour C = (Colour)d;
			TextureImpl.bindNone();
			GL11.glColor4f(C.getRed(), C.getGreen(), C.getBlue(), C.getAlpha());
		}
		if(d instanceof Frame) {
			Frame f = (Frame)d;
			if(!f.visible) {
				return;
			}
			
			if(f.getTexture() == null) {
				f.setTexture(tm.load(f.getPath()));
			}
			
			f.getTexture().Bind();
			if(am != null) {
				source = new Rectangle(f.getTexCoord(f.getTexture(), am));
			}
		}

		float Scale = Graphics.scale;
		
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
	
	public static void die() {
		System.out.println();
		System.out.println("SOMETHING WENT SOUR, DYING");
		System.out.println();
		AL.destroy();
		Display.destroy();
		System.exit(0);
	}
	
	private void preInitialize() {
		setDisplay(800, 600);
		t = new Time();
		
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
		GameMouse.wheel = Mouse.isButtonDown(2);
	}
	public void update() {} //to be overridden
	private void postUpdate() {
		GameMouse.prevLeft = GameMouse.left;
		GameMouse.prevRight = GameMouse.right;
		GameMouse.prevWheel = GameMouse.wheel;
		Display.update();
		SoundStore.get().poll(0);
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
		AL.destroy();
		System.out.println("post unload OK");
	}
}
