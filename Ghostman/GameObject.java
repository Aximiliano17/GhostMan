package Ghostman;

/**
 * This class is a abstract class that implements fields that belong to all the other objects.listX and listY are the positions in the 2d matrix.
 * x and y is the pixel position.Since each cell in the matrix is of BLOCK_SIZE pixels,to get the x and y pixel position we just multiple by BLOCK_SIZE
 * the listX and listY coordinates.
 * 
 */
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Ghostman.Pac.Direction;

public abstract class GameObject {

	enum ObjectId {
		Dot, Pac, Ghost, Block, RedPac
	}

	protected int x, y;// pixel position
	protected ObjectId id;
	protected int listX, listY;// 2d matrix coordinates

	public GameObject(ObjectId id, int listX, int listY) {
		this.id = id;
		this.x = listX * Game.BLOCK_SIZE;
		this.y = listY * Game.BLOCK_SIZE;
		this.listX = listX;
		this.listY = listY;
	}

	public abstract void render(Graphics g);

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getListX() {
		return this.listX;
	}

	public int getListY() {
		return this.listY;
	}

	public void setListX(int l) {
		this.x = l * Game.BLOCK_SIZE;
		this.listX = l;
	}

	public void setListY(int l) {
		this.y = l * Game.BLOCK_SIZE;
		this.listY = l;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(GameObject.ObjectId id) {
		this.id = id;
	}

//CHecks to see if there are dots or blocks around the object and returns a list of directions it can go to, used for Pac AI
//and Ghost checking for blocks
	public List<Direction> hasObjectsAround(int x, int y, GameObject.ObjectId id, Handler handler) {
		List<Direction> direction = new ArrayList<Direction>();
		if (id == GameObject.ObjectId.Dot) {
			if (hasObject(x, y - 1, id, handler)) {
				direction.add(Direction.Up);
			}
			if (hasObject(x + 1, y, id, handler)) {
				direction.add(Direction.Right);
			}
			if (hasObject(x, y + 1, id, handler)) {
				direction.add(Direction.Down);
			}
			if (hasObject(x - 1, y, id, handler)) {
				direction.add(Direction.Left);
			}
			// if there isn't a block in one direction we add that direction to the list
		} else if (id == GameObject.ObjectId.Block) {
			if (!hasObject(x, y - 1, id, handler)) {
				direction.add(Direction.Up);
			}
			if (!hasObject(x + 1, y, id, handler)) {
				direction.add(Direction.Right);
			}
			if (!hasObject(x, y + 1, id, handler)) {
				direction.add(Direction.Down);
			}
			if (!hasObject(x - 1, y, id, handler)) {
				direction.add(Direction.Left);
			}
		}
		return direction;
	}

//This method checks for an object at x and y coordinates
	public Boolean hasObject(int x, int y, GameObject.ObjectId id, Handler handler) {
		if (x == 15)
			x = 0;
		if (x == -1)
			x = 14;
		if (y == 15)
			y = 0;
		if (y == -1)
			x = 14;
		for (GameObject obj : handler.getGameObjects()) {
			if (obj.getId() == id && obj.getListX() == x && obj.getListY() == y) {
				return true;
			}
		}
		return false;
	}
	//Moves object to the specified direction and changes the image to correspond to its direction
	public void moveObject(Direction d, GameObject obj,Handler handler) {
		switch (d) {
		case Up:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewY(-1);
			}
			//checks for boundaries, and checks it there is a block before moving
			if (obj.getListY() > 0 && !hasObject(obj.getListX(), obj.getListY() - 1, GameObject.ObjectId.Block,handler)) {
				obj.setListY(obj.getListY() - 1);
			} else if (obj.getListY() == 0 && !hasObject(obj.getListX(), 14, GameObject.ObjectId.Block,handler)) {
				obj.setListY(14);//if y coordinate is at 0 and we are moving up, the next position is 14
			}
			break;
		case Right:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewX(1);
			}
			if (obj.getListX() < 14 && !hasObject(obj.getListX() + 1, obj.getListY(), GameObject.ObjectId.Block,handler)) {
				obj.setListX(obj.getListX() + 1);
			} else if (obj.getListX() == 14 && !hasObject(0, obj.getListY(), GameObject.ObjectId.Block,handler)) {
				obj.setListX(0);
			}
			break;
		case Down:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewY(1);
			}
			if (obj.getListY() < 14 && !hasObject(obj.getListX(), obj.getListY() + 1, GameObject.ObjectId.Block,handler)) {
				obj.setListY(obj.getListY() + 1);
			} else if (obj.getListY() == 14 && !hasObject(obj.getListX(), 0, GameObject.ObjectId.Block,handler)) {
				obj.setListY(0);
			}
			break;
		case Left:
			if (obj instanceof Pac||obj instanceof RedPac) {
				Pac p = (Pac) obj;
				p.setViewX(-1);
			}
			if (obj.getListX() > 0 && !hasObject(obj.getListX() - 1, obj.getListY(), GameObject.ObjectId.Block,handler)) {
				obj.setListX(obj.getListX() - 1);
			} else if (obj.getListX() == 0 && !hasObject(14, obj.getListY(), GameObject.ObjectId.Block,handler)) {
				obj.setListX(14);
			}
			if(obj instanceof Pac) {
			}
			break;
		}
	}
}
