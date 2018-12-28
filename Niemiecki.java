/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import javax.swing.UIManager;

/**
 *
 * @author lenovo
 */
public class Niemiecki {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // select Look and Feel
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
            // start application
            new Wyglad();
        }
        catch (Exception ex) {
            
        }
         
    }    
}
