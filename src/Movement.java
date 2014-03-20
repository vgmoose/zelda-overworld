
public class Movement 
{
	public static boolean debug = false;

	public static void main(String[] args)
	{
		// These are the paths of the images that will be used
		String[] images = {"link_sprites.png","link_red.png","blue_links.png"};
		
		// Give these image paths to Player 
		Player.setImages(images);
		
		// Create main window
		GameWindow window = new GameWindow();
		
		// Display the window
		window.setVisible(true);
		
		// To demonstrate modularity, uncomment the below two lines
		// to get a second instance to appear!
//		GameWindow window2 = new GameWindow();
//		window2.setVisible(true);
	}
}
