package Ghostman;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends GameObject {

	public Block(ObjectId id,int mapX,int mapY) {
		super(id,mapX,mapY);
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.blue);
		g.drawRect( (int)x,(int) y, 24, 24);
	}
}
