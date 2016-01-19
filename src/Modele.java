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
			return liste.getListe().get(rowIndex).getVersion();

		case 1:
			return liste.getListe().get(rowIndex).getUser();

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
	
}