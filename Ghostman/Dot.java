package Ghostman;

import java.awt.Color;
import java.awt.Graphics;

public class Dot extends GameObject {

	public Dot(ObjectId id, int mapX, int mapY) {
		super(id, mapX, mapY);
	}
	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x+1, y+1, 24, 24);
		g.setColor(Game.dotColor);
		g.fillRect(x + 11, y + 11, 2, 2);

	}

}
