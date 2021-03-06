import javax.swing.table.AbstractTableModel;

import java.lang.Object;

public class Modele extends AbstractTableModel																		// G�re les donn�es
{
	private static final long serialVersionUID = 1L;
	
	private final String[] entetes = {"Numero","Auteur","Date","Commmentaire", "Ticket(s) rep�r�(s)"};				// Colonnes
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
	public Object getValueAt(int rowIndex, int columnIndex) {															// Retourne la valeur d'une cellule
		switch (columnIndex) {
		case 0:
			return liste.getListe().get(rowIndex).getNumeroVersion();
			
		case 1:
			return liste.getListe().get(rowIndex).getIdUtilisateur();

		case 2:
			return liste.getListe().get(rowIndex).getDate();
			
		case 3:
			return liste.getListe().get(rowIndex).getCommentaire();
			
		case 4:
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
	public void setValueAt(Object value, int row, int col) {															// Modifie la valeur d'une cellule
	    
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
			liste.getListe().get(row).setCommentaire((String)value);
			break;
			
		case 4:
			liste.getListe().get(row).setTickets((String)value);
			break;
			
		default:
			throw new IllegalArgumentException();
		}
	    fireTableCellUpdated(row, col);	  
	 }
	
}