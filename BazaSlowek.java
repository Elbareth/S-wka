/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author lenovo
 */
public class BazaSlowek extends JPanel{
    private JButton back;
    private JPanel baza; // Ważen do bazy danych!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111
    //Do wpisywania słówek
    private JPanel slowa;
    private JLabel polski;
    private JLabel niemiecki;
    private JPanel napis;
    private JTextField slowoPolskie;
    private JTextField slowoNiemieckie;
    //Do obsługi klawiatury
    private JPanel klawiatura;
    private JButton SS;
    private JButton maleE;
    private JButton duzeE;
    private JButton maleA;
    private JButton duzeA;
    private JButton maleO;
    private JButton duzeO;
    private JButton dodaj;
    private JTable tabelka; 
    private Vector nazwaKolumn = new Vector();   
    private Vector <Vector<String>> lista = new Vector <Vector<String>>();
    //private BazaDanych db = new BazaDanych();
    private Connection connect;
    public BazaSlowek(Connection connect){ 
        //db.polacz();
        this.connect  = connect;
        setSize(600,600);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        back = new JButton(new ImageIcon("obraz\\back.png"));
        back.setContentAreaFilled(false);
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.addActionListener(new Powrot());
        add(back);
        add(Box.createRigidArea(new Dimension(0,5)));
        baza = new JPanel();
        baza.setLayout(new FlowLayout());
        baza.setAlignmentX(Component.CENTER_ALIGNMENT);
        baza.setMaximumSize(new Dimension(600,350));
        boolean pusta = true;
        try{
            String sql = "SELECT * FROM niemiecki.slowka";
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);         
            while(resultSet.next()){
                Vector <String> ID = new Vector<String>();               
                ID.add(resultSet.getString("ID"));                
                ID.add(resultSet.getString("Polski"));               
                ID.add(resultSet.getString("Niemiecki"));                            
                lista.add(ID);  
                pusta = false;
            }
            if(pusta){ // W przypadku gdy nic nie mamy w bazie!!!!
                Vector <String> ID = new Vector<String>();               
                ID.add("");                
                ID.add("");               
                ID.add("");                            
                lista.add(ID);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        nazwaKolumn.add("ID");
        nazwaKolumn.add("Polski");
        nazwaKolumn.add("Niemiecki");
        nazwaKolumn.add("Usuń");
        TableCellRenderer cellRender = new JTableButtonRenderer();     
        tabelka = new JTable(lista,nazwaKolumn){
            @Override
            public boolean isCellEditable(int row, int column){
                //Kolumna 0 i 3 nie mogą być edytowane
                boolean tmp = true;
                if(column == 0 || column == 3) tmp = false;
                return tmp;
            }
        };
        tabelka.setMaximumSize(new Dimension(500,300));
        tabelka.setPreferredScrollableViewportSize(new Dimension(500,300));
        tabelka.setFillsViewportHeight(true);
        tabelka.getModel().addTableModelListener(new Tabela());  
        tabelka.getColumn("Usuń").setCellRenderer(cellRender);
        tabelka.addMouseListener(new Delete());
        JScrollPane scroll = new JScrollPane(tabelka);
        baza.add(scroll);
        add(baza);
        add(Box.createRigidArea(new Dimension(0,5)));
        slowa = new JPanel();
        slowa.setAlignmentX(Component.CENTER_ALIGNMENT);
        slowa.setSize(new Dimension(500,60));
        slowa.setLayout(new FlowLayout());
        slowa.add(Box.createRigidArea(new Dimension(40,0)));
        polski = new JLabel("Polski: ");
        slowa.add(polski);
        slowa.add(Box.createRigidArea(new Dimension(200,0)));
        niemiecki = new JLabel("Niemiecki: ");
        slowa.add(niemiecki);
        //add(Box.createRigidArea(new Dimension(0,5)));
        napis = new JPanel();
        napis.setAlignmentX(Component.CENTER_ALIGNMENT);
        napis.setSize(new Dimension(500,60));
        napis.setLayout(new FlowLayout());
        slowoPolskie = new JTextField();
        slowoPolskie.setPreferredSize(new Dimension(150,30));
        napis.add(slowoPolskie);
        napis.add(Box.createRigidArea(new Dimension(100,0)));
        slowoNiemieckie = new JTextField();
        slowoNiemieckie.setPreferredSize(new Dimension(150,30));
        napis.add(slowoNiemieckie);
        //napis.add(Box.createRigidArea(new Dimension(300,0)));
        add(slowa);
        add(napis);
        add(Box.createRigidArea(new Dimension(0,10)));
        klawiatura = new JPanel();
        klawiatura.setPreferredSize(new Dimension(200,60));
        klawiatura.setAlignmentX(Component.RIGHT_ALIGNMENT);
        klawiatura.setLayout(new GridLayout(0,3));
        SS = new JButton("ß");
        SS.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"ß");
            }
        });
        klawiatura.add(SS);
        maleA = new JButton("ӓ");
        maleA.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"ӓ");
            }
        });
        klawiatura.add(maleA);
        duzeA = new JButton("Ӓ");
        duzeA.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"Ӓ");
            }
        });
        klawiatura.add(duzeA);
        maleO = new JButton("ö");
        maleO.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"ö");
            }
        });
        klawiatura.add(maleO);
        duzeO = new JButton("Ö");
        duzeO.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"Ö");
            }
        });
        klawiatura.add(duzeO);
        maleE = new JButton("ё");
        maleE.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"ё");
            }
        });
        klawiatura.add(maleE);
        duzeE = new JButton("Ë");
        duzeE.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = slowoNiemieckie.getText();
                slowoNiemieckie.setText(tmp+"Ë");
            }
        });
        klawiatura.add(duzeE);
        add(klawiatura);        
        dodaj = new JButton("Dodaj");
        dodaj.setAlignmentX(Component.LEFT_ALIGNMENT);
        //dodaj.setPreferredSize(new Dimension(150,50));
        dodaj.setMaximumSize(new Dimension(150,50));
        dodaj.addActionListener(new Dodaj());
        add(dodaj);
        setVisible(true);
    }
    class Powrot implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, BazaSlowek.this);
            parent.getContentPane().removeAll();
            parent.add(new StronaGlowna());
            parent.validate();
            parent.repaint();
        }        
    }
    class Dodaj implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String sql = "INSERT INTO slowka (Polski, Niemiecki) VALUES (?,?)";
                PreparedStatement prepare = connect.prepareStatement(sql);
                prepare.setString(1, slowoPolskie.getText());
                prepare.setString(2, slowoNiemieckie.getText());
                prepare.executeUpdate();
                slowoPolskie.setText("");
                slowoNiemieckie.setText("");
                JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, BazaSlowek.this);
                parent.getContentPane().removeAll();
                parent.add(new BazaSlowek(connect));
                parent.validate();
                parent.repaint();
            }            
            catch(SQLException e1){
                e1.printStackTrace();
            }
        }
        
    }
    class Tabela implements TableModelListener{

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = tabelka.getEditingRow();
            int column = tabelka.getEditingColumn();          
            String wynik = (String) tabelka.getValueAt(row, column);
            String nazwaKolumny = (String) tabelka.getColumnName(column);
            String id = (String) tabelka.getValueAt(row, 0);  
            Integer idNumer = Integer.parseInt(id);            
            try{
                String sql = "UPDATE niemiecki.slowka SET "+nazwaKolumny+ " = ? WHERE ID = ?;";
                PreparedStatement prepare = connect.prepareStatement(sql);                
                prepare.setString(1, wynik);
                prepare.setInt(2, idNumer);                
                prepare.executeUpdate();
            }
            catch(SQLException e1){
                e1.printStackTrace();
            }
        }
        
    }
    class Delete implements MouseListener{
        
        public void metodaPomocnicza(){
            int column = tabelka.getSelectedColumn();
            if(column == 3){
                int row = tabelka.getSelectedRow();
                String id = (String) tabelka.getValueAt(row, 0);
                Integer idNumer = Integer.parseInt(id);
                try{
                    String sql = "DELETE FROM niemiecki.slowka WHERE ID = ?;";
                    PreparedStatement prepare = connect.prepareStatement(sql);
                    prepare.setInt(1,idNumer);
                    prepare.executeUpdate();
                    JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, BazaSlowek.this);
                    parent.getContentPane().removeAll();
                    parent.add(new BazaSlowek(connect));
                    parent.validate();
                    parent.repaint();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        @Override
         public void mouseClicked(MouseEvent e){
             metodaPomocnicza();
         }

        @Override
        public void mousePressed(MouseEvent e) {
            //metodaPomocnicza();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
}
