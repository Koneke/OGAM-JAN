package lh.koneke.games.lwjglplatformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import lh.koneke.thomas.framework.Game;
import lh.koneke.thomas.framework.GameMouse;
import lh.koneke.thomas.framework.Graphics;
import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.TextureInformation;
import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.DrawingObject;
import lh.koneke.thomas.graphics.Frame;
import lh.koneke.thomas.graphics.SpriteSheet;
import lh.koneke.thomas.graphics.Texture2d;
import lh.koneke.thomas.gui.Button;
import lh.koneke.thomas.gui.ContextMenu;
import lh.koneke.thomas.gui.Font;
import lh.koneke.thomas.gui.Text;

public class LWJGLPlatformer extends Game {
	public static void main(String[] args) { LWJGLPlatformer game = new LWJGLPlatformer(); game.start(); }
	
	/*
	 * TODO:
	 * remove textSheet, switch to new rendering instead.
	 */
	
	
	Vector2f screenSize;
	float scale;
	
	List<Entity> testList;
	Random random;
	
	/* ~~~~~~~~~~ */
	
	Entity player;
	Entity binoculars;
	Entity bob;
	Entity ladder;
	Vector2f playerTarget; //spot to move to
	
	Texture2d levelBackground;
	Texture2d gridtexture;
	Vector2f tileSize;
	SpriteSheet tileSheet;
	SpriteSheet textSheet;
	
	DrawingObject d;
	
	Screen currentScreen;
	
	Button testButton;
	ContextMenu contextMenu;
	ContextMenu actionMenu;
	List<String> commands = new ArrayList<String>();
	Entity selectedEntity;
	boolean mouseFree = true;
	
	int hotswapFrequency = 1000; //time in ms for hotswap updating
	int hotswapTimer = 0;
	
	Font f;
	SpriteSheet font;

	Text text = new Text("Test");
	
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
		f.characterWidth = 5;
		f.characterHeight = 13;
		f.margin = 1;
		
		f.specialWidth.put('a', 4);
		f.specialWidth.put('g', 4);
		f.specialWidth.put('h', 4);
		f.specialWidth.put('I', 3);
		f.specialWidth.put('i', 1);
		f.specialWidth.put('J', 4);
		f.specialWidth.put('j', 3);
		f.specialWidth.put('K', 4);
		f.specialWidth.put('k', 3);
		f.specialWidth.put('l', 1);
		f.specialWidth.put('n', 4);
		f.specialWidth.put('o', 4);
		f.specialWidth.put('P', 4);
		f.specialWidth.put('p', 4);
		f.specialWidth.put('q', 4);
		f.specialWidth.put('R', 4);
		f.specialWidth.put('r', 2);
		f.specialWidth.put('s', 3);
		f.specialWidth.put('t', 3);
		f.specialWidth.put('u', 4);
		f.specialWidth.put('v', 3);
		f.specialWidth.put('X', 3);
		f.specialWidth.put('x', 3);
		f.specialWidth.put('Y', 4);
		f.specialWidth.put('y', 4);
		f.specialWidth.put('z', 3);
		
		tileSize = new Vector2f(32,32);
		tileSheet = new SpriteSheet(null, new Vector2f(32,32));
		textSheet = new SpriteSheet(null, new Vector2f(64,13));
		currentScreen = new Screen(10, 8, tileSheet, tileSize, levelBackground);
		
		loadScreen(currentScreen);
		
		player = new Entity("Player");
		player.quad = new Quad(new Rectangle(new Vector2f(16,160), tileSize));
		playerTarget = new Vector2f(player.quad.getCenter()); //where the player is moving towards
		
		player.logicalPosition = new Vector2f(
				(float)Math.floor(player.quad.topleft.intx()/tileSize.x),
				(float)Math.floor(player.quad.topleft.inty()/tileSize.y));
		player.currentTileSlot = currentScreen.map
				[player.logicalPosition.intx()]
				[player.logicalPosition.inty()];
		currentScreen.activeTiles.add(player.currentTileSlot);
		
