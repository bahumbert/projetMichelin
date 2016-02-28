import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.*;

/**
 * Code copié collé de :
 * https://docs.oracle.com/javase/tutorial/uiswing/examples/components/
 * TabComponentsDemo
 */ 
public class Barre extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTabbedPane panel;

    public Barre(final JTabbedPane onglets) {
        if (onglets == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        panel = onglets;
        
        JLabel label = new JLabel() {
			private static final long serialVersionUID = 1L;

			public String getText() {
                int i = panel.indexOfTabComponent(Barre.this);
                if (i != -1) {
                    return panel.getTitleAt(i);
                }
                return null;
            }
        };        
		
        add(label);
        JButton button = new TabButton();
        add(button);
    }

    private class TabButton extends JButton implements ActionListener {
        /**
		 * 
		 */
		private static final long serialVersionUID = 3L;

		public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Fermer cet onglet");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = panel.indexOfTabComponent(Barre.this);
            if (i != -1) {
                panel.remove(i);
            }
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}