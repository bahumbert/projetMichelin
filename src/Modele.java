import javax.swing.table.AbstractTableModel;

import com.sun.xml.internal.ws.util.StringUtils;

import org.apache.commons.*;
import java.lang.Object;

public class Modele extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	
	private final String[] entetes = {"Numero","Auteur","Date","Ligne","Commmentaire", "Ticket(s) repéré(s)"};
	private Liste liste;
	private Liste originale;
	
	public Modele()
	{
		
	}
	
	public Modele(Liste l)
	{
		this.liste=l;
		this.originale=new Liste(l);
	}
	
	public Liste getListe()
	{
		return liste;
	}
	
	public Liste getOriginale()
	{
		return originale;
	}
	
	public void retour()
	{
		this.liste=originale;
	}
	
	public void setListe(Liste l)
	{
		this.liste=l;
	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}

	@Override
	public int getRowCount() {
		return liste.getListe().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return liste.getListe().get(rowIndex).getNumeroVersion();
			
		case 1:
			return liste.getListe().get(rowIndex).getIdUtilisateur();

		case 2:
			return liste.getListe().get(rowIndex).getDate();

		case 3:
			return liste.getListe().get(rowIndex).getLignes();

		case 4:
			return liste.getListe().get(rowIndex).getCommentaire();
			
		case 5:
			return liste.getListe().get(rowIndex).getTickets();
			
		default:
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return true;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
	    
	    switch (col) {
		case 0:
			liste.getListe().get(row).setNumeroVersion((String)value);
		    break;
		    
		case 1:
			liste.getListe().get(row).setIdUtilisateur((String)value);
			break;
			
		case 2:
			liste.getListe().get(row).setDate((String)value);
			break;
			
		case 3:
			liste.getListe().get(row).setLignes((String)value);
			break;
			
		case 4:
			liste.getListe().get(row).setCommentaire((String)value);
			break;
			
		case 5:
			liste.getListe().get(row).setTickets((String)value);
			//gestionTicketsAvancee(row, (String)value);
			break;
			
		default:
			throw new IllegalArgumentException();
		}
	    fireTableCellUpdated(row, col);	  
	 }
	
	
	/*private void gestionTicketsAvancee(int row, String value){
		/*String tickets = (String) this.getValueAt(row,5);
		//System.out.println(tickets);
		
		System.out.println(row);
		
		int count = 0;
		int max = 0;
		String ticketRepete = "";
		
		String[] listTickets = tickets.split(",");
		for(String t : listTickets){
			
			count = org.apache.commons.lang3.StringUtils.countMatches((CharSequence)tickets, (CharSequence)t);
			
			if (count > max){
				ticketRepete = t;
				count = max;
			}
		}
		
		if (max != 0){
			this.setValueAt(ticketRepete,row,5);
			System.out.println("max!=0 row="+row);
		}
		else if (tickets.contains(",")) {
			this.setValueAt(tickets.substring(0, tickets.indexOf(",")),row,5);
			System.out.println(", row="+row);
		}
		else{
			this.setValueAt(tickets,row,5);
			System.out.println("1 row="+row);
		}
		*/
		
		/*System.out.println(row);
		System.out.println(value);
		
	}*/

	
}