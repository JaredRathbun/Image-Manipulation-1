/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagefinal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;
import static org.junit.Assert.*;
import rathbunfinal.Image;

/**
 *
 * @author zach
 */
public class ImageTest {
    
    public ImageTest()
    {
    }

    /**
     * Test of toGrayScale method, of class Image.
     * @throws java.io.IOException
     */
    @Test
    public void testToGrayScale() throws IOException
    {
        Image img1 = new Image(new File("mosley-bridge.jpg"));
        Image img2 = new Image(new File("mosley-gs.jpg"));
        
        img1.toGrayScale();
        
        assertTrue(isEqual(img1, img2));
    }

    /**
     * Test of addFrame method, of class Image.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddFrame() throws Exception
    {
        Image img1 = new Image(new File("mosley-bridge.jpg"));
        Image img2 = new Image(new File("mosley-sparkle.jpg"));
        
        boolean good = img1.addFrame(new File("sparkle-overlay.png"));
        
        assertTrue(good && isEqual(img1, img2));
        
        img1 = new Image(new File("mosley-bridge.jpg"));
        assertFalse(img1.addFrame(new File("sparkle-too-big.png")));
    }

    /**
     * Test of addSticker method, of class Image.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddSticker() throws Exception
    {
        Image img1 = new Image(new File("mosley-bridge.jpg"));
        Image img2 = new Image(new File("mosley-sticker.jpg"));
        
        boolean good = img1.addSticker(new File("wow-sticker.png"));
        
        assertTrue(good && isEqual(img1, img2));
        
        img1 = new Image(new File("mosley-bridge.jpg"));
        assertFalse("Bad sticker size", 
                img1.addSticker(new File("wow-sticker-too-big.png")));
    }

    /**
     * Test of cartoonify method, of class Image.
     * @throws java.lang.Exception
     */
    @Test
    public void testCartoonify() throws Exception
    {
        Image img1 = new Image(new File("mosley-bridge.jpg"));
        Image img2 = new Image(new File("mosley-cartoon.jpg"));
        
        img1.cartoonify();
        
        assertTrue(isEqual(img1, img2));
    }
    
    public boolean isEqual(Image img1, Image img2) throws IOException
    {
        BufferedImage i1;
        BufferedImage i2;
        
        // Save the image and then reload it, this way the colors are 
        // correct. This is because JPG introduces compression and 
        // some color channel values will change.
        ImageIO.write(img1.getImage(), "jpg", new File("tmp.jpg"));
        i1 = new Image(new File("tmp.jpg")).getImage();
        
        // Get the image from img2.
        i2 = img2.getImage();
        
        // Check to see if the images have the same width and height.
        if (i1.getWidth() != i2.getWidth() || 
            i1.getHeight() != i2.getHeight())
            return false;
        
        // Walk through the image and make sure the images are equal.
        for (int i = 0; i < i1.getWidth(); i++)
            for (int j = 0; j < i1.getHeight(); j++)
                if (i1.getRGB(i, j) != i2.getRGB(i, j))
                    return false;
        
        // At this point every pixel mathches, return true.
        return true;
    }
    
}
