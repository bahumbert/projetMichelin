import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
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

public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTabbedPane onglets;
	 
	 private void ajoutOnglet(File f)
	 {
		 int index;
		 try{
			 String nom=f.getName();
			 onglets.addTab(nom, table(new Liste(f.getAbsolutePath())));
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
	 
	 public Modele getModele()
	 {
		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
	 	JTable t = (JTable) s.getViewport().getView();
	 	Modele m = (Modele) t.getModel();
	 	
	 	return m;
	 }
	 
	 ActionListener MenuListener = new ActionListener() {
		 public void actionPerformed(ActionEvent event) {
			 
			 switch(event.getActionCommand())
			 {
				case "Importer un fichier":
			 		JFileChooser fc = new JFileChooser(".");
					 FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers csv et xml.", "csv","xml");
			         fc.addChoosableFileFilter(filter);
			         fc.setAcceptAllFileFilterUsed(false);
			         
					 if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
						{
						 	File fichier;
							fichier = fc.getSelectedFile();
							try{
								ajoutOnglet(fichier);
							}
							catch(Exception e){
								e.printStackTrace();

							}
						}
				break;
				
			 	case "Importer depuis le serveur":
			 					 		
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
				 	    
				 		String cmd[] = {"cmd.exe", "/C", "svn log --xml > svnlog.xml"};
				 		try {
							Runtime r = Runtime.getRuntime();
							
							/*String test = System.getenv("Path");
							System.out.println(test);
							System.out.println(test.length());*/
							final Process p = r.exec(cmd, new String[]{"Path=C:\\Program Files (x86)\\TortoiseSVN\\bin;C:\\Program Files\\TortoiseSVN\\bin;E:\\Applications\\TortoiseSVN/bin"},  new File(emplacement));
							
							BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
							BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
							
	
							System.out.println("Commande :\n");
	
							int count = 0;
							String s;
							String result = "";
								while ((s = stdInput.readLine()) != null) {
									count++;
									result = result + s + "\n";
								}
							
							stdInput.close();
							
								count = 0;
								String err = "";
								while ((s = stdErr.readLine()) != null) {
									count++;
									err = err + s + "\n";
								}
								
							stdErr.close();
							
							//System.out.println("commande =" + cmd.toString() + "\nresult : " + count + " : " + result + " err="+err);
							
							if (err.equals("")){
								File fichier = new File(emplacement+"\\svnlog.xml");
								try{
									ajoutOnglet(fichier);
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
							System.out.println("erreur d'execution " + cmd + e.toString());
				        }
					}
				 	break;




			 	case "Sauvegarder...":
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour sauvegarder", "Sauvegarder", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			Modele m = getModele();
						try {
							m.getListe().sauver();
						} catch (IOException e) {
							e.printStackTrace();
						}
			 		}
			 	break;
			 	
			 	case "Exporter les tickets":
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour effectuer l'export", "Export des tickets", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			Modele m = getModele();
						try {
							m.getListe().export();
						} catch (IOException e) {
							e.printStackTrace();
						}
			 		}
			 	break;
			 		
			 	case "Imprimer...":
			 		if(onglets.getTabCount()==0){
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour imprimer", "Imprimer", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			
			 		}
			 		break;
			 		
			 	case "Quitter":
			 		setVisible(false);
			 		dispose();
			 		break;

			 /************ Menu Outils **************/
			 		
			 	case "Appliquer des filtres":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit �re ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
			 			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 			
			 			String author="", day1="",day2="",number="";
			 			String message = "Selectionnez les filtres souhaites";

				 		JLabel auteur = new JLabel("Auteur");
				 		JTextField champ= new JTextField();
				 		JLabel dates = new JLabel("Dates");;
				 		JXDatePicker date1 = new JXDatePicker();
				 		JXDatePicker date2 = new JXDatePicker();
				 			date1.setFormats(format);
				 			date2.setDate(Calendar.getInstance().getTime());
				 			date2.setFormats(format);
				 		JLabel ticket = new JLabel("Numero de commit");
				 		JTextField champ2= new JTextField();
				 		
				 		Object [] params ={message,auteur,champ,dates,date1,date2,ticket,champ2};
				 		JOptionPane.showConfirmDialog(null, params, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 		
				 		if(champ.getText()!=null)
				 			author=champ.getText();

				 		if(date1.getDate()!=null)
				 			day1=format.format(date1.getDate());
				 		
				 		day2=format.format(date2.getDate());
				 		
				 		if(champ2.getText()!=null)
				 			number=champ2.getText();
				 		
				 		Modele m = getModele();
				 		try {
							Liste l=m.getListe().filtres(author, day1, day2, number);
							m.setListe(l);
							m.fireTableDataChanged();
						} catch (ParseException e) {
							e.printStackTrace();
						}
				 	
			 		}
			 	break;
			 	
			 	case "Enlever tous les filtres":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit �re ouvert pour supprimer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
			 			Modele m=getModele();
			 			m.retour();
				 		m.fireTableDataChanged();
			 		}
			 	break;
			 	
				case "Detection de ticket":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit être ouvert pour trouver des tickets", "Tickets", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else {
			 			
			 			InputStream ips;
			 			String tickets = "";
						try {
							ips = new FileInputStream("./tickets.txt");
							InputStreamReader ipsr=new InputStreamReader(ips);
							BufferedReader br=new BufferedReader(ipsr);
							//System.out.println("Fichier ouvert");
							
				 			String ligne = "";
				 			
							while ((ligne=br.readLine())!=null){
								tickets += ligne;
							}
							
							br.close(); 
						} 
						catch (FileNotFoundException e) {
							System.out.println("Le fichier des tickets n'a pas �t� trouv�");
						} 
						catch (IOException e) {
							e.printStackTrace();
						} 

			 			String msg = "Entrer le(s) ticket(s) souhaité(s)";
				 		JTextField pattern= new JTextField(tickets);
				 				 		
				 		Object [] parameters ={msg,pattern};
				 		int res = JOptionPane.showConfirmDialog(null, parameters, "Tickets", JOptionPane.OK_CANCEL_OPTION);

			 			Modele model = getModele();
			 			
				 		if (res == 0 && !pattern.getText().equals("")){
					 		
					 		for (int row = 0; row < model.getRowCount(); row++){
					 			model.setValueAt("", row, model.getColumnCount()-1);
					 		}
					 		
					 		File fichier = new File("./tickets.txt");
					 		try {
								FileWriter fw = new FileWriter(fichier);
								fw.write(pattern.getText());
								fw.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					 		/*String affichage =*/model.getListe().detectionTickets(pattern.getText());
							
					 		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
					 		JTable t = (JTable) s.getViewport().getView();
					 		
						    TableCellRenderer renderer2 = new CustomTableCellRenderer();
						    
						    t.setDefaultRenderer(Object.class, renderer2);
						    //t.getColumnModel().getColumn(3).setCellRenderer(renderer2);
				 		}
				 		else if (pattern.getText().equals("")){

				 			for (int row = 0; row < model.getRowCount(); row++){
					 			model.setValueAt(" ", row, model.getColumnCount()-1);
					 		}
				 			
				 			JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
					 		JTable t = (JTable) s.getViewport().getView();
				 			TableCellRenderer renderer2 = new CustomTableCellRenderer();
				 		      
						    t.setDefaultRenderer(Object.class, renderer2);
				 			//t.getColumnModel().getColumn(3).setCellRenderer(renderer2);
				 		}
				 		model.fireTableDataChanged();

			 		}
			 		
			 	break;
			 	
			 	case("Trouver les similitudes"):
				case("Trouver les differences"):
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit �re ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else{
			 			Modele mo = getModele();
			 			JFileChooser filec = new JFileChooser(".");
						FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichiers csv.", "csv");
				        filec.addChoosableFileFilter(filtre);
				        filec.setAcceptAllFileFilterUsed(false);
				         
						if(filec.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
						{
						 	File fichier;
						 	String s;
							fichier = filec.getSelectedFile();

							Liste compare;
							if(event.getActionCommand()=="Trouver les similitudes"){
								compare=mo.getListe().comparerIdentique(new Liste(fichier.getAbsolutePath()));
								s="similitudes";
							}
							else{
								compare=mo.getListe().comparerDifferent(new Liste(fichier.getAbsolutePath()));
								s="différences";
							}
							
							if(compare.liste.size()==0)
								JOptionPane.showConfirmDialog(null, "Aucunes "+s+" n'ont ete trouvees", "Comparaison", JOptionPane.DEFAULT_OPTION);

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
		 JMenuItem ouvrir = new JMenuItem("Importer un fichier");
		 nouveau.add(ouvrir);
		 ouvrir.addActionListener(MenuListener);
		 	
		JMenuItem log = new JMenuItem("Importer depuis le serveur");
		 nouveau.add(log);
		 log.addActionListener(MenuListener);
	 
		 JMenuItem sauvegarder = new JMenuItem("Sauvegarder...");
		 sauvegarder.addActionListener(MenuListener);
		 nouveau.add(sauvegarder);
		 
		 JMenuItem exporter = new JMenuItem("Exporter les tickets");
		 exporter.addActionListener(MenuListener);
		 nouveau.add(exporter);
				
		 JMenuItem imprimer = new JMenuItem("Imprimer...");
		 imprimer.addActionListener(MenuListener);
		 nouveau.add(imprimer);
				
		 JMenuItem quitter = new JMenuItem("Quitter");
		 quitter.addActionListener(MenuListener);
		 nouveau.add(quitter);
	 
		 JMenu outils = new JMenu("Outils");
		 	JMenuItem ticket = new JMenuItem("Detection de ticket");
			ticket.addActionListener(MenuListener);
			
		 	JMenu filtres = new JMenu("Filtres");
		 	JMenuItem enlever = new JMenuItem("Enlever tous les filtres");
		 	enlever.addActionListener(MenuListener);
		 	JMenuItem appliquer = new JMenuItem("Appliquer des filtres");
			appliquer.addActionListener(MenuListener);
			filtres.add(enlever);
			filtres.add(appliquer);

			JMenu comparer = new JMenu("Comparer");
			JMenuItem egal = new JMenuItem("Trouver les similitudes");
			egal.addActionListener(MenuListener);
			JMenuItem different = new JMenuItem("Trouver les differences");
			different.addActionListener(MenuListener);
			comparer.add(egal);
			comparer.add(different);
			
			outils.add(ticket);
			outils.add(filtres);
			outils.add(comparer);
					
				
		menuBar.add(nouveau);
		menuBar.add(outils);
	 
		setJMenuBar(menuBar);
	}

	 public static void resizeColumnWidth(JTable table) {
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
	 
	 private void updateRowHeights(JTable table)
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
	 
	private JScrollPane table(Liste liste)
	{
		JTable table;
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.LEFT);
	    renderer.setBackground(Color.lightGray);
	        
		Modele modele=new Modele(liste);
		table = new JTable(modele);
		table.getTableHeader().setDefaultRenderer(renderer);
		
		JScrollPane pane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 
		resizeColumnWidth(table);
		updateRowHeights(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		return pane;
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
         
         this.setMinimumSize(new Dimension(800,400));
		 this.setContentPane(container);  
		 this.pack();
		 this.setExtendedState(Frame.MAXIMIZED_BOTH);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 

		/*Component test = onglets.getTabComponentAt(0);
		test.setBackground(Color.blue);*/
	}
	
}