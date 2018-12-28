/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author lenovo
 */
public class Wyglad extends JFrame{
    public Wyglad(){
        setSize(600,600);
        setTitle("Program do nauki języka Niemieckiego");        
        try{
            setIconImage(ImageIO.read(new File("obraz\\ikona.png"))); // ustawiam ikone
        }
        catch(IOException e){
            System.out.println(e.toString());
}
        add(new StronaGlowna());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
