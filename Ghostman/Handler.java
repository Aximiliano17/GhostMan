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

	public List<GameObject> object = new CopyOnWriteArrayList<GameObject>();

	public void render(Graphics g) {
		for (GameObject obj : object) {
			obj.render(g);
		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	//list of objects is sorted so that when rendering,a dot won't be drawn on top of Pacs and Ghost.
	public void sortObjects()
	{
		 Collections.sort(object, new Comparator<GameObject>() {
		      @Override
		      public int compare(GameObject obj, GameObject obj2) {
		          return obj.getId().compareTo(obj2.getId());
		      }
		    });
	}
	//at the end of each map, we clear the objects
	public void clearObjects() {
		this.object.clear();
	}
//This method checks for an object at x and y coordinates.Mostly used for blocks and Dots
	public Boolean hasObject(int x, int y, GameObject.ObjectId o) {
		if (x == 15)
			x = 0;
		if (x == -1)
			x = 14;
		if (y == 15)
			y = 0;
		if (y == -1)
			x = 14;
		for (GameObject obj : object) {
			if (obj.getId() == o && obj.getListX() == x && obj.getListY() == y) {
				return true;
			}
		}
		return false;
	}
//CHecks to see if there are dots or blocks around the object
	public List<Pac.Direction> hasObjectsAround(int x, int y, GameObject.ObjectId id) {
		List<Pac.Direction> direction = new ArrayList<Pac.Direction>();
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
//Moves object, as long as there isn't a block in the specified direction
	public void moveObject(Direction d, GameObject t) {
		switch (d) {
		case Up:
			if (t.getId() == GameObject.ObjectId.Pac||t.getId()==GameObject.ObjectId.RedPac) {
				Pac p = (Pac) t;
				p.setViewY(-1);
			}
			if (t.getListY() > 0 && !hasObject(t.getListX(), t.getListY() - 1, GameObject.ObjectId.Block)) {
				t.setListY(t.getListY() - 1);
			} else if (t.getListY() == 0 && !hasObject(t.getListX(), 14, GameObject.ObjectId.Block)) {
				t.setListY(14);
			}
			break;
		case Right:
			if (t.getId() == GameObject.ObjectId.Pac||t.getId()==GameObject.ObjectId.RedPac) {
				Pac p = (Pac) t;
				p.setViewX(1);
			}
			if (t.getListX() < 14 && !hasObject(t.getListX() + 1, t.getListY(), GameObject.ObjectId.Block)) {
				t.setListX(t.getListX() + 1);
			} else if (t.getListX() == 14 && !hasObject(0, t.getListY(), GameObject.ObjectId.Block)) {
				t.setListX(0);
			}
			break;
		case Down:
			if (t.getId() == GameObject.ObjectId.Pac||t.getId()==GameObject.ObjectId.RedPac) {
				Pac p = (Pac) t;
				p.setViewY(1);
			}
			if (t.getListY() < 14 && !hasObject(t.getListX(), t.getListY() + 1, GameObject.ObjectId.Block)) {
				t.setListY(t.getListY() + 1);
			} else if (t.getListY() == 14 && !hasObject(t.getListX(), 0, GameObject.ObjectId.Block)) {
				t.setListY(0);
			}
			break;
		case Left:
			if (t.getId() == GameObject.ObjectId.Pac||t.getId()==GameObject.ObjectId.RedPac) {
				Pac p = (Pac) t;
				p.setViewX(-1);
			}
			if (t.getListX() > 0 && !hasObject(t.getListX() - 1, t.getListY(), GameObject.ObjectId.Block)) {
				t.setListX(t.getListX() - 1);
			} else if (t.getListX() == 0 && !hasObject(14, t.getListY(), GameObject.ObjectId.Block)) {
				t.setListX(14);
			}
			break;
		}
	}
}
