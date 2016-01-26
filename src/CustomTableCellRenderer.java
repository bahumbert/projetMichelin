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
        
        //Object o = table.getValueAt(row, column);
        /*System.console().writer().println(row);
        System.console().writer().println(column);
        System.console().writer().println(o);*/
        
        /*if(column == 1){
        	 cell.setBackground( Color.red );
        }
        else {
        	cell.setBackground( Color.white );
        }*/
        //Object o = null;
        /*System.console().writer().println(value);
        for(int i=0;i<table.getRowCount();i++){
        	o = table.getValueAt(i, 5);
        	System.console().writer().println(o);
        }*/
        
        
        if(row == 0){																//(String)value == "WEOSI-195"
    
               cell.setBackground( Color.red );
               // You can also customize the Font and Foreground this way
               // cell.setForeground();
               // cell.setFont();
        }
        else {
        	cell.setBackground( Color.white );
        }
        return cell;
    }
}