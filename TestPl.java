/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author lenovo
 */
public class TestPl extends JPanel{
    private JButton back;
    private JLabel slowoNiemieckie;
    private JTextField slowoPolskie;
    private JPanel pole;
    private JButton sprawdz;
    private JButton dalej; 
    private Connection connect;
    private List<String[]> slowka;
    private List<String[]> bledy;
    private boolean [] czyZajeteSlowko;
    private boolean [] czyZajetyBlad;
    private int losowaneSlowko;
    private int losowanyBlad;
    private boolean czyToBlad;
    public TestPl(Connection connect){
        this.connect = connect;
        pobierzSlowka();              
        pobierzBledy(); 
        czyToBlad = false;
        setSize(600,600);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        back = new JButton(new ImageIcon("obraz\\back.png"));
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.setContentAreaFilled(false);
        back.addActionListener(new Powrot());
        add(back);
        add(Box.createRigidArea(new Dimension(0,5)));
        slowoNiemieckie = new JLabel("");
        slowoNiemieckie.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(slowoNiemieckie);
        add(Box.createRigidArea(new Dimension(0,5)));
        slowoPolskie = new JTextField();
        slowoPolskie.setMaximumSize(new Dimension(150,30));
        add(slowoPolskie);
        add(Box.createRigidArea(new Dimension(0,10)));
        pole = new JPanel();
        pole.setMaximumSize(new Dimension(500,200));
        pole.setLayout(new FlowLayout());
        sprawdz = new JButton("Sprawdź");
        sprawdz.setPreferredSize(new Dimension(150,30));
        sprawdz.addActionListener(new Sprawdz());
        pole.add(sprawdz);
        pole.add(Box.createRigidArea(new Dimension(150,0)));
        dalej = new JButton("Dalej");
        dalej.setPreferredSize(new Dimension(150,30));
        dalej.addActionListener(new Dalej());
        pole.add(dalej);
        losujSlowko();
        add(pole);
        setVisible(true);
    }
    class Powrot implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, TestPl.this);
            parent.getContentPane().removeAll();
            parent.add(new StronaGlowna());
            parent.validate();
            parent.repaint();
        }        
    }
    public void pobierzSlowka(){
        slowka = new ArrayList<String[]>();
        try{
            String sql = "SELECT * FROM niemiecki.slowka";
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String [] tmp = new String[2];
                tmp[0] = resultSet.getString("Polski");
                tmp[1] = resultSet.getString("Niemiecki");
                slowka.add(tmp);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        czyZajeteSlowko = new boolean[slowka.size()];        
        for(int i=0;i<slowka.size();i++){
            czyZajeteSlowko[i] = false;
        }
    }           
    public void pobierzBledy(){
        bledy = new ArrayList<String[]>();
        try{
            String sql = "SELECT * FROM niemiecki.bledy";
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String [] tmp = new String[2];
                tmp[0] = resultSet.getString("Polski");
                tmp[1] = resultSet.getString("Niemiecki");
                bledy.add(tmp);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        czyZajetyBlad = new boolean[bledy.size()];
        for(int i=0;i<bledy.size();i++){
            czyZajetyBlad[i] = false;
        }
    }
    public boolean losujSlowko(){ // Mowi nam czy już wszystkie slowka zostały wylosowane
        boolean full = false;        
        int licznik = 0;
        losowaneSlowko = (int) (Math.random() * slowka.size());        
        while(czyZajeteSlowko[losowaneSlowko]){
            if(licznik == slowka.size()){
                full = true;
                break;
            }
            losowaneSlowko = (int) (Math.random() * slowka.size());            
            licznik++;
        }
        czyZajeteSlowko[losowaneSlowko] = true;        
        slowoNiemieckie.setText(slowka.get(losowaneSlowko)[1]);  // Tym razem wybieramy słówko niemieckie    
        return full;
    }
    public boolean losujBlad(){
        boolean full = false;        
        int licznik = 0;
        losowanyBlad = (int) (Math.random() * bledy.size());
        while(czyZajetyBlad[losowanyBlad]){
            if(licznik == bledy.size()){
                full = true;
                break;
            }
            losowanyBlad = (int) (Math.random()*bledy.size());
            licznik++;
        }
        czyZajetyBlad[losowanyBlad] = true;
        slowoNiemieckie.setText(bledy.get(losowanyBlad)[1]); // Teraz sprawdzamy znjaomosc błedów
        return full;
    }
    //Teraz sprawdzamy poprawność polskiech słów
    public boolean sprawdzSlowko(){
        return slowoPolskie.getText().equals(slowka.get(losowaneSlowko)[0]);
    }
    public boolean sprawdzBlad(){
        return slowoPolskie.getText().equals(bledy.get(losowanyBlad)[0]);
    }
    class Sprawdz implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!czyToBlad){
                if(sprawdzSlowko()){
                    slowoPolskie.setBackground(Color.green);
                    slowoPolskie.setOpaque(true);
                }
                else{
                    slowoPolskie.setBackground(Color.red);
                    slowoPolskie.setOpaque(true);
                    try{
                        String sql = "INSERT INTO bledy (Polski,Niemiecki) VALUES (?,?)";
                        PreparedStatement prepare = connect.prepareStatement(sql);
                        //Obie poprawne wartosci, żeby było z czym w przyszlości sprawdzać
                        prepare.setString(1, slowka.get(losowaneSlowko)[0]);
                        prepare.setString(2, slowka.get(losowaneSlowko)[1]);
                        prepare.executeUpdate();
                        pobierzBledy();
                        JOptionPane.showMessageDialog(null,"Poprawna odpowiedź to "+slowka.get(losowaneSlowko)[0]);
                    }
                    catch(SQLException e1){
                        e1.printStackTrace();
                    }
                }
            }
            if(czyToBlad){
                if(sprawdzBlad()){
                    slowoPolskie.setBackground(Color.green);
                    slowoPolskie.setOpaque(true);
                }
                else{
                    slowoPolskie.setBackground(Color.red);
                    slowoPolskie.setOpaque(true);
                    try{
                        String sql = "INSERT INTO bledy (Polski,Niemiecki) VALUES (?,?)";
                        PreparedStatement prepare = connect.prepareStatement(sql);
                        //Obie poprawne wartosci, żeby było z czym w przyszlości sprawdzać
                        prepare.setString(1, bledy.get(losowanyBlad)[0]);
                        prepare.setString(2, bledy.get(losowanyBlad)[1]);
                        prepare.executeUpdate();
                        pobierzBledy();
                        JOptionPane.showMessageDialog(null,"Poprawna odpowiedź to "+bledy.get(losowanyBlad)[0]);
                    }
                    catch(SQLException e1){
                        e1.printStackTrace();
                    }
                }
            }
        }        
    }
    class Dalej implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            slowoPolskie.setBackground(Color.white);
            slowoPolskie.setOpaque(true);
            slowoPolskie.setText("");
            boolean tmp = losujSlowko();            
            boolean blad;
            if(tmp){                
                czyToBlad = true;
                blad = losujBlad();
                if(blad){                    
                    czyToBlad = false;
                    for(int i=0;i<slowka.size();i++){
                        czyZajeteSlowko[i] = false;
                    }
                    for(int i=0;i<bledy.size();i++){
                        czyZajetyBlad[i] = false;
                    }
                }
            }
        }        
    }
}
