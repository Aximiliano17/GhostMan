package Ghostman;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class Pac extends GameObject {

	enum Direction {
		Up, Right, Down, Left;
	}

	protected int pacAnimDir = 1;
	protected int pacAnimation = 0;
	
	public static int pacCount;
	public static int enemyCount;
	protected String pacDown;
	protected String pacDown2;
	protected String pacLeft;
	protected String pacLeft2;
	protected String pacRight;
	protected String pacUp;
	protected String pacRight2;
	protected String pacUp2;

	protected int viewX, viewY;

	public Pac(ObjectId id, int mapX, int mapY) {
		super(id, mapX, mapY);
		loadImages();
	}
public void loadImages()
{
	pacDown = "/images/PacManDown.gif";
	pacDown2 = "/images/PacManDown2.gif";
	pacLeft = "/images/PacManLeft.gif";
	pacLeft2 = "/images/PacManLeft2.gif";
	pacRight = "/images/PacManRight.gif";
	pacUp = "/images/PacManUp.gif";
	pacRight2 = "/images/PacManRight2.gif";
	pacUp2 = "/images/PacManUp2.gif";
	
}
	@Override
	public void render(Graphics g) {
		pacAnimation = pacAnimation + pacAnimDir;
		if (pacAnimation == 3 || pacAnimation == 0) {
			pacAnimDir = -pacAnimDir;
		}
		drawPac(g);
	}

	public void setViewX(int x) {
		this.viewX = x;
		this.viewY = 0;
	}

	public void setViewY(int y) {
		this.viewY = y;
		this.viewX = 0;
	}

	public Image getImages(String s) {
		ImageIcon i = new ImageIcon(getClass().getResource(s));
		return i.getImage();
	}

	protected void drawPac(Graphics g) {

		if (viewX == -1) {
			drawPacmanLeft(g);
		} else if (viewX == 1) {
			drawPacmanRight(g);
		} else if (viewY == -1) {
			drawPacmanUp(g);
		} else if (viewY == 1) {
			drawPacmanDown(g);
		} else {
			drawPacmanRight(g);
		}
	}

	protected void drawPacmanUp(Graphics g) {

		switch (pacAnimation) {
		case 1:
			g.drawImage(getImages(this.pacUp), this.x, this.y, null);
			break;
		case 2:
			g.drawImage(getImages(this.pacUp2), this.x, this.y, null);
			break;
		default:
			g.drawImage(getImages(this.pacUp), this.x, this.y, null);
			break;
		}
	}

	protected void drawPacmanDown(Graphics g) {

		switch (pacAnimation) {
		case 1:
			g.drawImage(getImages(this.pacDown), this.x, this.y, null);
			break;
		case 2:
			g.drawImage(getImages(this.pacDown2), this.x, this.y, null);
			break;
		default:
			g.drawImage(getImages(this.pacDown), this.x, this.y, null);
			break;
		}
	}

	protected void drawPacmanLeft(Graphics g) {

		switch (pacAnimation) {
		case 1:
			g.drawImage(getImages(this.pacLeft), this.x, this.y, null);
			break;
		case 2:
			g.drawImage(getImages(this.pacLeft2), this.x, this.y, null);
			break;
		default:
			g.drawImage(getImages(this.pacLeft), this.x, this.y, null);
			break;
		}
	}

	protected void drawPacmanRight(Graphics g) {
		switch (pacAnimation) {
		case 1:
			g.drawImage(getImages(this.pacRight), this.x, this.y, null);
			break;
		case 2:
			g.drawImage(getImages(this.pacRight2), this.x, this.y, null);
			break;
		default:
			g.drawImage(getImages(this.pacRight), this.x, this.y, null);
			break;
		}
	}

	protected void pacAI(Handler handler) {
		
		List<Direction> dotsAround = new ArrayList<Direction>();
		dotsAround = handler.hasObjectsAround(this.getListX(), this.getListY(),GameObject.ObjectId.Dot);//stores the directions in which there are dots
		if (dotsAround.isEmpty()) {
			 randomDirection(handler);
			}
		 else {
			eatDot(handler,dotsAround);
		}}

	protected void randomDirection(Handler handler) {
		List<Direction> directions = new ArrayList<Direction>();
		directions = handler.hasObjectsAround(this.getListX(), this.getListY(),GameObject.ObjectId.Block);//stores the direction it can move

		Random rand = new Random();
		Direction randomD = directions.get(rand.nextInt(directions.size()));
		handler.moveObject(randomD,this);
		for(GameObject obj:handler.object)
		{
			if(obj.getId()==GameObject.ObjectId.Ghost&&obj.getListX()==this.getListX()&&obj.getListY()==this.getListY())
			{
				handler.removeObject(this);
				pacCount--;
				enemyCount--;
			}
		}
		}
		protected void eatDot(Handler handler,List<Direction> direction)
		{
			Random rand = new Random();
			Direction randomD = direction.get(rand.nextInt(direction.size()));
			handler.moveObject(randomD,this);
			handler.object.removeIf(o -> o.getId() == GameObject.ObjectId.Dot && o.getListX() == this.getListX()
					&& o.getListY() == this.getListY());
			for(GameObject obj:handler.object)
			{
				if(obj.getId()==GameObject.ObjectId.Ghost&&obj.getListX()==this.getListX()&&obj.getListY()==this.getListY())
				{
					handler.removeObject(this);
					pacCount--;
					enemyCount--;
				}
			}
			}
}
