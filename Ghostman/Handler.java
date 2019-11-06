package Ghostman;
/**
 * This class is used to hold all our game objects into a list and updates and renders all of them.
 */

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import Ghostman.Pac.Direction;

public class Handler {

	private List<GameObject> gameObjects = new CopyOnWriteArrayList<GameObject>();

	public List<GameObject> getGameObjects()
	{
		return this.gameObjects;
	}
	public void render(Graphics g) {
		for (GameObject obj : gameObjects) {
			obj.render(g);
		}
	}
	public void addObject(GameObject object) {
		this.gameObjects.add(object);
	}
	public void removeObject(GameObject object) {
		this.gameObjects.remove(object);
	}
	//list of objects is sorted so that when rendering,a dot won't be drawn on top of Pacs and Ghost.
	public void sortGameObjects()
	{
		 Collections.sort(gameObjects, new Comparator<GameObject>() {
		      @Override
		      public int compare(GameObject obj, GameObject obj2) {
		          return obj.getId().compareTo(obj2.getId());
		      }
		    });
	}
	//at the end of each map, we clear the objects
	public void clearObjects() {
		this.gameObjects.clear();
	}
//This method checks for an object at x and y coordinates
	public Boolean hasObject(int x, int y, GameObject.ObjectId id) {
		if (x == 15)
			x = 0;
		if (x == -1)
			x = 14;
		if (y == 15)
			y = 0;
		if (y == -1)
			x = 14;
		for (GameObject obj : this.gameObjects) {
			if (obj.getId() == id && obj.getListX() == x && obj.getListY() == y) {
				return true;
			}
		}
		return false;
	}
//CHecks to see if there are dots or blocks around the object and returns a list of directions it can go to, used for Pac AI
	public List<Direction> hasObjectsAround(int x, int y, GameObject.ObjectId id) {
		List<Direction> direction = new ArrayList<Direction>();
		if (id == GameObject.ObjectId.Dot) {
			if (hasObject(x, y - 1, id)) {
				direction.add(Direction.Up);
			}
			if (hasObject(x + 1, y, id)) {
				direction.add(Direction.Right);
			}
			if (hasObject(x, y + 1, id)) {
				direction.add(Direction.Down);
			}
			if (hasObject(x - 1, y, id)) {
				direction.add(Direction.Left);
			}
			//if there isn't a block in one direction we add that direction to the list
		} else if (id == GameObject.ObjectId.Block) {
			if (!hasObject(x, y - 1, id)) {
				direction.add(Direction.Up);
			}
			if (!hasObject(x + 1, y, id)) {
				direction.add(Direction.Right);
			}
			if (!hasObject(x, y + 1, id)) {
				direction.add(Direction.Down);
			}
			if (!hasObject(x - 1, y, id)) {
				direction.add(Direction.Left);
			}
		}
		return direction;
	}
//Moves object to the specified direction and changes the image to correspond to its direction
	public void moveObject(Direction d, GameObject obj) {
		switch (d) {
		case Up:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewY(-1);
			}
			if (obj.getListY() > 0 && !hasObject(obj.getListX(), obj.getListY() - 1, GameObject.ObjectId.Block)) {
				obj.setListY(obj.getListY() - 1);
			} else if (obj.getListY() == 0 && !hasObject(obj.getListX(), 14, GameObject.ObjectId.Block)) {
				obj.setListY(14);
			}
			removePac();
			break;
		case Right:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewX(1);
			}
			if (obj.getListX() < 14 && !hasObject(obj.getListX() + 1, obj.getListY(), GameObject.ObjectId.Block)) {
				obj.setListX(obj.getListX() + 1);
			} else if (obj.getListX() == 14 && !hasObject(0, obj.getListY(), GameObject.ObjectId.Block)) {
				obj.setListX(0);
			}
			removePac();
			break;
		case Down:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewY(1);
			}
			if (obj.getListY() < 14 && !hasObject(obj.getListX(), obj.getListY() + 1, GameObject.ObjectId.Block)) {
				obj.setListY(obj.getListY() + 1);
			} else if (obj.getListY() == 14 && !hasObject(obj.getListX(), 0, GameObject.ObjectId.Block)) {
				obj.setListY(0);
			}
			removePac();
			break;
		case Left:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewX(-1);
			}
			if (obj.getListX() > 0 && !hasObject(obj.getListX() - 1, obj.getListY(), GameObject.ObjectId.Block)) {
				obj.setListX(obj.getListX() - 1);
			} else if (obj.getListX() == 0 && !hasObject(14, obj.getListY(), GameObject.ObjectId.Block)) {
				obj.setListX(14);
			}
			removePac();
			break;
		}
	}
	//If Pac and Ghost are at same location, Pac gets removed
	public void removePac()
	{
		for(GameObject g:this.gameObjects)
		{
			for(GameObject p:this.gameObjects)
			{
				if(g.getId()==GameObject.ObjectId.Ghost && p.getId()==GameObject.ObjectId.Pac && g.getListX()==p.getListX()&&g.getListY()==p.getListY())
				{
					this.gameObjects.remove(p);
					Pac.pacCount--;
					Pac.enemyCount--;
				}
			}
		}
	}
}
