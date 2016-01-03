import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Onglet extends JScrollPane
{
	private static final long serialVersionUID = 1L;
	File fichier;
	Liste liste;
	
	public Onglet(File f)
	{
		super(new JTextArea());
		fichier=f;
		liste=new Liste(fichier.getAbsolutePath());
		JTextArea champ = new JTextArea(liste.toString());
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(champ);		
	}
	
	public File getFichier()
	{
		return fichier;
	}
	
	public Liste getListe()
	{
		return liste;
	}
	
}