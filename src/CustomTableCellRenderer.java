import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class CustomTableCellRenderer extends DefaultTableCellRenderer 
{
	static final long serialVersionUID = 1;
	
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
		
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (table.getValueAt(row, 4).toString().equals(" ")){
        	cell.setBackground( Color.white );
        }
        else if(table.getValueAt(row, 4)!=null && table.getValueAt(row, 4).toString().contains("(") || table.getValueAt(row, 4).toString().contains(" ")) {
        	cell.setBackground( Color.orange );
        }
        else if(table.getValueAt(row, 4)==null || table.getValueAt(row, 4).equals("")) {
        	cell.setBackground( Color.yellow );
        }
        else {
        	cell.setBackground( Color.white );
        }
        
        return cell;
    }
}