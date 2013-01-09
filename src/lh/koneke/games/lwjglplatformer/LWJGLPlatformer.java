package lh.koneke.games.lwjglplatformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import lh.koneke.thomas.framework.EntityManager;
import lh.koneke.thomas.framework.Font;
import lh.koneke.thomas.framework.Game;
import lh.koneke.thomas.framework.GameMouse;
import lh.koneke.thomas.framework.Graphics;
import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.SpriteSheet;
import lh.koneke.thomas.graphics.Texture2d;
import lh.koneke.thomas.graphics.TextureInformation;
import lh.koneke.thomas.gui.Button;
import lh.koneke.thomas.gui.ContextMenu;
import lh.koneke.thomas.gui.Text;

public class LWJGLPlatformer extends Game {
	public static void main(String[] args) { LWJGLPlatformer game = new LWJGLPlatformer(); game.start(); }
	
	Vector2f screenSize;
	float scale;
	
	Random random;
	
	/* ~~~~~~~~~~ */
	
	EntityManager em;
	Vector2f playerTarget; //spot to move to
	
	Texture2d levelBackground;
	Vector2f tileSize;
	SpriteSheet tileSheet;
	
	Screen currentScreen;
	
	ContextMenu contextMenu;
	ContextMenu actionMenu;
	List<String> commands = new ArrayList<String>();
	Entity selectedEntity;
	boolean mouseFree = true;
	
	int hotswapFrequency = 1000; //time in ms for hotswap updating
	int hotswapTimer = 0;
	
	Font f;
	SpriteSheet font;
	
	String[] console;
	
	/* ~~~~~~~~~~ */
	
	public void sysInit() {
		screenSize = new Vector2f(320, 256);
		scale = 3f;
		setDisplay((int)(screenSize.x * scale), (int)(screenSize.y * scale));
		random = new Random();
	}
	
	public void initialize() {
		sysInit();
		font = new SpriteSheet(null, new Vector2f(0,0));
		
		f = new Font();
		f.sheet = font;
		
		console = new String[3];
		for(int i = 0; i < 3; i++){
			console[i] = "";
		}

		tileSize = new Vector2f(32,32);
		tileSheet = new SpriteSheet(null, new Vector2f(32,32));
		currentScreen = new Screen(10, 8, tileSheet, tileSize, levelBackground);
		
		loadScreen(currentScreen);

		sm = new SoundManager();
		em = new EntityManager(currentScreen);
		
		f.load("res/font.thf");
		
		em.load("res/entities.the");
		em.getEntity("Binoculars").currentFrame = new Vector2f(0, 96); //todo: rm
		em.getEntity("Ladder").currentFrame = new Vector2f(0, 0); //todo: rm
		
		playerTarget = new Vector2f(em.getEntity("player").quad.getCenter()); //where the player is moving towards
		
		contextMenu = new ContextMenu(new Vector2f(0,0), 68);
		contextMenu.setGraphics(new Colour(0.3f,0.3f,0.3f,1));
		
		actionMenu = new ContextMenu(new Vector2f(0,0), 68);
		actionMenu.setGraphics(new Colour(0.3f,0.3f,0.3f,1));
		
		commands.add("Look at");
		actionMenu.addItem(
			new Button(new Rectangle(new Vector2f(0, 0), new Vector2f(66, 15)),
			null
		));
	}
	
	public void load() {
		Graphics.setInterpolationMode("none");
		String path;
		Texture2d texture;
		
		path = "res/testsheet.png";
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) {
			em.getEntity("player").spriteSheet = new SpriteSheet(texture, new Vector2f(32,32));
			em.getEntity("player").am.load("res/player.tha");
			em.getEntity("player").am.startAnimation("idle");
		} else { System.exit(0); }
		