		binoculars = new Entity("Binoculars");
		binoculars.nameTexture = new Frame(new Vector2f(64,13), textSheet);
		binoculars.quad = new Quad(new Rectangle(new Vector2f(6*32,3*32), tileSize));
		binoculars.logicalPosition = new Vector2f(6,3);
		binoculars.currentTileSlot = currentScreen.map
				[binoculars.logicalPosition.intx()]
				[binoculars.logicalPosition.inty()];
		binoculars.currentFrame = new Vector2f(0,96);
		binoculars.depth = -1;
		currentScreen.map[binoculars.logicalPosition.intx()][binoculars.logicalPosition.inty()].entities.add(binoculars);
		currentScreen.activeTiles.add(binoculars.currentTileSlot);
		binoculars.look = "You see binoculars.";
		
		bob = new Entity("Bob");
		bob.nameTexture = new Frame(new Vector2f(64,26), textSheet);
		bob.quad = new Quad(new Rectangle(new Vector2f(6*32,3*32), tileSize));
		bob.logicalPosition = new Vector2f(6,3);
		bob.currentTileSlot = currentScreen.map
				[bob.logicalPosition.intx()]
				[bob.logicalPosition.inty()];
		bob.currentFrame = new Vector2f(128,0);
		bob.depth = 1;
		currentScreen.map[bob.logicalPosition.intx()][bob.logicalPosition.inty()].entities.add(bob);
		currentScreen.activeTiles.add(bob.currentTileSlot);
		bob.look = "You see bob.";
		
		ladder = new Entity("Ladder");
		ladder.nameTexture = new Frame(new Vector2f(64,39), textSheet);
		ladder.quad = new Quad(new Rectangle(new Vector2f(6*32, 4*32), tileSize.scale(1, 2)));
		ladder.currentFrame = new Vector2f(0,0);
		ladder.depth = 1;
		currentScreen.map[6][4].entities.add(ladder);
		currentScreen.map[6][5].entities.add(ladder);
		ladder.look = "It's a ladder";
		
		contextMenu = new ContextMenu(new Vector2f(0,0), 66);
		contextMenu.setGraphics(new Colour(0.5f,0.5f,1,1));
		
		actionMenu = new ContextMenu(new Vector2f(0,0), 66);
		actionMenu.setGraphics(new Colour(0.5f,0.5f,1,1));
		
		commands.add("look");
		actionMenu.addItem(
			new Button(new Rectangle(new Vector2f(0,0), new Vector2f(64, 13)),
			new Frame(new Vector2f(0,0), textSheet)
		));
		
