/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niemiecki;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author lenovo
 */
public class JTableButtonRenderer extends JButton implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //JButton delete = (JButton) value;   
        JButton delete = new JButton();
        delete.setIcon(new ImageIcon("obraz\\delete1.png"));
        delete.setContentAreaFilled(false);      
        return delete;
    }
    
}