		path = "res/testsheet2.png";
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) { tileSheet.setTexture(texture); } else { System.exit(0); }
		
		path = f.getPath();
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) { font.setTexture(texture); } else { System.exit(0); }

		em.getEntity("Binoculars").spriteSheet = tileSheet;	//Todo: integrate into entities system
		em.getEntity("Binoculars").am.addFrameToAnimation("idle", new Vector2f(0, 96), -1);
		
		em.getEntity("Ladder").spriteSheet = tileSheet;	//Todo: integrate into entities system
		em.getEntity("Binoculars").am.addFrameToAnimation("idle", new Vector2f(0, 0), -1);
		
		
		path = "res/testbg2.png";
		levelBackground = new Texture2d(Graphics.loadTexture(path), path);
		if(levelBackground == null) { System.exit(0); }
		
		/* --- audio --- */
		
		Game.sm.load("res/Randomize3.wav"); //is player with sm.play("Randomize3");
		
	}
	
	public void loadScreen(/*String path,*/Screen screen) {
		//load from file in the future
		
		screen.map[7][2].addTile(new Tile(new Vector2f(0,0), true));
		screen.map[5][2].addTile(new Tile(new Vector2f(0,0)));
		screen.map[6][2].addTile(new Tile(new Vector2f(32,0)));
		
		screen.map[5][3].addTile(new Tile(new Vector2f(0,32)));
		screen.map[7][3].addTile(new Tile(new Vector2f(0,32), true));
		screen.map[6][3].addTile(new Tile(new Vector2f(64,0),2)); //backpart of watchtower at depth 2
		screen.map[6][3].addTile(new Tile(new Vector2f(32,32)));
		
		screen.map[5][4].addTile(new Tile(new Vector2f(64,96)));
		screen.map[6][4].addTile(new Tile(new Vector2f(32,64)));
		screen.map[7][4].addTile(new Tile(new Vector2f(64,32)));
		
		screen.map[5][5].addTile(new Tile(new Vector2f(0,64)));
		screen.map[6][5].addTile(new Tile(new Vector2f(32,96)));
		screen.map[7][5].addTile(new Tile(new Vector2f(64,64)));
		
		for(int x = 0; x < screen.map.length-3; x++) {
			screen.map[x][7].addTile(new Tile(new Vector2f(96,96)));
			screen.map[x][6].addTile(new Tile(new Vector2f(96,64)));
			screen.map[x][5].addTile(new Tile(new Vector2f(random.nextInt(4)*32,128)));
		}
		
		screen.map[7][6].addTile(new Tile(new Vector2f(128,64)));
		screen.map[7][7].addTile(new Tile(new Vector2f(96,96)));
		screen.map[8][7].addTile(new Tile(new Vector2f(160,64)));
		screen.map[8][6].addTile(new Tile(new Vector2f(random.nextInt(2)*32,160)));
	}
	
	public Vector2f getGridPosition(Vector2f position) {
		Vector2f v = new Vector2f(position);
		v.x -= v.x % tileSize.x;
		v.y -= v.y % tileSize.y;
		v = v.scale(1f/tileSize.x, 1f/tileSize.y);
		return v;
	}
	
	public void update() {
		/* 
		 * TODO:
		 * clean menu stuff
		 */
		if(GameMouse.right && !GameMouse.prevRight) {
			actionMenu.setVisible(false);
			contextMenu.setVisible(true);
			
			//get tile clicked
			Vector2f v = getGridPosition(GameMouse.getPosition().scale(1f/scale));
			TileSlot tile = currentScreen.getAt(v);
			contextMenu.tile = tile;

			//clear the menu, and add a button for each entity in the tile
			contextMenu.clear();
			int items = tile.entities.size();
			for(int i = 0; i< items; i++) {
				Button b = new Button(new Rectangle(
					new Vector2f(contextMenu.getShape().getPosition().add(new Vector2f(1, 0))), new Vector2f(contextMenu.getShape().w-2,
						f.characterHeight+2)),
					null
				);
				if (b.getGraphics() == null) {
					b.setGraphics(new Colour(1,1,1,1));
				}
				contextMenu.addItem(b);
			}

			//shape and place the buttons
			contextMenu.getShape().setPosition(GameMouse.getPosition().scale(1f/scale));
			float h = 2;
			for(Button b : contextMenu.getItems()) {
				b.getShape().x = contextMenu.getShape().x+1;
				b.getShape().y = contextMenu.getShape().y+h;
				h += b.getShape().h+2; //margin = 2
			}
			
			contextMenu.getShape().h = h;
		}
		
		if(GameMouse.left && !GameMouse.prevLeft) {
			if(contextMenu.getVisible()) {
				if(!contextMenu.getShape().containsPoint(GameMouse.getPosition().scale(1f/scale))) {
					//clicked somewhere else, close and hide menu
					contextMenu.setVisible(false);
					mouseFree = true;
				} else {
					//menu has been clicked, lock the mouse so we can navigate it without moving the player
					mouseFree = false;
					
					for(Button b : contextMenu.getItems()) {
						if(b.getShape().containsPoint(GameMouse.getPosition().scale(1f/scale))) {
							//the button b in the menu was clicked, select that item
							selectedEntity = contextMenu.tile.entities.get(contextMenu.getItems().indexOf(b));
							
							//this menu is done, switch to the next one
							contextMenu.setVisible(false);
							actionMenu.getShape().setPosition(contextMenu.getShape().getPosition());
							actionMenu.getShape().h = 4;
							actionMenu.setVisible(true);
							
							float h = 2;
							for(Button bb : actionMenu.getItems()) {
								bb.getShape().x = actionMenu.getShape().x+1;
								bb.getShape().y = actionMenu.getShape().y+h;
								h += bb.getShape().h+2; //margin = 2
							}
							//place and shape the buttons
							actionMenu.getShape().h = h;
							
							break;
						}
					}
				}
			} else if(actionMenu.getVisible()) {
				if(!contextMenu.getShape().containsPoint(GameMouse.getPosition().scale(1f/scale))) {
					actionMenu.setVisible(false);
					mouseFree = true;
				} else {
					mouseFree = false;
					
					for(Button b : actionMenu.getItems()) {
						if(b.getShape().containsPoint(GameMouse.getPosition().scale(1f/scale))) {
							//get command
							String command = commands.get(actionMenu.getItems().indexOf(b));
							
							//handle command
							switch(command) {
								case "Look at":
									console[0] = console[1];
									console[1] = console[2];
									console[2] = selectedEntity.getLook();
									break;
								default:
									console[0] = console[1];
									console[1] = console[2];
									console[2] = "Uh, what?";
							}
							
							//action menu is done
							actionMenu.setVisible(false);
							break;
						}
					}
				}
			} else {
				mouseFree = true;
			}
		}
		
		
		if(mouseFree) {
			if(GameMouse.left) {
				playerTarget.x = GameMouse.getPosition().scale(1f/scale).x;
			} else {
				//if the mouse is not free, set our current position as target
				playerTarget.x = em.getEntity("player").quad.topleft.x;
			}
		}
		
		if(Math.abs(em.getEntity("player").quad.topleft.x - playerTarget.x) > 1) {
			em.getEntity("player").spriteSheet.xflip = GameMouse.getPosition().scale(1f/scale).x < em.getEntity("player").quad.topleft.x;
			
			Vector2f preMove = getGridPosition(em.getEntity("player").quad.topleft);
			em.getEntity("player").quad.move(new Vector2f(((em.getEntity("player").quad.topleft.x > playerTarget.x) ? -1 : 1)*dt*tileSize.x/250f, 0));
			Vector2f postMove = getGridPosition(em.getEntity("player").quad.topleft);
			
			if(preMove.x != postMove.x || preMove.y != postMove.y) {
				em.getEntity("player").logicalPosition = getGridPosition(em.getEntity("player").quad.topleft);
				
				currentScreen.getActiveTiles().remove(em.getEntity("player").currentTileSlot);
				currentScreen.getAt(preMove).entities.remove(em.getEntity("player"));
				
				em.getEntity("player").currentTileSlot = currentScreen.getAt(postMove);
				
				currentScreen.getActiveTiles().add(em.getEntity("player").currentTileSlot);
				currentScreen.getAt(postMove).entities.add(em.getEntity("player"));
			}
			
			if(em.getEntity("player").am.getAnimation() != "walking") {
				em.getEntity("player").am.startAnimation("walking");
			}
		} else {
			if(em.getEntity("player").am.getAnimation() != "idle") {
				em.getEntity("player").am.startAnimation("idle");
			}
		}
		
		em.getEntity("player").lifetime += dt;
		hotswapTimer += dt;
		if (hotswapTimer > hotswapFrequency) {
			while (hotswapTimer > hotswapFrequency) {
				hotswapTimer -= hotswapFrequency;
			}
			
			for(TextureInformation ti : Texture2d.information) {
				ti.checkHotswap();
			}
			
			tileSheet.getTexture().checkHotswap();
			font.getTexture().checkHotswap();
		}
		
		
	}
	
	public void draw() {
		List<DrawQuadCall> drawCommands = new ArrayList<DrawQuadCall>();
		
		drawCommands.add(new DrawQuadCall(
			levelBackground, null, null,
			new Quad(new Rectangle(new Vector2f(0,0), new Vector2f(512,256))),
			scale, 10, null
		));
		
		/*
		for(TileSlot t : currentScreen.activeTiles) {
			drawCommands.add(new DrawQuadCall(
				new Colour(1,0,0,1),
				null,
				new Quad(new Rectangle(t.position.scale(tileSize.x, tileSize.y), tileSize)),
				scale,
				3
			));
		}*/
		//debugging, show active tiles
		
		for (int x = 0; x < currentScreen.map.length; x++) {
			for (int y = 0; y < currentScreen.map[0].length; y++) {
				for (Tile t : currentScreen.map[x][y].getTiles()) {
					Vector2f v = t.tile;
					Rectangle r = currentScreen.map[x][y].getSpriteSheet().getAt(v);
					
					if(t.xflip) r=r.xflip();
					if(t.yflip) r=r.yflip();
					
					drawCommands.add(new DrawQuadCall(
						currentScreen.map[x][y].getSpriteSheet().getTexture(), null, r,
						new Quad(new Rectangle(
							currentScreen.map[x][y].position.scale(
									currentScreen.tileSize.x,
									currentScreen.tileSize.y),
							currentScreen.tileSize)),
						scale, t.depth, null));
				}
			}
		}
		
		em.getEntity("player").am.Update(dt);
		
		float bump = 0; float bumpsPerSecond = 4;
		if(em.getEntity("player").am.getAnimation() == "walking") { bump = 5f; }
		
		drawCommands.add(new DrawQuadCall(
			em.getEntity("player").spriteSheet.getTexture(),
			em.getEntity("player").am,
			em.getEntity("player").spriteSheet.getTexCoords(em.getEntity("player").am),
			em.getEntity("player").quad.offset(em.getEntity("player").quad.topright.
					subtract(em.getEntity("player").quad.topleft).scale(-0.5f))
					.offset(new Vector2f(0, -(float)Math.abs(Math.sin(Math.toRadians((180/(1000f/bumpsPerSecond))*em.getEntity("player").lifetime)))*bump)),
			scale, em.getEntity("player").depth, null));
		

		for(Entity e : em.getEntities().values()) {
			if(!e.name.equals("player")) {
			drawCommands.add(new DrawQuadCall(
				tileSheet, e.am, e.spriteSheet.getAt(e.currentFrame),
				e.quad, scale, e.depth, null));
			}
		}
		
		if(contextMenu.getVisible()) {
			drawCommands.add(new DrawQuadCall(
				contextMenu.getGraphics(), null, null,
				new Quad(contextMenu.getShape()), scale, -10, null));
			
			for(Button b : contextMenu.getItems()) {
				drawCommands.add(new DrawQuadCall(
					new Colour(0.5f,0.5f,0.5f,1), null, null,
					new Quad(b.getShape()), scale, -11, null));
				drawCommands.addAll(Text.renderString(
					contextMenu.tile.entities.get(contextMenu.getItems().indexOf(b)).name,
					f, b.getShape().getPosition().add(new Vector2f(1,1)), scale, -12, null).calls);
			}
		}
		
		if(actionMenu.getVisible()) {
			drawCommands.add(new DrawQuadCall(
				actionMenu.getGraphics(), null, null,
				new Quad(actionMenu.getShape()), scale, -10, null));
			
			for(Button b : actionMenu.getItems()) {
				drawCommands.add(new DrawQuadCall(
					new Colour(0.5f,0.5f,0.5f,1), null, null,
					new Quad(b.getShape()), scale, -11, null));
				drawCommands.addAll(Text.renderString(
					commands.get(actionMenu.getItems().indexOf(b)),
					f, b.getShape().getPosition().add(new Vector2f(1,1)), scale, -12, null).calls);
			}
		}
		
		/*drawCommands.addAll(Text.renderString(
				"ABCDEFGHIJKLMNOPQRSTUWXYZabcdefghijklmnopqrstuwxyz",
				f, new Vector2f(0,0), scale, -10, new Colour(1,0,0,1)).calls);*/
		//debugging, draw alphabet
		
		for(int i = 0;i<3;i++) {
			drawCommands.addAll(Text.renderString(
				console[i], f, new Vector2f(2,f.characterHeight*i), scale, -10, new Colour(0,0,0,1)).calls);
		}
		
		/*Sort and run calls*/
		
		Collections.sort(drawCommands, new Comparator<DrawQuadCall>() {
			public int compare(DrawQuadCall a, DrawQuadCall b) {
				if (a.depth < b.depth) return -1;
				if (a.depth > b.depth) return 1;
				return 0; }});
		Collections.reverse(drawCommands);
		
		for(DrawQuadCall dqc : drawCommands) {
			if(dqc.c != null) {
				GL11.glColor4f(dqc.c.getRed(), dqc.c.getGreen(), dqc.c.getBlue(), dqc.c.getAlpha());
			}
			drawQuad(dqc.d, dqc.am, dqc.source, dqc.q, dqc.scale);
		}
	}
}
