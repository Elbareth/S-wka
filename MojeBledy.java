/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author lenovo
 */
public class MojeBledy extends JPanel{
    private JButton back;
    private JPanel baza;
    private JTable tabelka; 
    private Vector nazwaKolumn = new Vector();   
    private Vector <Vector<String>> lista = new Vector <Vector<String>>();
    private Connection connect;
    public MojeBledy(Connection connect){
        this.connect = connect;
        setSize(600,600);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        back = new JButton(new ImageIcon("obraz\\back.png"));
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.setContentAreaFilled(false);
        back.addActionListener(new Powrot());
        add(back);
        add(Box.createRigidArea(new Dimension(150,5)));
        baza = new JPanel();
        baza.setLayout(new FlowLayout());
        baza.setAlignmentX(Component.CENTER_ALIGNMENT);
        baza.setMaximumSize(new Dimension(600,350));        
        boolean pusta = true;
        try{
            String sql = "SELECT * FROM niemiecki.bledy";
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
        TableCellRenderer cellRender = new JTableButtonRenderer(); //Żeby mieć możliwość usuwania wyników
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
        setVisible(true);
    }
    class Powrot implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, MojeBledy.this);
            parent.getContentPane().removeAll();
            parent.add(new StronaGlowna());
            parent.validate();
            parent.repaint();
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
                String sql = "UPDATE niemiecki.bledy SET "+nazwaKolumny+ " = ? WHERE ID = ?;";
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
                    String sql = "DELETE FROM niemiecki.bledy WHERE ID = ?;";
                    PreparedStatement prepare = connect.prepareStatement(sql);
                    prepare.setInt(1,idNumer);
                    prepare.executeUpdate();
                    JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, MojeBledy.this);
                    parent.getContentPane().removeAll();
                    parent.add(new MojeBledy(connect));
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
