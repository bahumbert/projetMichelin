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
        
        
        if(table.getValueAt(row, 5)!=null && table.getValueAt(row, 5).toString().contains(",")) {
        	cell.setBackground( Color.red );
        }
        else if(table.getValueAt(row, 5)==null || table.getValueAt(row, 5).equals("")) {
        	cell.setBackground( Color.orange );
        }
        else {
        	cell.setBackground( Color.white );
        }
        
        /*if(column == 5)
        {
        	
        	System.out.println(table.getValueAt(row, column));
        	//System.console().writer().println((String)value);
               cell.setBackground( Color.red );
               // You can also customize the Font and Foreground this way
               // cell.setForeground();
               // cell.setFont();
        }
        else {
        	cell.setBackground( Color.white );
        }*/
        return cell;
    }
}