		d = new Colour(1,1,1,1);
	}
	
	public void load() {
		Graphics.setInterpolationMode("none");
		String path;
		Texture2d texture;
		
		path = "res/testsheet.png";
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) {
			player.spriteSheet = new SpriteSheet(texture, new Vector2f(32,32));
			player.spriteSheet.addFrameToAnimation("idle", new Vector2f(0,0), 250);
			player.spriteSheet.addFrameToAnimation("walking", new Vector2f(32,0), 250);
			player.spriteSheet.addFrameToAnimation("walking", new Vector2f(64,0), 250);
			player.spriteSheet.startAnimation("idle");
		} else { System.exit(0); }
		
		path = "res/testsheet2.png";
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) {
			tileSheet.setTexture(texture);
		} else { System.exit(0); }
		
		path = "res/textsheet.png";
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) {
			textSheet.setTexture(texture);
		} else { System.exit(0); }

		path = "res/font.png";
		texture = new Texture2d(Graphics.loadTexture(path), path);
		if(texture.getTexture() != null) {
			font.setTexture(texture);
		} else { System.exit(0); }

		binoculars.spriteSheet = tileSheet;
		bob.spriteSheet = tileSheet;
		
		path = "res/testbg2.png";
		levelBackground = new Texture2d(Graphics.loadTexture(path), path);
		if(levelBackground == null) { System.exit(0); }
		
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
		if(GameMouse.right && !GameMouse.prevRight) {
			contextMenu.setVisible(true);
			
			Vector2f v = getGridPosition(GameMouse.getPosition().scale(1f/scale));
			TileSlot tile = currentScreen.map[v.intx()][v.inty()];
			contextMenu.tile = tile;

			contextMenu.clear();
			int items = tile.entities.size();
			for(int i = 0; i< items; i++) {
				Button b = new Button(new Rectangle(
					new Vector2f(contextMenu.getShape().getPosition().add(new Vector2f(1, 0))), new Vector2f(contextMenu.getShape().w-2,13)),
					tile.entities.get(i).nameTexture
				);
				if (b.getGraphics() == null) {
					b.setGraphics(new Colour(1,1,1,1));
				}
				contextMenu.addItem(b);
			}
			//clear the menu, and add a button for each entity in the tile
		
			contextMenu.getShape().setPosition(GameMouse.getPosition().scale(1f/scale));
			float h = 2;
			for(Button b : contextMenu.getItems()) {
				b.getShape().x = contextMenu.getShape().x+1;
				b.getShape().y = contextMenu.getShape().y+h;
				h += b.getShape().h+2; //margin = 2
			}
			//shape and place the buttons
			
			contextMenu.getShape().h = h-2; //padding 4 on each side
		}
		
		if(GameMouse.left && !GameMouse.prevLeft) {
			if(contextMenu.getVisible()) {
				if(!contextMenu.getShape().containsPoint(GameMouse.getPosition().scale(1f/scale))) {
					contextMenu.setVisible(false);
					mouseFree = true;
				} else {
					mouseFree = false;
					for(Button b : contextMenu.getItems()) {
						if(b.getShape().containsPoint(GameMouse.getPosition().scale(1f/scale))) {
							//the button b in the menu was clicked, select that item
							selectedEntity = contextMenu.tile.entities.get(contextMenu.getItems().indexOf(b));
							
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
							actionMenu.getShape().h = h - 2;
							
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
							String command = commands.get(actionMenu.getItems().indexOf(b));
							
							switch(command) {
								case "look":
									System.out.println(selectedEntity.lookAt()); break;
								default:
									System.out.println("Uh, what?");
							}
							
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
				playerTarget.x = player.quad.topleft.x;
			}
		}
		
		if(Math.abs(player.quad.topleft.x - playerTarget.x) > 1) {
			player.spriteSheet.xflip = GameMouse.getPosition().scale(1f/scale).x < player.quad.topleft.x;
			
			int preMove = (int)((player.quad.topleft.x - player.quad.topleft.x % tileSize.x) / tileSize.x);
			player.quad.move(new Vector2f(
						((player.quad.topleft.x > playerTarget.x) ? -1 : 1)*dt*tileSize.x/250f, 0));
			int postMove = (int)((player.quad.topleft.x - player.quad.topleft.x % tileSize.x) / tileSize.x);
			if(postMove != preMove) {
				player.logicalPosition.x = (float)Math.floor(player.quad.topleft.intx()/tileSize.x);
				player.logicalPosition.y = (float)Math.floor(player.quad.topleft.inty()/tileSize.y);
				
				currentScreen.activeTiles.remove(player.currentTileSlot);
				player.currentTileSlot = currentScreen.map[postMove][player.logicalPosition.inty()];
				currentScreen.activeTiles.add(player.currentTileSlot);
			}
			
			if(player.spriteSheet.getAnimation() != "walking") {
				player.spriteSheet.startAnimation("walking");
			}
		} else {
			if(player.spriteSheet.getAnimation() != "idle") {
				player.spriteSheet.startAnimation("idle");
			}
		}
		
		player.lifetime += dt;
		hotswapTimer += dt;
		if (hotswapTimer > hotswapFrequency) {
			while (hotswapTimer > hotswapFrequency) {
				hotswapTimer -= hotswapFrequency;
			}
			
			for(TextureInformation ti : Texture2d.information) {
				ti.checkHotswap();
			}
			
			tileSheet.getTexture().checkHotswap();
			textSheet.getTexture().checkHotswap();
		}
	}
	
	public void draw() {
		List<DrawQuadCall> drawCommands = new ArrayList<DrawQuadCall>();
		
		drawCommands.add(new DrawQuadCall(
			levelBackground,
			null,
			new Quad(new Rectangle(new Vector2f(0,0), new Vector2f(512,256))),
			scale,
			10
		));
		
		for (int x = 0; x < currentScreen.map.length; x++) {
			for (int y = 0; y < currentScreen.map[0].length; y++) {
				for (Tile t : currentScreen.map[x][y].getTiles()) {
					Vector2f v = t.tile;
					Rectangle r = currentScreen.map[x][y].getSpriteSheet().getAt(v);
					if(t.xflip) r=r.xflip();
					if(t.yflip) r=r.yflip();
					
					drawCommands.add(new DrawQuadCall(
						currentScreen.map[x][y].getSpriteSheet().getTexture(),
						r,
						new Quad(
							new Rectangle(
								currentScreen.map[x][y].position.scale(
									currentScreen.tileSize.x, currentScreen.tileSize.y),
								currentScreen.tileSize
							)),
						scale,
						t.depth
					));
				}
			}
		}
		
		player.spriteSheet.Update(dt);
		
		float bump = 0; float bumpsPerSecond = 4;
		if(player.spriteSheet.getAnimation() == "walking") { bump = 5f; }
		
		drawCommands.add(new DrawQuadCall(
			player.spriteSheet.getTexture(),
			player.spriteSheet.getTexCoords(),
			player.quad.offset(player.quad.topright.
					subtract(player.quad.topleft).scale(-0.5f))
					.offset(new Vector2f(0, -(float)Math.abs(Math.sin(Math.toRadians((180/(1000f/bumpsPerSecond))*player.lifetime)))*bump)),
			scale,
			player.depth
		));
		
		drawCommands.add(new DrawQuadCall(
			binoculars.spriteSheet.getTexture(),
			binoculars.spriteSheet.getAt(binoculars.currentFrame),
			binoculars.quad,
			scale,
			binoculars.depth
		));
		
		drawCommands.add(new DrawQuadCall(
				bob.spriteSheet.getTexture(),
				bob.spriteSheet.getAt(binoculars.currentFrame),
				bob.quad,
				scale,
				bob.depth
			));
		
		if(contextMenu.getVisible()) {
			drawCommands.add(new DrawQuadCall(
				contextMenu.getGraphics(),
				null,
				new Quad(contextMenu.getShape()),
				scale,
				-10
			));

			for(Button b : contextMenu.getItems()) {
				drawCommands.add(new DrawQuadCall(
					b.getGraphics(),
					null,
					new Quad(b.getShape()),
					scale,
					-11
				));
			}
		}
		
		if(actionMenu.getVisible()) {
			drawCommands.add(new DrawQuadCall(
				actionMenu.getGraphics(),
				null,
				new Quad(actionMenu.getShape()),
				scale,
				-10
			));
			
			for(Button b : actionMenu.getItems()) {
				drawCommands.add(new DrawQuadCall(
					b.getGraphics(),
					null,
					new Quad(b.getShape()),
					scale,
					-12
				));
			}
		}
		
		for(DrawQuadCall dqc : Text.renderString("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOo", f, new Vector2f(0,0), scale).calls) {
			drawCommands.add(dqc);
		}
		
		Collections.sort(drawCommands, new Comparator<DrawQuadCall>() {
			public int compare(DrawQuadCall a, DrawQuadCall b) {
				if (a.depth < b.depth) return -1;
				if (a.depth > b.depth) return 1;
				return 0;
			}
		});
		Collections.reverse(drawCommands);
		
		for(DrawQuadCall dqc : drawCommands) {
			drawQuad(dqc.d, dqc.source, dqc.q, dqc.scale);
		}
	}
}
