package Ghostman;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Ghost extends GameObject {

	private String ghost = "/images/Ghost1.gif";
	private Pac.Direction dir=null;
	
	public Ghost(ObjectId id,int mapX,int mapY) {
		super(id,mapX,mapY);
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(getImages(ghost), (int) x, (int) y, null);
	}
	public Image getImages(String s) {
		ImageIcon i = new ImageIcon(getClass().getResource(s));
		return i.getImage();
	}
	public void moveGhost(Handler handler)
	{
		if(this.dir==null) return;
		
		moveObject(dir,this,handler);
		
	}
	public void setDir(Pac.Direction dir)
	{
		this.dir=dir;
	}

}
