/**
 * This class handles all operations on the Image.
 * 
 * @author Jared Rathbun
 */
package rathbunfinal;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Image
{
    private BufferedImage img; // An image.
    
    /**
     * Default Constructor sets the img equal to the file entered by the user.
     * 
     * @param file The image the user selects.
     * @throws IOException 
     */
    public Image(File file) throws IOException
    {
        img = ImageIO.read(file);
    }
    
    /**
     * This method returns the image.
     * 
     * @return The image the user selected.
     */
    public BufferedImage getImage()
    {
	return this.img;
    }
    
    /**
     * This method changes the color image in grayscale.
     */
    public void toGrayScale()
    {
	for (int i = 0; i < img.getWidth(); i++)
	{
	    for (int j = 0; j < img.getHeight(); j++)
	    {	
		// The image's pixel is set to the grayscale version of it.
		img.setRGB(i,j,rgbaToGray(img.getRGB(i,j)));
	    }
	} 
    }
    
    /**
     * This method adds a "frame" to the image.
     * 
     * @param frame The frame the user selects.
     * @return true or false, true if the frame was applied, and false if the frame is
     * bigger than the image.
     * @throws IOException 
     */
    public boolean addFrame(File frame) throws IOException
    {
	assert (frame != null);
	
	BufferedImage frameImg; // The frame as an image.
        BufferedImage croppedImg = img;

        frameImg = ImageIO.read(frame);

	// If the frame is larger than the image the method returns false.
	if (frameImg.getWidth() > img.getWidth() || 
		frameImg.getHeight() > img.getHeight())
	    return false;
	
	
	// The new cropped Image is made.
	img = croppedImg.getSubimage((img.getWidth() - frameImg.getWidth()) / 2, 
		(img.getHeight() - frameImg.getHeight()) / 2,frameImg.getWidth(),
		frameImg.getHeight());

	// The cropped image is applied.
	applyOverlay(frameImg);
	  
	return true;
    }
    
    /**
     * This method adds a sticker to the image the user enters.
     * 
     * @param sticker The sticker the user enters.
     * @return true or false, true if the sticker was applied, and false if the sticker is 
     * bigger than the image.
     * @throws IOException 
     */
    public boolean addSticker(File sticker) throws IOException
    {
	assert (sticker != null);
	
	BufferedImage stickerImg;

         stickerImg = ImageIO.read(sticker);
	 
	if (stickerImg.getWidth() > img.getWidth() || 
		stickerImg.getHeight() > img.getHeight())
                return false;

         // The sticker is applied to the image.
         applyOverlay(stickerImg);
	
	return true;
	
    }
    
    /**
     * This method turns the image into a cartoon.
     * 
     * @throws FileNotFoundException 
     */
    public void cartoonify() throws FileNotFoundException
    {	
	// The crayola palette.
	int[] palette = PixelColor.getPalette();
	
	// A 2-D Array with the same dimensions as the image.
	int[][] clusterColors = new int[img.getWidth()][img.getHeight()];
	
	clusterPixels(palette,clusterColors);
    }
    
    /**
     * This method returns the grayscale version of the entered rgba value. 
     * 
     * @param rgba An RGBA that is between 0 and 255.
     * @return The grayscale version of rgba. 
     */
    private int rgbaToGray(int rgba)
    {
	assert (rgba > 0) && (rgba < 256);
	
	// The red channel.
	int redChannel = (int) Math.floor((PixelColor.getRedChannel(rgba) + 
		PixelColor.getGreenChannel(rgba) + 
		PixelColor.getBlueChannel(rgba)) / 3 + .5);
	
	// The green channel.
	int greenChannel = (int) Math.floor((PixelColor.getRedChannel(rgba) + 
		PixelColor.getGreenChannel(rgba) + 
		PixelColor.getBlueChannel(rgba)) / 3 + .5);
	
	// The blue channel.
	int blueChannel = (int) Math.floor((PixelColor.getRedChannel(rgba) + 
		PixelColor.getGreenChannel(rgba) + 
		PixelColor.getBlueChannel(rgba)) / 3 + .5);
	
	// The encoded grayscale value is returned.
    	return PixelColor.encodeToRGBA(redChannel,greenChannel,blueChannel,
		PixelColor.getAlphaChannel(rgba));
    }
    
    /**
     * This method applies the image passed as a parameter to img.
     * 
     * @param overlay An image.
     */
    private void applyOverlay(BufferedImage overlay)
    {
	int pixelColor;
	
	for (int i = 0; i < overlay.getWidth(); i++)
	{
	    for (int j = 0; j < overlay.getHeight(); j++)
	    {
		pixelColor = overlay.getRGB(i,j); // The RGB value of the current pixel.
		
		// If the alpha value is not 0, the pixel is set to pixelColor.
		if (PixelColor.getAlphaChannel(pixelColor) != 0)
		    img.setRGB(i,j,pixelColor);
	    }
	} 
    }
    
    /**
     * This method replaces each pixel of the image with the closet version of the color 
     * in the crayola palette.
     * 
     * @param clusterColors The palette of the 120 crayola colors.
     * @param clusterMember A 2-D array that is the same dimensions of the image.
     */
    private void clusterPixels(int[] clusterColors, int[][] clusterMember)
    {
	assert (clusterMember.length == img.getHeight()) 
		&& (clusterMember[0].length == img.getWidth());

	for (int i = 0; i < clusterMember.length; i++)
	{  
            for (int j = 0; j < clusterMember[0].length; j++)
	    {
                  // The closest color in the palette to the pixel.
                  int bestColorIndex = 0;
                  
		for (int k = 0; k < clusterColors.length; k++)
		{
		    if (PixelColor.colorDistance(img.getRGB(i,j),clusterColors[k]) 
                            < PixelColor.colorDistance(img.getRGB(i,j),
                                    clusterColors[bestColorIndex]))
		    {
                        // The best color index is saved as k.
			bestColorIndex = k;			  
		    }

		   
		}
                  
		// Each pixel is set at the best index in the color palette.
		img.setRGB(i,j,clusterColors[bestColorIndex]);      
	    }
	}
	
    }
    
    /**
     * This method flips the image's colors to a negative value.
     */
    public void negativeImage()
    {
	int r, g, b; // The values for each channel.
	
	for (int i = 0; i < img.getWidth(); i++)
	{
	    for (int j = 0; j < img.getHeight(); j++)
	    {
		// Each pixel's channel is found.
		r = PixelColor.getRedChannel(img.getRGB(i,j));
		g = PixelColor.getGreenChannel(img.getRGB(i,j));
		b = PixelColor.getBlueChannel(img.getRGB(i,j));
		
		// The negative is set as (i,j) by subtracting the RGB value from 255.
		img.setRGB(i,j,PixelColor.encodeToRGBA(255 - r, 255 - g, 255 -b, 
			PixelColor.getAlphaChannel(img.getRGB(i, j))));
	    }
	}
    } 
}