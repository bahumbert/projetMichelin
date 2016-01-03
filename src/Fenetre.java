
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector; 


public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTabbedPane onglets;
	 
	 private void ajoutOnglet(File f)
	 {
		 String nom=f.getName();
		 onglets.addTab(nom, null, new Onglet(f));
		 onglets.setTabComponentAt((onglets.getTabCount())-1,new Barre(onglets));
	 }
	 
	 ActionListener MenuListener = new ActionListener() {
		 public void actionPerformed(ActionEvent event) {
			 
			 switch(event.getActionCommand())
			 {
			 	case "Ouvrir":
			 		JFileChooser fc = new JFileChooser(".");
					 FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers csv.", "csv");
			         fc.addChoosableFileFilter(filter);
			         fc.setAcceptAllFileFilterUsed(false);
			         
					 if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
						{
						 	File fichier;
							fichier = fc.getSelectedFile();
							ajoutOnglet(fichier);
						}
				break;
				
			 	case "Ajouter filtre":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit êre ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
			 			int i;
			 			String message = "Sélectionnez les filtres souhaités";
			 			String message2 = "Sélectionnez les fichiers sur lequels appliquer les filtres";
				 		JCheckBox auteur = new JCheckBox("Auteur");
				 		JCheckBox dates = new JCheckBox("Date");
				 		JCheckBox ticket = new JCheckBox("Numéro de ticket");
				 		
				 		Object [] params ={message,auteur,dates,ticket,message2};
				 		JOptionPane.showConfirmDialog(null, params, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 		
			 		}
			 	break;
			 	
			 	case "Comparer deux listes":
			 		
			 	break;
			 }
		 }
	 };
	 
	 private void menu(){
			JMenuBar menuBar = new JMenuBar();
	 
			JMenu fichier = new JMenu("Fichier");
				JMenuItem ouvrir = new JMenuItem("Ouvrir");
				fichier.add(ouvrir);
				ouvrir.addActionListener(MenuListener);
	 
				JMenuItem sauvegarder = new JMenuItem("Sauvegarder");
				sauvegarder.addActionListener(MenuListener);
				fichier.add(sauvegarder);
				
				JMenuItem imprimer = new JMenuItem("Imprimer");
				imprimer.addActionListener(MenuListener);
				fichier.add(imprimer);
				
				JMenuItem quitter = new JMenuItem("quitter");
				quitter.addActionListener(MenuListener);
				fichier.add(quitter);
	 
			JMenu outils = new JMenu("Outils");
				JMenuItem filtres = new JMenuItem("Ajouter filtre");
				filtres.addActionListener(MenuListener);
				JMenuItem comparer = new JMenuItem("Comparer deux listes");
				comparer.addActionListener(MenuListener);
				outils.add(filtres);
				outils.add(comparer);
					
				
			menuBar.add(fichier);
			menuBar.add(outils);
	 
			setJMenuBar(menuBar);
		}
	
	public Fenetre(String name)
	{
		 this.setTitle(name);
		 this.setLocationRelativeTo(null); 
		 
		 JPanel container = new JPanel();
		 onglets  = new JTabbedPane(SwingConstants.TOP);
		 container.add(onglets);
		 
		 BoxLayout bl=new BoxLayout(container,BoxLayout.Y_AXIS);
	     container.setLayout(bl);
		 
         menu();
         
		 this.setContentPane(container);  
		 this.pack();
		 this.setExtendedState(Frame.MAXIMIZED_BOTH);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
	}
	
}