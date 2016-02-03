import javax.swing.table.AbstractTableModel;

public class Modele extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	
	private final String[] entetes = {"Numero","Auteur","Date","Ligne","Commmentaire", "Ticket(s) repéré(s)"};
	private Liste liste;
	
	public Modele()
	{
		
	}
	
	public Modele(Liste liste)
	{
		this.liste=liste;
	}
	
	public Liste getListe()
	{
		return liste;
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
			return liste.getListe().get(rowIndex).getTicket();
			
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
			liste.getListe().get(row).setTicket((String)value);
			// Fonction gestion avancée 
			break;
			
		default:
			throw new IllegalArgumentException();
		}
	    fireTableCellUpdated(row, col);	  
	 }
	
	/*@Override
	public Class<? extends Object> getColumnClass(int c){
		Object object = getValueAt(0, c);
	    if(object == null) {
	        return Object.class;
	    if(getValueAt(0, c) instanceof IColorable) {
	        return ICarPart.class;
	    } else {
	        return getValueAt(0, c).getClass();
	    }
	}*/
	
}