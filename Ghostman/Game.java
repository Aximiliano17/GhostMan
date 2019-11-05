package Ghostman;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static GameState state = GameState.PAUSED;
	private int gameScore;

	private Timer timer;
	private Handler handler;
	private Maps maps;
	private int currentMap;//keeps track of the current map, since the maps are stored in an arraylist. When currentMap is 0, it will point to first map.

	public static Color dotColor = new Color(192, 192, 0);
	public static Color mazeColor = new Color(5, 100, 5);

	//Constants for screensize and size of blocks, as well as used to get object coordinates.
	public static final int BLOCK_SIZE = 30;
	private static final int N_BLOCKS = 15;
	private static final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

	private boolean mapDrawn;

	public enum GameState {
		PLAYING, PAUSED, GAMEOVER
	}

	public Game() {
		handler = new Handler();
		maps = new Maps();
		timer = new Timer(1000, this);
		timer.start();
		addKeyListener(new KeyInput(handler));
		setFocusable(true);
	}

	// rendering images, and backgrounds
	private void render(Graphics g) {
		// Created a black screen
		g.setColor(Color.black);
		g.fillRect(0, 0, SCREEN_SIZE, SCREEN_SIZE);
		
		//calls a method to draw the currentMap
		if (mapDrawn == false && currentMap < 3) {
			drawMap(g, maps.getMapList().get(currentMap));
		}
		handler.render(g);

		switch (state) {
		case PLAYING:
			playGame(g);
			break;
		case PAUSED:
			showIntroScreen(g);
			break;
		case GAMEOVER:
			gameOver(g);
			break;
		}
	}
//Draws the map depending on the number in each cell
	public void drawMap(Graphics g, Integer[][] currentMap) {
		for (int y = 0, a = 0; a < currentMap.length; y += BLOCK_SIZE, a++) {
			for (int x = 0, b = 0; b < currentMap[a].length; x += BLOCK_SIZE, b++) {

				g.setColor(mazeColor);

				if ((currentMap[a][b]) == 1) {
					handler.addObject(new Block(GameObject.ObjectId.Block, b, a));
				}

				if ((currentMap[a][b]) == 0) {
					handler.addObject(new Dot(GameObject.ObjectId.Dot, b, a));
				}
				if ((currentMap[a][b] == -1)) {
					g.setColor(Color.black);
					g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

				}
				if (currentMap[a][b] == 2) {
					handler.addObject(new Pac(GameObject.ObjectId.Pac, b, a));
					Pac.pacCount++;
					Pac.enemyCount++;
				}
				if (currentMap[a][b] == 3) {
					handler.addObject(new Ghost(GameObject.ObjectId.Ghost, b, a));
				}
				if (currentMap[a][b] == 4) {
					handler.addObject(new RedPac(GameObject.ObjectId.RedPac, b, a));
					Pac.enemyCount++;
				}
			}
		}
		handler.sortObjects();

		mapDrawn = true;
	}

	public void showIntroScreen(Graphics g) {

		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		String s = "Game Paused";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
		String f = "Press p to pause/unpause";
		g.drawString(f, 80, SCREEN_SIZE / 2 + 16);
	}

	private void playGame(Graphics g) {
		int count = 0;
		if (Pac.enemyCount > 0) {
			for (GameObject obj : handler.object) {
				if (obj.getId() == GameObject.ObjectId.Pac) {
					((Pac) obj).pacAI(handler);
				}
				if (obj.getId() == GameObject.ObjectId.RedPac) {

					((RedPac) obj).pacAI(handler);
				}
			}
		} else if (Pac.enemyCount == 0) {
			for (GameObject obj : handler.object) {
				if (obj.getId() == GameObject.ObjectId.Dot) {
					count++;
				}
			}
			gameScore = gameScore + (count * 50);
			handler.clearObjects();
			currentMap++;
			mapDrawn = false;
		}
		if (currentMap == 3) {
			state = GameState.GAMEOVER;
		}

	}

	public void gameOver(Graphics g) {
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 70);
		g.setColor(Color.white);
		g.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 70);
		String s = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
		String f = "Score: " + gameScore;
		g.drawString(f, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2 + 16);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public static void main(String[] args) {
		new Window(SCREEN_SIZE, SCREEN_SIZE, "GhostMan", new Game());
	}

}
