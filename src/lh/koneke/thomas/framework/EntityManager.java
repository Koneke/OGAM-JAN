package lh.koneke.thomas.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lh.koneke.games.lwjglplatformer.Entity;
import lh.koneke.games.lwjglplatformer.Screen;


/* entity
 * :name
 * look
 * x,y,depth,w,h
 * texture
 * .tha
 */

public class EntityManager {
	Map<String, Entity> entities;
	Screen screen;
	
	public EntityManager(Screen screen) {
		entities = new HashMap<String, Entity>();
		this.screen = screen;
	}
	
	public void switchScreen(Screen screen) {
		this.screen = screen;
	}
	
	public void load(String path) {
		BufferedReader br;
		System.out.println();
		try {
			br = new BufferedReader(new FileReader(path));
			String name;
			int x, y, z, w, h;
			Entity currentEntity = null;
			
			String s;
			int line = 0;
			while((s = br.readLine()) != null) {
				if(s.substring(0,1).equals(":")) {
					name = s.substring(1, s.length());
					currentEntity = new Entity(name);
					line = 1;
				}
				else {
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

						System.out.println("Loaded "+currentEntity.getName()+", look: \""+currentEntity.getLook()+"\".");
						System.out.println(" - It is placed at "+x+","+y+" at depth "+z+", and it is "+w+" wide and "+h+" high.");
						
						line = 3;
					} else if(line == 3) {
						currentEntity.texturePath = s;
						line = 4;
					} else if(line == 4) {
						currentEntity.thaPath = s;
						line = 5;
					}
					if(line == 5) {
						System.out.println(" - tex "+currentEntity.texturePath);
						System.out.println(" - tha "+currentEntity.thaPath);
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
			}

			System.out.println();
		}
	}
	
	public Entity getEntity(String name) {
		return entities.get(name.toLowerCase());
	}

	public Map<String, Entity> getEntities() {
		return entities;
	}
}
