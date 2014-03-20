import javax.swing.JFrame;


public class GameWindow extends JFrame 
{
	// field variable: canvas to go in this window
	private GamePanel canvas;
	
	public GameWindow()
	{
		// create a new canvas
		canvas = new GamePanel();
		
		// add that canvas inside this window
		super.add(canvas);
		
		// wrap ("pack") this window around the canvas that's been added
		super.pack();
	}
}