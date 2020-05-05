package Ghostman;

/**
 * This class is used to hold all our game objects into a list and updates and renders all of them.
 */

import java.awt.Graphics;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import Ghostman.GameObject.ObjectId;

public class Handler {

	private List<GameObject> gameObjects = new CopyOnWriteArrayList<GameObject>();

	public List<GameObject> getGameObjects() {
		return this.gameObjects;
	}

	public void render(Graphics g) {
		for (GameObject obj : gameObjects) {
			obj.render(g);
		}
	}

	// list of objects is sorted so that when rendering,a dot won't be drawn on top
	// of Pacs and Ghost.
	public void sortGameObjects() {
		Collections.sort(gameObjects, (GameObject a, GameObject b) -> a.getId().compareTo(b.getId()));
	}

	// at the end of each map, we clear the objects
	public void clearObjects() {
		this.gameObjects.clear();
	}

	public boolean removeObject(GameObject obj, GameObject.ObjectId id) {
		// A special case when Pac moves and needs to check if Ghost is in the same
		// position.Then remove Pac
		if ((obj instanceof Pac || obj instanceof Ghost) && id == ObjectId.Pac) {
			for (GameObject ob : gameObjects) {
				if (ob instanceof Ghost) {
					Ghost g = (Ghost) ob;
					if (gameObjects.removeIf(
							o -> o.getId() == id && o.getListX() == g.getListX() && o.getListY() == g.getListY())) {
						Pac.pacCount--;
						Pac.enemyCount--;
						if (Pac.pacCount == 0) {
							changeIds();
						}
						return true;
					}
				}
			}
		} else if (gameObjects
				.removeIf(o -> o.getId() == id && o.getListX() == obj.getListX() && o.getListY() == obj.getListY()))
			return true;

		return false;
	}

	public void changeIds() {
		Pac.pacCount = Pac.enemyCount;
		for (GameObject obj : gameObjects) {
			if (obj instanceof RedPac) {
				obj.id = ObjectId.Pac;
			}
		}
	}
}
