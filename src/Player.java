import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player 
{
	private static BufferedImage[] images;
	private int color, direction, frame;
	public int x,y; // this is public information
	public static BufferedImage bg;
	private int speed = 4;
	
	// frame is the column of the image (step)
	// direction is 0=down, 1=up, 2=left, 3=right
	
	private Image sprite;
	
	public Player(int size, int x, int y) 
	{
		// the size mod 3 will determine what color this link is
		this.color = size % 3;
		
		// set the coordinates too
		this.x = x;
		this.y = y;
		
		// update and initialize the image
		updateImage();
	}
	
	public void updateImage()
	{
		// this is to control how link moves a little bit
		int thisFrame = frame/5;
		
		// set the appropriate image for the current direction and frame
		sprite = images[color].getSubimage(thisFrame*32, direction*32, 32, 32);
	}
	
	public void draw(Graphics g)
	{
		// draw the sprite on the given Graphics object
		g.drawImage(sprite,this.x,this.y,null);
		
		// draw bounding box in debug mode
		if (Movement.debug)
		{
			g.drawRect(x, y, 32, 32);
//			g.drawString(""+this.x+","+this.y, x-5, y-6);
		}
	}

	static void setImages(String[] im)
	{
		// initialize and set the length of the Image array 
		images = new BufferedImage[im.length];
		
		try {
			// create the background
			bg = ImageIO.read(Movement.class.getResource("res/grass.png"));
		} catch (IOException e1) {
			System.out.println("Failed to load background");
		}

		// Create a new Image object for each path passed
		for (int i=0; i<im.length; i++)
		{
			try {
				images[i] = ImageIO.read(Movement.class.getResource("res/"+im[i]));
			} catch (IOException e) {
				System.out.println("Error loading "+im[i]);
			}
		}
	}

	public void move(int keyCode, GamePanel gp) 
	{
		// advance the frame
		frame = (frame+1)%10;
		
		// move in the direction of the code
		switch (keyCode)
		{
		case KeyEvent.VK_UP:
			this.direction = 1;
			if (gp.checkCollisions(x, y-speed))
				this.y -= this.speed;
			break;
		case KeyEvent.VK_DOWN:
			this.direction = 0;
			if (gp.checkCollisions(x, y+speed))
				this.y += this.speed;
			break;
		case KeyEvent.VK_LEFT:
			this.direction = 2;
			if (gp.checkCollisions(x-speed, y))
				this.x -= this.speed;
			break;
		case KeyEvent.VK_RIGHT:
			this.direction = 3;
			if (gp.checkCollisions(x+speed, y))
				this.x += this.speed;
			break;
		default:
		}
		
		// update the image since we've moved
		updateImage();
	}

}
