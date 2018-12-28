/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author lenovo
 */
public class StronaGlowna extends JPanel{
    private JFrame parent;
    private JButton bazaSlowek;
    private JButton testPlDe;
    private JButton testDePl;
    private JButton bledy;
    private BazaDanych db = new BazaDanych();
    private Connection connect;
    public StronaGlowna(){         
        connect = db.polacz();        
        setSize(600,600);
        //parent =(JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        add(Box.createRigidArea(new Dimension(0,50)));
        bazaSlowek = new JButton("Baza słówke (dodaj/usuń/modyfikuj)");
        bazaSlowek.setAlignmentX(Component.CENTER_ALIGNMENT);
        bazaSlowek.addActionListener(new Baza());
        add(bazaSlowek);
        add(Box.createRigidArea(new Dimension(0,5)));
        testPlDe = new JButton("Test Polsko - Niemiecki");
        testPlDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        testPlDe.addActionListener(new Niemiecki());
        add(testPlDe);
        add(Box.createRigidArea(new Dimension(0,5)));
        testDePl = new JButton("Test Niemiecko - Polski");
        testDePl.setAlignmentX(Component.CENTER_ALIGNMENT);
        testDePl.addActionListener(new Polski());
        add(testDePl);
        add(Box.createRigidArea(new Dimension(0,5)));
        bledy = new JButton("Moje Błędy");
        bledy.setAlignmentX(Component.CENTER_ALIGNMENT);
        bledy.addActionListener(new Bledy());
        add(bledy);
        add(Box.createRigidArea(new Dimension(0,50)));
        setVisible(true);
    }
    class Baza implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, StronaGlowna.this);
            parent.getContentPane().removeAll();            
            parent.add(new BazaSlowek(connect));
            parent.validate();
            parent.repaint();
        }
        
    }
    class Niemiecki implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, StronaGlowna.this);
            parent.getContentPane().removeAll();            
            parent.add(new TestDe(connect));
            parent.validate();
            parent.repaint();
        }        
    }
    class Polski implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, StronaGlowna.this);
            parent.getContentPane().removeAll();            
            parent.add(new TestPl(connect));
            parent.validate();
            parent.repaint();
        }  
    }
    class Bledy implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, StronaGlowna.this);
            parent.getContentPane().removeAll();            
            parent.add(new MojeBledy(connect));
            parent.validate();
            parent.repaint();
        } 
    }
}
