import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTabbedPane onglets;
	 
	 private void ajoutOnglet(File f)										// Ajoute un nouvel onglet
	 {
		 int index;
		 try{
			 String nom=f.getName();
			 onglets.addTab(nom, table(new Liste(f.getAbsolutePath())));	// Création de la liste correspondante à l'onglet
			 index=(onglets.getTabCount())-1;
			 onglets.setTabComponentAt(index,new Barre(onglets));
			 onglets.setSelectedIndex(index);
		 }
		 catch(Exception e){
			 throw e;
		 }
	 }
	 
	 private void ajoutOnglet(Liste l)
	 {
		 int index;
		 try{
			 index=(onglets.getTabCount());
			 onglets.addTab("temp", table(l));
			 onglets.setTabComponentAt(index,new Barre(onglets));
			 onglets.setSelectedIndex(index);
		 }
		 catch(Exception e){
			 throw e;
		 }
	 }
	 
	 public Modele getModele()												// Récupère le Modèle (les données)
	 {
		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
	 	JTable t = (JTable) s.getViewport().getView();
	 	Modele m = (Modele) t.getModel();
	 	
	 	return m;
	 }
	 
	 ActionListener MenuListener = new ActionListener() {					// Actions du menu
		 public void actionPerformed(ActionEvent event) {
			 switch(event.getActionCommand())
			 {
				case "Importer un fichier...":								// Importation fichier
					FileDialog fd = new FileDialog(new JFrame(), "Choisissez un fichier", FileDialog.LOAD);
					fd.setDirectory(".");
					fd.setVisible(true);

					if(fd.getFile()!=null)
					{
						File fichier;
						fichier = new File(fd.getDirectory()+fd.getFile());
						try{
							ajoutOnglet(fichier);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				break;
				
			 	case "Importer depuis le serveur...":						// Importation svn log depuis serveur
			 					 		
			 		JFileChooser fac = new JFileChooser(".");
			 		fac.setCurrentDirectory(new java.io.File("."));
			 		fac.setDialogTitle("Repository : ");
			 		
			 		fac.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			 		fac.setAcceptAllFileFilterUsed(false);
			 		
			 		String emplacement = "";
			 	    if (fac.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			 	    	emplacement = fac.getSelectedFile().toString();
			 	    }

			 	    if (emplacement != ""){
			 	    	
			 	    	
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");		// Fenêtre des filtres sur svn log
			 			
			 			String message = "Sï¿½lectionnez les filtres souhaités";

				 		JLabel max = new JLabel("Nombre maximum");
				 		JTextField champMax= new JTextField("500");
				 		JLabel revision = new JLabel("Numéro de révision");
				 		JLabel borneInf = new JLabel("Borne inférieure :");
				 		JTextField champRevision1= new JTextField();
				 		JLabel borneSup = new JLabel("Borne Supérieure :");
				 		JTextField champRevision2= new JTextField("HEAD");
				 		JLabel dates = new JLabel("Dates");
				 		JXDatePicker date1 = new JXDatePicker();
				 		JXDatePicker date2 = new JXDatePicker();
				 			date1.setFormats(format);
				 			date2.setDate(Calendar.getInstance().getTime());
				 			date2.setFormats(format);
				 		
				 		
				 		Object [] params ={message,max,champMax,revision,borneInf,champRevision1,borneSup,champRevision2,dates,date1,date2};
				 		int res = JOptionPane.showConfirmDialog(null, params, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 		
				 		if (res == 0){				 	
				 			String valMax="", valDay1="",valDay2="", valRevision1="", valRevision2="", parametres = "";
				 			
				 			
					 		if(champMax.getText()!=null){									// "Encodage" des filtres en flags svn log
					 			valMax=champMax.getText();
					 			parametres += " -l " + valMax;
					 		}
					 		
					 		if(!champRevision1.getText().equals("")){
					 			valRevision1=champRevision1.getText();
					 			if(!champRevision2.getText().equals("")){
						 			valRevision2=champRevision2.getText();
						 			parametres += " -r " + valRevision1 + ":" + valRevision2;
						 		}
					 			else{
					 				parametres += " -r " + valRevision1;
					 			}
					 			
					 		}
					 		else if (!champRevision2.getText().equals("")){
					 			valRevision2=champRevision2.getText();
					 			parametres += " -r 1:" + valRevision2;
					 		}
					 		else {
	
						 		if(date1.getDate()!=null){
						 			valDay1=format.format(date1.getDate());
						 			valDay2=format.format(date2.getDate());
						 			parametres += " -r {" + valDay1 + "}:{" + valDay2+"}";
						 		}
						 		else {
						 			valDay2=format.format(date2.getDate());
						 			parametres += " -r {" + valDay2+"}";
						 		}
					 		}

				 			String cmd[] = {"cmd.exe", "/C", "svn log " + parametres + " --xml > svnlog.xml"};		// Commande à exécuter
					 		String path = "";
					 		try {
								Runtime r = Runtime.getRuntime();
								
								InputStream ips;
								try {
									ips = new FileInputStream("./pathTortoise.txt");
									InputStreamReader ipsr=new InputStreamReader(ips);								// Récupération du Path pour svn log
									BufferedReader br=new BufferedReader(ipsr);
									
						 			String ligne = "";
						 			
									while ((ligne=br.readLine())!=null){
										path += ligne;
									}
									
									br.close(); 
								} 
								catch (FileNotFoundException e) {
									System.out.println("Le fichier de configuration n'a pas été trouvé");
									path = "C:\\Program Files (x86)\\TortoiseSVN\\bin;C:\\Program Files\\TortoiseSVN\\bin";
									File fichier = new File("./pathTortoise.txt");
							 		try {
										FileWriter fw = new FileWriter(fichier);
										fw.write(path);
										fw.close();
									} catch (IOException e2) {
										e2.printStackTrace();
									}
								} 
								catch (IOException e) {
									e.printStackTrace();
									path = "C:\\Program Files (x86)\\TortoiseSVN\\bin;C:\\Program Files\\TortoiseSVN\\bin";
								} 
								
								final Process p = r.exec(cmd, new String[]{"Path="+path},  new File(emplacement));				// Exécution de la commande
								
								BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
								BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

								String s;
								String result = "";
								while ((s = stdInput.readLine()) != null) {
									result = result + s + "\n";
								}
								stdInput.close();
								String err = "";
								while ((s = stdErr.readLine()) != null) {														// Récupération de l'output de la commande
									err = err + s + "\n";
								}
								
								stdErr.close();
								
								if (err.equals("")){
									File fichier = new File(emplacement+"\\svnlog.xml");										// Création d'un fichier xml avec le résultat de la commande
									try{
										ajoutOnglet(fichier);																	// Et ouverture de ce fichier
									}
									catch(Exception e){
										System.out.println("Erreur "+e.toString());
									}
								}
								else {
									JOptionPane.showMessageDialog(onglets, "Erreur : "+err);
								}
					 		}
					 		catch(Exception e) {
								System.out.println("Erreur d'exécution " + cmd + e.toString());
					        }
						}
			 	    }
				 	break;




			 	case "Sauvegarder...":																							// Export des données
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour sauvegarder", "Sauvegarder", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
						try {
							Modele model = getModele();
							model.getListe().sauver();
						} catch (IOException e) {
							e.printStackTrace();
						}
			 		}
			 	break;
			 	
			 	case "Exporter les tickets...":																					// Export des tickets
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour effectuer l'export", "Export des tickets", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
						try {
							Modele model = getModele();
							model.getListe().export();
						} catch (IOException e) {
							e.printStackTrace();
						}
			 		}
			 	break;
			 		
			 	case "Quitter":
			 		setVisible(false);
			 		dispose();
			 		break;

			 /************ Menu Outils **************/
			 		
			 	case "Appliquer des filtres":																					// Filtres
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
			 			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 			
			 			String author="", day1="",day2="",number1="",number2="";
			 			String message = "Sélectionnez les filtres souhaités";

				 		JLabel auteur = new JLabel("Auteur");
				 		JTextField champ1= new JTextField();
				 		JLabel dates = new JLabel("Dates");;
				 		JXDatePicker date1 = new JXDatePicker();
				 		JXDatePicker date2 = new JXDatePicker();
				 			date1.setFormats(format);
				 			date2.setDate(Calendar.getInstance().getTime());
				 			date2.setFormats(format);
				 		JLabel ticket = new JLabel("Numéro de révision");
				 		JLabel borneInf = new JLabel("Borne inférieure :");
				 		JTextField champ2 = new JTextField();
				 		JLabel borneSup = new JLabel("Borne Supérieure :");
				 		JTextField champ3= new JTextField();
				 		
				 		Object [] params ={message,auteur,champ1,dates,date1,date2,ticket,borneInf,champ2,borneSup,champ3};
				 		JOptionPane.showConfirmDialog(null, params, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 		
				 		if(!champ1.getText().equals(""))
				 			author=champ1.getText();

				 		if(date1.getDate() != null && !date1.getDate().equals(""))
				 			day1=format.format(date1.getDate());
				 		
				 		day2=format.format(date2.getDate());
				 		
				 		if(!champ2.getText().equals(""))
				 			number1=champ2.getText();
				 		
				 		if(!champ3.getText().equals(""))
				 			number2=champ3.getText();
				 		
				 		Modele m = getModele();
				 		try {
							Liste l=m.getListe().filtres(author, day1, day2, number1,number2);				// Appel de la fonction des filtres
							m.setListe(l);
							m.fireTableDataChanged();
						} 
				 		catch (ParseException e) {
							e.printStackTrace();
						}
				 	
			 		}
			 	break;
			 	
			 	case "Enlever tous les filtres":															// Suppression des filtres
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour supprimer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
						Modele model = getModele();
			 			model.retour();
			 			model.fireTableDataChanged();
			 			for (int row = 0; row < model.getRowCount(); row++){								// Suppression d'une éventuelle détection de tickets
				 			model.setValueAt(" ", row, 4);
				 		}
			 			model.fireTableDataChanged();
			 		}
			 	break;
			 	
				case "Detecter des tickets":																	// Détection tickets
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour rechercher des tickets", "Tickets", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else {
			 			Modele model = getModele();
			 			InputStream ips;
			 			String tickets = "";
						try {
							ips = new FileInputStream("./tickets.txt");
							InputStreamReader ipsr=new InputStreamReader(ips);
							BufferedReader br=new BufferedReader(ipsr);
							
				 			String ligne = "";
				 			
							while ((ligne=br.readLine())!=null){
								tickets += ligne;
							}
							
							br.close(); 
						} 
						catch (FileNotFoundException e) {
							System.out.println("Le fichier des tickets n'a pas été trouvé");
						} 
						catch (IOException e) {
							e.printStackTrace();
						} 

			 			String msg = "Entrer le(s) ticket(s) souhaité(s)";
				 		JTextField pattern= new JTextField(tickets);
				 				 		
				 		Object [] parameters ={msg,pattern};
				 		int res = JOptionPane.showConfirmDialog(null, parameters, "Tickets", JOptionPane.OK_CANCEL_OPTION);
			 			
				 		if (res == 0 && !pattern.getText().equals("")){
												 		
					 		for (int row = 0; row < model.getRowCount(); row++){					// Reset des données de la colonne des tickets
					 			model.setValueAt("", row, 4);
					 		}
					 		
					 		File fichier = new File("./tickets.txt");
					 		try {
								FileWriter fw = new FileWriter(fichier);
								fw.write(pattern.getText());
								fw.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
					 		
					 		
					 		/*String affichage =*/ model.getListe().detectionTickets(pattern.getText());					// Appel fonction de détection tickets
							
					 		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
					 		JTable t = (JTable) s.getViewport().getView();													// Appel de la coloration des lignes
						    TableCellRenderer renderer2 = new CustomTableCellRenderer();
						    t.setDefaultRenderer(Object.class, renderer2);													


				 		}
				 		else if (pattern.getText().equals("")){

				 			for (int row = 0; row < model.getRowCount(); row++){
					 			model.setValueAt(" ", row, 4);
					 		}
				 			
				 			JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
					 		JTable t = (JTable) s.getViewport().getView();
				 			TableCellRenderer renderer2 = new CustomTableCellRenderer();
							
						    t.setDefaultRenderer(Object.class, renderer2);
				 			
				 		}
				 		model.fireTableDataChanged();

			 		}
			 		
			 	break;
			 	
				case("Annuler la detection"):
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour annuler la détection", "Détection", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else {
						Modele model = getModele();
						for (int row = 0; row < model.getRowCount(); row++){
				 			model.setValueAt(" ", row, 4);
				 		}
						model.fireTableDataChanged();
			 		}
				
			 	break;

			 	case("Trouver les similitudes"):											// Comparaisons d'onglets
				case("Trouver les differences"):
			 		if(onglets.getTabCount()<2)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins deux fichiers doivent être ouverts pour la comparaison", "Comparaison", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			Modele mo = getModele();
			 			Modele m2 = null;
			 			Boolean ok = false;
					 	String s;
					 	JPanel control = new JPanel();
					 	control.setLayout(new GridLayout(6,1));
					 	ButtonGroup group = new ButtonGroup();

					 	for(int i=0;i<onglets.getTabCount();i++)
					 	{
					 		JRadioButton radioButton = new JRadioButton(onglets.getTitleAt(i));
					 		group.add(radioButton);
					 		control.add(radioButton);
					 	}
					 	int res= JOptionPane.showConfirmDialog(null, control, "Comparaison", JOptionPane.OK_CANCEL_OPTION);
					 	
					 	if(res==0)
					 	{
					 		if(group.getSelection()!=null)
					 		{
					 			int i=0;
					 			for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
					 	            AbstractButton button = buttons.nextElement();

					 	            if (button.isSelected()) {
					 	            	JTable jt =(JTable)((JScrollPane)onglets.getComponentAt(i)).getViewport().getView();
					 	            	m2=(Modele)jt.getModel();
					 	            	ok=true;
					 	            }
					 	            i++;
					 	        }
					 		}
					 	}
					 	if(ok)
					 	{
					 		Liste compare;
							if(event.getActionCommand()=="Trouver les similitudes"){
								compare=mo.getListe().comparerIdentique(m2.getListe());
								s="similitude";
							}
							else{
								compare=mo.getListe().comparerDifferent(m2.getListe());
								s="différence";
							}
							
							if(compare.liste.size()==0)
								JOptionPane.showConfirmDialog(null, "Aucune "+s+" n'a été trouvée", "Comparaison", JOptionPane.DEFAULT_OPTION);

							else
								ajoutOnglet(compare);
					 	}
			 		}
				break;
			 }
		 }
	 };
	 
	 private void menu()
	 {
		 JMenuBar menuBar = new JMenuBar();
	 
		 JMenu nouveau = new JMenu("Nouveau");
		 JMenuItem ouvrir = new JMenuItem("Importer un fichier...");
		 nouveau.add(ouvrir);
		 ouvrir.addActionListener(MenuListener);
		 	
		JMenuItem log = new JMenuItem("Importer depuis le serveur...");
		 nouveau.add(log);
		 log.addActionListener(MenuListener);
	 
		 JMenuItem sauvegarder = new JMenuItem("Sauvegarder...");
		 sauvegarder.addActionListener(MenuListener);
		 nouveau.add(sauvegarder);
		 
		 JMenuItem exporter = new JMenuItem("Exporter les tickets...");
		 exporter.addActionListener(MenuListener);
		 nouveau.add(exporter);
				
		 JMenuItem quitter = new JMenuItem("Quitter");
		 quitter.addActionListener(MenuListener);
		 nouveau.add(quitter);
	 
		 JMenu outils = new JMenu("Outils");
		 JMenu tickets = new JMenu("Tickets");
		 	JMenuItem addTicket = new JMenuItem("Detecter des tickets");
		 	addTicket.addActionListener(MenuListener);
			JMenuItem delTicket = new JMenuItem("Annuler la detection");
			delTicket.addActionListener(MenuListener);
			tickets.add(addTicket);
			tickets.add(delTicket);
			
		 	JMenu filtres = new JMenu("Filtres");
		 	JMenuItem appliquer = new JMenuItem("Appliquer des filtres");
			appliquer.addActionListener(MenuListener);
			JMenuItem enlever = new JMenuItem("Enlever tous les filtres");
		 	enlever.addActionListener(MenuListener);
		 	filtres.add(appliquer);
			filtres.add(enlever);
			

			JMenu comparer = new JMenu("Comparer");
			JMenuItem egal = new JMenuItem("Trouver les similitudes");
			egal.addActionListener(MenuListener);
			JMenuItem different = new JMenuItem("Trouver les differences");
			different.addActionListener(MenuListener);
			comparer.add(egal);
			comparer.add(different);
			
			outils.add(tickets);
			outils.add(filtres);
			outils.add(comparer);
					
				
		menuBar.add(nouveau);
		menuBar.add(outils);
	 
		setJMenuBar(menuBar);
	}
	 
	 public static void resizeColumnWidth(JTable table) {										// Resize des colonnes
		    int cumulativeActual = 0;
		    int padding = 15;
		    int max =700;
		    for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
		        int width = 50; // Taille min
		        
		        TableColumn column =table.getColumnModel().getColumn(columnIndex);
		        for (int row = 0; row < table.getRowCount(); row++) {
		            TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
		            Component comp = table.prepareRenderer(renderer, row, columnIndex);
		            width = Math.max(comp.getPreferredSize().width + padding, width);
		        }
		        
		        if (columnIndex < table.getColumnCount() - 1) {
		        	if (width>max){
		        		width=max;
		        	}
		            column.setPreferredWidth(width);
		            column.setMaxWidth(width);
		            cumulativeActual += column.getWidth();
		        } else {
		        	//Dernière colonne 
		            column.setPreferredWidth((int) table.getSize().getWidth() - cumulativeActual);
		        }
		    }
		}
	 
	 private void updateRowHeights(JTable table)															// Resize des lignes
	 {
	     for (int row = 0; row < table.getRowCount(); row++)
	     {
	         int rowHeight = table.getRowHeight();
	         int column = 3;
	         
	         Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
	         rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);

	         table.setRowHeight(row, rowHeight);
	     }
	 }
	 
	private JScrollPane table(Liste liste)																// Instanciation de la JTable
	{
		JTable table;
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.LEFT);
	    renderer.setBackground(Color.lightGray);
	        
		Modele modele=new Modele(liste);
		table = new JTable(modele);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modele);
		table.setRowSorter(sorter);
		table.getTableHeader().setDefaultRenderer(renderer);
		
		JScrollPane pane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 
		resizeColumnWidth(table);
		updateRowHeights(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		return pane;
	}
	
	public Fenetre(String name)																// Constructeur
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