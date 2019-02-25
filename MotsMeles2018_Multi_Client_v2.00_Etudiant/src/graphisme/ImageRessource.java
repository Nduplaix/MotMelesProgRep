package graphisme;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRessource  {


	private BufferedImage image;

	public ImageRessource(String image) {
		try {
			this.image = ImageIO.read(getClass().getResource(image));
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		
	}
	
	
	public BufferedImage getImage() {
		return image;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
