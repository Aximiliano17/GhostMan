package Ghostman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Ghostman.GameObject.ObjectId;

public class KeyInput extends KeyAdapter {
	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
			for (GameObject obj : handler.getGameObjects()) {
				if (obj instanceof Ghost) {
					Ghost g = (Ghost) obj;
					if (Game.state == Game.GameState.PLAYING) {
						if (key == KeyEvent.VK_RIGHT) {

							g.setDir(Pac.Direction.Right);
							handler.removeObject(obj, ObjectId.Pac);

						} else if (key == KeyEvent.VK_LEFT) {

							g.setDir(Pac.Direction.Left);
							handler.removeObject(obj, ObjectId.Pac);

						} else if (key == KeyEvent.VK_UP) {

							g.setDir(Pac.Direction.Up);
							handler.removeObject(obj, ObjectId.Pac);

						} else if (key == KeyEvent.VK_DOWN) {

							g.setDir(Pac.Direction.Down);
							handler.removeObject(obj, ObjectId.Pac);

						} else if (key == 'p' || key == 'P') {
							Game.state = Game.GameState.PAUSED;
						}
					} else if (key == 'p' || key == 'P') {
						Game.state = Game.GameState.PLAYING;
					}

				}
		}
	}
}
