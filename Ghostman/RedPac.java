package Ghostman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RedPac extends Pac {

	public RedPac(ObjectId id, int mapX, int mapY) {
		super(id, mapX, mapY);
	}

	@Override
	public void loadImages() {
		pacDown = "/images/redPacManDown.gif";
		pacDown2 = "/images/redPacManDown2.gif";
		pacLeft = "/images/redPacManLeft.gif";
		pacLeft2 = "/images/redPacManLeft2.gif";
		pacRight = "/images/redPacManRight.gif";
		pacUp = "/images/redPacManUp.gif";
		pacRight2 = "/images/redPacManRight2.gif";
		pacUp2 = "/images/redPacManUp2.gif";
	}

	/*
	 * The Idea for the AI is to create a 2D Node representation of my matrix. Every cell will hold a distance of Max_value
	 * and a reference to where we came from, called parent or prev. Every time we move a cell we increase the distance by 1,
	 * and if that distance is less than its previous distance then we override the cell, and add it to the q. By doing this,
	 * we are not continuing paths already visited with a higher distance. When we find our Ghost we will be able to backtrack
	 * our path by following the parent/prev nodes. In the end we will return the next cell our redPac needs to move to.
	 */
	public void pacAI(Handler handler, Integer[][] map) {
		int[] destination = new int[2];// will store coordinates of Ghost

		Cell[][] cells = createCells(map, destination, handler);// create the cells matrix and find Ghost coordinates

		Cell next=doBFS(cells, destination);//Does BFS and returns the next cell redPac should move to
		chooseDirection(handler, next);//Choose direction based on cell coordinates

	}

	private Cell doBFS(Cell[][] cells, int[] destination) {
		LinkedList<Cell> path = new LinkedList<>();// Will hold my shortest path when I find it
		LinkedList<Cell> q = new LinkedList<>();
		Cell src = cells[this.listX][this.listY];// the source coordinates is this object ListX & ListY
		src.dist = 0;
		q.add(src);
		Cell dest = null;// When our source meets the ghost, our path is complete, dest will point to Ghost cell
		Cell curr;

		while (!q.isEmpty()) {
			curr = q.poll();
			if (curr.x == destination[0] && curr.y == destination[1]) {
				dest = curr;
				break;
			}
			visit(cells, q, curr.x - 1, curr.y, curr);
			visit(cells, q, curr.x + 1, curr.y, curr);
			visit(cells, q, curr.x, curr.y - 1, curr);
			visit(cells, q, curr.x, curr.y + 1, curr);
		}
		if (dest == null) {
			return null;
		} else {
			curr = dest;
			do {
				path.addFirst(curr);
			} while ((curr = curr.prev) != null);
			path.removeFirst();// Gets rid of the source path
		}
		Cell next = path.removeFirst();
		return next;
	}
//If out of bounds, it returns immediatly, else adds the cell to the q with less distance and updates its parent
	private void visit(Cell[][] cells, LinkedList<Cell> q, int x, int y, Cell parent) {
		int dist = parent.dist + 1;
		if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
			return;
		}
		Cell curr = cells[x][y];
		// prevents from adding a node already visited
		if (dist < curr.dist) {
			curr.dist = dist;
			curr.prev = parent;
			q.add(curr);
		}
	}

	// Method for constructing a 2D matrix of cells and getting my start and
	// destination coordinates
	private Cell[][] createCells(Integer[][] map, int[] destination, Handler handler) {
		Cell[][] cells = new Cell[map.length][map[0].length];

		// Create every cell
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[j][i] = new Cell(j, i, Integer.MAX_VALUE, null);
			}
		}
		// Make block cells null and get sources and destination cells
		for (GameObject obj : handler.getGameObjects()) {
			int x = obj.getListX();
			int y = obj.getListY();

			if (obj.getId() == ObjectId.Block) {
				cells[x][y] = null;
			} else if (obj.getId() == ObjectId.Ghost) {
				destination[0] = x;
				destination[1] = y;
			}
		}

		return cells;
	}

//Based off the first path input, we choose the next direction RedPac should go
	public void chooseDirection(Handler handler, Cell next) {
		if (next.y == this.listY && next.x == this.listX + 1) {
			moveObject(Direction.Right, this, handler);
		} else if (next.y == this.listY && next.x == this.listX - 1) {
			moveObject(Direction.Left, this, handler);
		} else if (next.x == this.listX && next.y == this.listY + 1) {
			moveObject(Direction.Down, this, handler);
		} else if (next.x == this.listX && next.y == this.listY - 1) {
			moveObject(Direction.Up, this, handler);
		}

		if (handler.getGameObjects().removeIf(o -> o.getId() == GameObject.ObjectId.Ghost
				&& o.getListX() == this.getListX() && o.getListY() == this.getListY())) {
			Game.state = Game.GameState.GAMEOVER;
		}
	}

	class Cell implements Comparable<Cell> {
		int x;
		int y;
		int dist;
		Cell prev;

		Cell(int x, int y, int dist, Cell prev) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.prev = prev;
		}

		@Override
		public int compareTo(Cell o) {
			return dist - o.dist;
		}
	}
}
