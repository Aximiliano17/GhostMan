package Ghostman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	private Handler handler;

	private long lastPressProcessed = 0;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//Ghost Keyinput was too fast and had to be slow down
		if ((System.currentTimeMillis() - lastPressProcessed) > 700) {
			lastPressProcessed = System.currentTimeMillis();
			for (GameObject obj:handler.object) {
				if (obj instanceof Ghost) {
					Ghost g = (Ghost) obj;
					if (Game.state==Game.GameState.PLAYING) {
						if (key == KeyEvent.VK_RIGHT) {

							if (!handler.hasObject(g.getListX() + 1, g.getListY(),GameObject.ObjectId.Block)) {
								handler.moveObject(Pac.Direction.Right, obj);
								if(handler.object.removeIf(o -> o.getId() == GameObject.ObjectId.Pac
										&& o.getListX() == g.getListX() && o.getListY() == g.getListY()))
								{
									Pac.pacCount--;
									Pac.enemyCount--;
								}
							}

						} else if (key == KeyEvent.VK_LEFT) {
							if (!handler.hasObject(g.getListX() - 1, g.getListY(),GameObject.ObjectId.Block)) {
								handler.moveObject(Pac.Direction.Left, obj);
								if(handler.object.removeIf(o -> o.getId() == GameObject.ObjectId.Pac
										&& o.getListX() == g.getListX() && o.getListY() == g.getListY()))
								{
									Pac.pacCount--;
									Pac.enemyCount--;
								}
							}
						} else if (key == KeyEvent.VK_UP) {
							if (!handler.hasObject(g.getListX(), g.getListY() - 1,GameObject.ObjectId.Block)) {
								handler.moveObject(Pac.Direction.Up, obj);
								if(handler.object.removeIf(o -> o.getId() == GameObject.ObjectId.Pac
										&& o.getListX() == g.getListX() && o.getListY() == g.getListY()))
								{
									Pac.pacCount--;
									Pac.enemyCount--;
								}
							}
						} else if (key == KeyEvent.VK_DOWN) {
							if (!handler.hasObject(g.getListX(), g.getListY() + 1,GameObject.ObjectId.Block)) {
								handler.moveObject(Pac.Direction.Down, obj);
								
								if(handler.object.removeIf(o -> o.getId() == GameObject.ObjectId.Pac
										&& o.getListX() == g.getListX() && o.getListY() == g.getListY()))
								{
									Pac.pacCount--;
									Pac.enemyCount--;
								}
								
							}
						}
						else if(key=='p'||key=='P')
						{
							Game.state=Game.GameState.PAUSED;
						}
					} else if (key == 'p' || key == 'P') {
						Game.state=Game.GameState.PLAYING;
					}

				}
			}
		}
	}
}
