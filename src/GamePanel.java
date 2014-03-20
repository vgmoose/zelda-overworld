import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class GamePanel extends JPanel implements MouseListener, KeyListener
{
	// An array list is an object wrapper for an array, allows dynamic resizing
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player activePlayer;
	private int keyCode;
	private Timer t;

	public GamePanel()
	{
		// set the size of this canvas
		super.setPreferredSize(new Dimension(500, 500));

		// set the mouse and key listener of this panel to this panel
		super.addMouseListener(this);
		super.addKeyListener(this);

		// give this panel focus so keystrokes apply to it
		super.setFocusable(true);
		super.requestFocusInWindow();

		// create a timer to update the player every x seconds
		t = new Timer();

		// this is a kind of weird thing, it's needed to not have a
		// strange delay when you hold down a key
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				updateTimer();
			}
		}, 0, 40);
	}

	// this method is called when someone calls repaint()
	public void paintComponent(Graphics g)
	{
		// this call clears the canvas (it's like clrScreen)
		super.paintComponent(g);

		if (!Movement.debug)
		{
			// draw the background
			g.drawImage(Player.bg, 0, 0, 500, 500, null);

			// draw rectangle in the top left
			g.setColor(new Color(250, 250, 178));
			g.fillRect(0, 0, 330, 61);
			g.setColor(Color.BLACK);

			// draw text strings 
			g.drawString("Click to create a new player at the mouse position", 3, 13);
			g.drawString("Click an existing player to mark them active", 3, 27);
			g.drawString("Use the arrow keys to move the active player", 3, 41);
			g.drawString("Right click to toggle debug information", 3, 55);
		}

		// draw each player in the arraylist
		// (they are passed the Graphics object, and then tell it how to draw them)
		for (Player p : players)
			p.draw(g);
	}

	public void mouseClicked(MouseEvent e) 
	{
		// toggle debug mode when right mouse button is pressed
		if (SwingUtilities.isRightMouseButton(e))
		{
			Movement.debug = !Movement.debug;
			return;
		}

		// get the coordinates of the click
		int x = e.getX()/4*4;
		int y = e.getY()/4*4;

		// for each player, does the cursor lie in their coordinates?
		for (Player p : players)
		{
			// if so, make them the active player and return
			if (p.x < x+16 && p.x+32 > x-16 && y-16 < p.y+32 && y+16 > p.y)
			{
				activePlayer = p;
				return;
			}
		}

		// if no one was found at the current click, make a new one

		// Create a new player and give the mouse coordinates
		// as well as how many players currently exist (size of players arraylist)
		Player p = new Player(players.size(), x-16, y-16);

		// add this player to the arraylist
		players.add(p);

		// redraw the canvas
		repaint();

		// make this player active
		activePlayer = p;

	}

	public void keyPressed(KeyEvent e) 
	{
		// if a key is down, don't repeat the key press event
		if (keyCode != -1)
			return;

		// get keycode from event
		keyCode = e.getKeyCode();

		// send that keycode to the player to move
		activePlayer.move(keyCode, this);

		// repaint the canvas
		repaint();
	}

	public void updateTimer()
	{
		// move if a key is being held
		if (keyCode > 0)
			activePlayer.move(keyCode, this);

		// repaint the canvas
		repaint();
	}

	public void keyReleased(KeyEvent arg0) 
	{
		// set keycode to -1 to indicate no key is being held down
		keyCode = -1;
	}

	public boolean checkCollisions(int x, int y) 
	{
		// this runs a simple rectangle boundary check on all the players and the given coordinates
		for (Player p : players)
			if (p != activePlayer)
				if (p.x < x+32 && p.x+32 > x && y < p.y+32 && y+32 > p.y) 
					return false;

		return true;
	}

	// Below are blank methods that must exist due to the MouseListener specs
	// (they will be called on this panel when the specified action happens)
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	// these are also required, but by KeyListener
	public void keyTyped(KeyEvent arg0) {}


}
