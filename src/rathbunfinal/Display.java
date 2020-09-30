package rathbunfinal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This class is responsible for displaying a buffered image to the screen.
 *
 * @author Zach Kissel
 */
public class Display extends WindowAdapter {

    public static JFrame frame;
    
    /**
     * This method displays the image to the screen.
     *
     * @param img the image to display to the screen.
     */
    public static void displayImage(Image img)
    {
        
        JLabel imgLabel = new JLabel();

        // Create a new JFrame
        frame = new JFrame("Image Manipulation");

        // When the clicks close, dispose of the frame.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        imgLabel.setIcon(new ImageIcon(scaleImage(img.getImage())));

        // Display the frame.
        frame.getContentPane().add(imgLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                if (JOptionPane.showConfirmDialog(frame,
                        "Do you want to save this image?", "Save Image?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION)
                    System.exit(0);
                else
		try
		{
		    ImageFinal.saveImage(img.getImage());
		} catch (IOException ex)
		{
		    // Swallow exception.
		}

            }
        });
    }
    
    /**
     * This method scales an image to fit on 80% of the screen dimensions if the
     * image is bigger than 80% of the screen dimensions; otherwise the original
     * image is returned.
     *
     * @param img this is the image to scale.
     */
    private static java.awt.Image scaleImage(BufferedImage img)
    {
        // Get the dimensions of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Get 80% of the screen height and width. Thus preserving the 
        // aspect ratio of the monitor.
        double width = screenSize.getWidth() * 0.8;
        double height = screenSize.getHeight() * 0.8;

        // Don't allow upscaling.
        if (img.getWidth() < (int) width)
        {
            width = img.getWidth();
        }
        if (img.getHeight() < (int) height)
        {
            height = img.getHeight();
        }

        // Scale the image so it fits nicely on the screen.
        return img.getScaledInstance((int) width, (int) height,
                java.awt.Image.SCALE_SMOOTH);
    }
}
