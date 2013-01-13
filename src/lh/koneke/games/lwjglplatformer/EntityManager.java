package lh.koneke.games.lwjglplatformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lh.koneke.thomas.framework.Game;
import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.Vector2f;


/* entity
 * :name
 * look
 * x,y,depth,w,h
 * texture
 * .tha
 */

public class EntityManager {
	Map<String, Entity> entities;
	Map<String, Entity> entityPrototypes;
	Screen screen;
	
	public EntityManager(Screen screen) {
		entities = new HashMap<String, Entity>();
		entityPrototypes = new HashMap<String, Entity>();
		this.screen = screen;
	}
	
	public void switchScreen(Screen screen) {
		this.screen = screen;
	}
	
	public void load(String path) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
			String name;
			String prototype = "";
			int x, y, z, w, h;
			Entity currentEntity = null;
			
			String s;
			int line = 0;
			while((s = br.readLine()) != null) {
				if(s.substring(0,1).equals(":")) {
					name = s.substring(1, s.length());
					currentEntity = new Entity(name);
					line = 1;
					prototype = "";
				}
				else {
					prototype = prototype.concat(prototype.equals("") ? s : "\n"+s);
					if(line == 1) {
						currentEntity.setLook(s);
						line+=1;
					} else if(line == 2) {
						String[] coordinates = s.split(",");
						x = Integer.parseInt(coordinates[0]);
						y = Integer.parseInt(coordinates[1]);
						z = Integer.parseInt(coordinates[2]);
						w = Integer.parseInt(coordinates[3]);
						h = Integer.parseInt(coordinates[4]);
						
						currentEntity.logicalPosition = new Vector2f(x,y);
						currentEntity.depth = z;
						currentEntity.size = new Vector2f(w,h);

						line = 3;
					} else if(line == 3) {
						currentEntity.texturePath = s;
						line = 4;
					} else if(line == 4) {
						currentEntity.thaPath = s;
						line = 5;
					}
					if(line == 5) {
						entities.put(currentEntity.getName().toLowerCase(), currentEntity);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Game.die();
		} finally {
			//spawn entities
			for (Entity e : entities.values()) {
				e.quad = new Quad(
					new Rectangle(e.logicalPosition.scale(screen.getTileSize().x,
						screen.getTileSize().y), screen.getTileSize()));
				
				e.currentTileSlot = screen.getAt(e.logicalPosition);
				for(int x = 0;x<e.size.x;x++) {
					for(int y = 0;y<e.size.y;y++) {
						screen.getActiveTiles().add(screen.getAt(e.logicalPosition.add(new Vector2f(x,y))));
						screen.getAt(e.logicalPosition.add(new Vector2f(x,y))).entities.add(e);
					}
				}
				
				entityPrototypes.put(e.getName().toLowerCase(), e);
			}
		}
	}
	
	public Entity getEntity(String name) {
		return entities.get(name.toLowerCase());
	}

	public Map<String, Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(String name, Entity e) {
		entities.put(name, e);
	}
	
	public void removeEntity(String name) {
		for(TileSlot t : screen.getActiveTiles()) {
			if(t.entities.contains(entities.get(name.toLowerCase()))) {
				t.entities.remove(entities.get(name.toLowerCase()));
			}
		}
		entities.remove(name.toLowerCase());
	}
	
	//add a new entity from a prototype
	
	public Entity addNew(String name) throws Exception {
		//System.out.println(name.toLowerCase());
		
		Entity e = null;
		
		try {
			if(entityPrototypes.containsKey(name.toLowerCase())) {
				
				Entity boop = entityPrototypes.get(name.toLowerCase());
				if(boop == null) { Game.die(); }
				
				e = new Entity(boop);
				
				e.quad = new Quad(
					new Rectangle(e.logicalPosition.scale(screen.getTileSize().x,
						screen.getTileSize().y), screen.getTileSize()));

				e.currentTileSlot = screen.getAt(e.logicalPosition);
				for(int x = 0;x<e.size.x;x++) {
					for(int y = 0;y<e.size.y;y++) {
						screen.getActiveTiles().add(screen.getAt(e.logicalPosition.add(new Vector2f(x,y))));
						screen.getAt(e.logicalPosition.add(new Vector2f(x,y))).entities.add(e);
					}
				}
				
				entities.put(name.toLowerCase(), e);
				
				TileSlot t = 
					screen.getAt(
						new Vector2f(
							e.logicalPosition.intx(),
							e.logicalPosition.inty()));
				t.entities.add(e);
				
				if(!screen.getActiveTiles().contains(t)) {
					screen.getActiveTiles().add(t);
				}
			}
		} catch (Exception f) {
			f.printStackTrace();
		}
		
		return e;
	}
}
