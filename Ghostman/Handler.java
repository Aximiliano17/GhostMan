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

import javax.swing.Timer;

import Ghostman.GameObject.ObjectId;
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
	//list of objects is sorted so that when rendering,a dot won't be drawn on top of Pacs and Ghost.
	public void sortGameObjects()
	{
		 Collections.sort(gameObjects, (GameObject a,GameObject b)-> a.getId().compareTo(b.getId()));
	}
	//at the end of each map, we clear the objects
	public void clearObjects() {
		this.gameObjects.clear();
	}
}
