import java.awt.Component;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/*public class CustomTableCellRenderer extends DefaultTableCellRenderer 
{
	static final long serialVersionUID = 1;
	
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
		
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (table.getValueAt(row, 5).toString().equals(" ")){
        	cell.setBackground( Color.white );
        }
        else if(table.getValueAt(row, 5)!=null && table.getValueAt(row, 5).toString().contains("(") || table.getValueAt(row, 5).toString().contains(" ")) {
        	cell.setBackground( Color.orange );
        }
        else if(table.getValueAt(row, 5)==null || table.getValueAt(row, 5).equals("")) {
        	cell.setBackground( Color.yellow );
        }
        else {
        	cell.setBackground( Color.white );
        }
        
        return cell;
    }
}*/

public class CustomTableCellRenderer extends JTextArea implements TableCellRenderer {
    public CustomTableCellRenderer() {
      setLineWrap(true);
      setWrapStyleWord(true);
   }
    
    
    //https://community.oracle.com/thread/1362611?start=0&tstart=0
 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      
	  setText((String) value);    
	  table.setRowHeight(row, getPreferredSize().height+15);
	  
	  int lastCol = table.getColumnCount()-1;
      
	    if (table.getValueAt(row,lastCol).toString().equals(" ")){
		  	this.setBackground( Color.white );
		  }
		  else if(table.getValueAt(row, lastCol)!=null && table.getValueAt(row, lastCol).toString().contains("(") || table.getValueAt(row, lastCol).toString().contains(" ")) {
		  	this.setBackground( Color.orange );
		  }
		  else if(table.getValueAt(row, lastCol)==null || table.getValueAt(row, lastCol).equals("")) {
	      	this.setBackground( Color.yellow );
	      }
	      else {
	      	this.setBackground( Color.white );
	      }
      
      return this;
  }
} 

/*public class CustomTableCellRenderer extends DefaultTableCellRenderer
{
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    
    int lastCol = table.getColumnCount()-1;
       
    if (table.getValueAt(row,lastCol).toString().equals(" ")){
	  	this.setBackground( Color.white );
	  }
	  else if(table.getValueAt(row, lastCol)!=null && table.getValueAt(row, lastCol).toString().contains("(") || table.getValueAt(row, lastCol).toString().contains(" ")) {
	  	this.setBackground( Color.orange );
	  }
	  else if(table.getValueAt(row, lastCol)==null || table.getValueAt(row, lastCol).equals("")) {
      	this.setBackground( Color.yellow );
      }
      else {
      	this.setBackground( Color.white );
      }
      
      return this;
  }


}*/
