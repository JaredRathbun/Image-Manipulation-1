/**
 * This is the driver class for the image manipulation program.
 *
 * @author Jared Rathbun
 */
package rathbunfinal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

 
public class ImageFinal
{
    public static void main(String[] args) throws IOException
    {
	if (args.length == 0 || validOperations(args) == false)
	{
	    System.out.println("Usage: imagemanip [ grayscale | addframe "
		    + "| addsticker | cartoonify | negative ]");
	    return;
	}
	
	// The user is prompted for the image.
	Image img = new Image(promptForFile("Select Image"));
	
	// The operations specified in the command line are performed on the image.
	processOperations(img, args);

	// The image is displayed.
	Display.displayImage(img);
    }
    
    /**
     * This method processes all of the operations specified from the command line.
     * 
     * @param image The image the user enters.
     * @param ops The command line arguments.
     * @throws IOException 
     */
    public static void processOperations(Image image, String[] ops) throws IOException
    {
	assert (ops.length != 0);
	
	for (String task : ops)
	{
	    switch (task)
	    {
		case "grayscale":  
		    image.toGrayScale();
		    break;   
		case "addframe":  
		    // If the frame is bigger than the image, a message box is displayed.
		    if (image.addFrame(promptForFile("Select Frame")) == false)
		    {
		      JOptionPane.showMessageDialog(null,"Frame is too big");
		      System.exit(0);
		    } 
		    break;
		case "addsticker": 		    
		    // If the frame is bigger than the image, a message box is displayed.
		    if (image.addSticker(promptForFile("Select Sticker")) == false)
		    {
		      JOptionPane.showMessageDialog(null,"Sticker is too big");
		      System.exit(0);
		    }
		    break;
		case "cartoonify": 
		    image.cartoonify();
		    break;
		case "negative":
		    image.negativeImage();
		    break;
	    }
	}
    }
    
    /**
     * This method checks to make sure the command line arguments are valid.
     * 
     * @param operations A 1-D Array of Strings. (The command line arguments)
     * @return True if the array's contents are valid, and false if they are not. 
     */
    public static boolean validOperations(String[] operations)
    {
	assert (operations.length >= 0) && (operations.length <= 4);
	
	int count = 0; // A counter.
	
	for (int i = 0; i < operations.length; i++)
	{
	    if (operations[i].equals("grayscale") || operations[i].equals("addframe") || 
		   operations[i].equals("addsticker") || operations[i].equals("cartoonify")
		    || operations[i].equals("negative"))
		count++;
	}
	
	return (count == operations.length);
    }
    
    /**
     * This method prompts the user for a image.
     * 
     * @param msg The title of the file chooser.
     * @return The file the user chooses.
     */
    public static File promptForFile(String msg)
    {
	assert (!msg.equals(""));
	
	String[] fileExtensions = {"jpg", "gif", "png"}; // The accepted file types.
	JFileChooser display = new JFileChooser(); // The object for the file chooser.
	
	FileNameExtensionFilter fileFilter = 
		new FileNameExtensionFilter("Images",fileExtensions);
	
	display.setDialogTitle(msg); // The file chooser title is set to msg.
	display.setFileFilter(fileFilter); // The file filter is applied.
	
	// Sets default directory on the file chooser to the project. (Easier to test)
	display.setCurrentDirectory(new java.io.File("."));
	
	if (display.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	    return display.getSelectedFile();
	else    
	    return null;   
    }
    
    /**
     * This method saves the image to a path the user selects.
     * 
     * @param img The image the user is going to save.
     * @throws IOException 
     */
    public static void saveImage(BufferedImage img) throws IOException
    {
	assert (img != null);
	
	String[] fileExtensions = {"jpg"}; // The file type the image will be saved as.
	JFileChooser display = new JFileChooser(); // The object for the file chooser.
	
	FileNameExtensionFilter fileFilter = 
                new FileNameExtensionFilter("JPG",fileExtensions);
	
	display.setFileFilter(fileFilter); // The file filter is applied.
	
        // The image is saved and written to specified location if the user hits "OK".
	if (display.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            ImageIO.write(img,"jpg",display.getSelectedFile());
    }

}
