
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTabbedPane onglets;
	 
	 private void ajoutOnglet(File f)
	 {
		 String nom=f.getName();
		 onglets.addTab(nom, table(new Liste(f.getAbsolutePath())));
		 onglets.setTabComponentAt((onglets.getTabCount())-1,new Barre(onglets));
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
				
			 	case "Detection de ticket":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit êre ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else {
			 			String msg = "Entrer le(s) ticket(s) souhaité(s)";
				 		JTextField pattern= new JTextField();
				 				 		
				 		Object [] parameters ={msg,pattern};
				 		JOptionPane.showConfirmDialog(null, parameters, "Filtres", JOptionPane.OK_CANCEL_OPTION);
				 	
				 		Modele model = getModele();
				 		String affichage = model.getListe().detectionTickets(pattern.getText());
						
				 		JScrollPane s = (JScrollPane) onglets.getSelectedComponent();
				 		JTable t = (JTable) s.getViewport().getView();
				 		
				 		/*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					    renderer.setHorizontalAlignment(SwingConstants.LEFT);
					    renderer.setBackground(Color.blue);
					    
					    for (int column = 0; column < t.getColumnCount(); column++)
					    {
					    	for (int row = 0; row < t.getRowCount()-2; row++)
						    {
					    		//t.getR
						 		TableCellRenderer cellRenderer = renderer;
						 		//Component c = t.getValueAt(row, column);
						 		Component c = t.prepareRenderer(cellRenderer, row, column);
						 				//t.getComponent(0);
						 				//
						 		c.setBackground(Color.blue);
						 		c.setForeground(Color.blue);
						 		//c.set
						    }
					    }*/
				 		
					    
					    TableCellRenderer renderer2 = new CustomTableCellRenderer();
					    try {
							t.setDefaultRenderer( Class.forName( "java.lang.String" ), renderer2 );
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    System.out.println("Coucou2");
				        //try
				       // {
				            //t.setDefaultRenderer(null, renderer2);
				            //setDefaultRenderer(renderer2);
				       // }
				        /*catch( ClassNotFoundException ex )
				        {
				            System.exit( 0 );
				        }*/
				 		
					    
					    
				 		/*t.getColumnModel().getColumn(0);
				 		int[] test = t.getSelectedRows();		
				 		//t.getTab
				 		
				 		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				 		
				 		comp.setBackground(Color.blue);
				 		
				 		//t.addRowSelectionInterval(0, 10);
				 		//t.setselection
				 		//t.setSelectionBackground(Color.blue);
				 		//t.selection
				 		
				 		/*model.getRowCount();
				 		model.*/
				 		
				 		//t.getColumnModel().getColumn(2).setCellRenderer(renderer);		// Marche mais colorie juste une colonne pas d'équivalent en row
					    
				 		model.fireTableDataChanged();
				 		//model.fireTableRowsUpdated(0, t.getRowCount());
				 		//System.out.println(affichage);
			 		}
			 		
			 	break;
				
			 	case "Ajouter filtre":
			 		if(onglets.getTabCount()==0)
			 		{
			 			JOptionPane.showConfirmDialog(null, "Au moins un fichier doit êre ouvert pour appliquer des filtres", "Filtres", JOptionPane.DEFAULT_OPTION);
			 		}
			 		else
			 		{
			 			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 			
			 			String author="", day1="",day2="",number="";
			 			String message = "Sélectionnez les filtres souhaités";

				 		JLabel auteur = new JLabel("Auteur");
				 		JTextField champ= new JTextField();
				 		JLabel dates = new JLabel("Dates");;
				 		JXDatePicker date1 = new JXDatePicker();
				 		JXDatePicker date2 = new JXDatePicker();
				 			date1.setFormats(format);
				 			date2.setDate(Calendar.getInstance().getTime());
				 			date2.setFormats(format);
				 		JLabel ticket = new JLabel("Numéro de ticket");
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
							m.getListe().filtres(author, day1, day2, number);
							m.fireTableDataChanged();
						} catch (ParseException e) {
							e.printStackTrace();
						}
				 	
			 		}
			 	break;
			 	
			 	case "Comparer deux listes":
			 		
			 	break;
			 }
		 }
	 };
	 
	 private void menu()
	 {
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
		 	JMenuItem ticket = new JMenuItem("Detection de ticket");
			ticket.addActionListener(MenuListener);
		 	JMenuItem filtres = new JMenuItem("Ajouter filtre");
			filtres.addActionListener(MenuListener);
			JMenuItem comparer = new JMenuItem("Comparer deux listes");
			comparer.addActionListener(MenuListener);
			outils.add(ticket);
			outils.add(filtres);
			outils.add(comparer);
					
				
		menuBar.add(fichier);
		menuBar.add(outils);
	 
		setJMenuBar(menuBar);
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
		 
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		 
		for (int column = 0; column < table.getColumnCount(); column++)
		{
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		    	TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		    	Component c = table.prepareRenderer(cellRenderer, row, column);
		    	int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		    	preferredWidth = Math.max(preferredWidth, width);

		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            //break;
		        }
		    }
		    tableColumn.setPreferredWidth( preferredWidth +20);
		}
		JScrollPane pane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
         
		 this.setContentPane(container);  
		 this.pack();
		 this.setExtendedState(Frame.MAXIMIZED_BOTH);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 

		/*Component test = onglets.getTabComponentAt(0);
		test.setBackground(Color.blue);*/
	}
	
}