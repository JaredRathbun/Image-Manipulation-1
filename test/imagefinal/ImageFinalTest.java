/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagefinal;

import org.junit.Test;
import static org.junit.Assert.*;
import rathbunfinal.ImageFinal;

/**
 *
 * @author zach
 */
public class ImageFinalTest {
    
    public ImageFinalTest()
    {
    }

    /**
     * Test of validOperations method, of class ImageFinal.
     */
    @Test
    public void testValidOperations()
    {
        String[] operations = {"grayscale", "addframe", "addsticker", "cartoonify"};
	
	assertTrue("trueTest", ImageFinal.validOperations(operations));
	
	String[] misspelledOperations = {"grysale", "addfme", "addscker", "cartoony"};
	
	assertFalse("falseTest(no arguments)", ImageFinal.validOperations(misspelledOperations));
    }

}
