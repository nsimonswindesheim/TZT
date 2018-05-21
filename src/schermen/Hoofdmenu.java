package schermen;

import klant.Klant;
import klant.KlantDAODerby;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Hoofdmenu extends JFrame implements ActionListener {

    private JButton routeBerekenen;
    private JButton nieuweKlanten;
    private JButton bestaandeKlant;
    private JButton uitloggen;
    private JFrame mainFrame;
    private JFrame inlogFrame;


    public Hoofdmenu(JFrame inlogFrame) {

        this.inlogFrame = inlogFrame;

        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.add(new JLabel("TZT-app"));
        headerPanel.setBackground(Color.WHITE);

        JPanel middlePanel = new JPanel(new GridLayout(3,1));

        bestaandeKlant = new JButton("Bestaande klant");
        bestaandeKlant.addActionListener(this);
        bestaandeKlant.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        middlePanel.add(bestaandeKlant);

        nieuweKlanten = new JButton("Nieuwe klant");
        nieuweKlanten.addActionListener(this);
        nieuweKlanten.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        middlePanel.add(nieuweKlanten);

        uitloggen = new JButton("Uitloggen");
        uitloggen.addActionListener(this);
        uitloggen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        middlePanel.add(uitloggen);


        mainFrame = new JFrame();
        mainFrame.setTitle("TZT Hoofdmenu");
        mainFrame.setSize(500, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(middlePanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == nieuweKlanten) {
            mainFrame.setVisible(false);
            new NieuweKlant(mainFrame);
        } else if(ae.getSource() == bestaandeKlant) {
            boolean status = true;
            while (status) {
                String id = JOptionPane.showInputDialog("Wat is het klant id");
                if(id != null) {
                    try {
                        int klantId = Integer.parseInt(id);
                        Klant klant = new KlantDAODerby().getKlantById(klantId);
                        if(klant != null) {
                            System.out.println("---: Searched klant with id: "+klantId);
                            System.out.println("---: Find klant: "+klant.getVoornaam() + " " + klant.getAchternaam());
                            status = false;
                            mainFrame.setVisible(false);
                            new OverzichtBestaandeKlant(klant, mainFrame, klantId);
                        } else {
                            JOptionPane.showMessageDialog(null,"Geen klant gevonden met dit id", "Oeps..", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                        JOptionPane.showMessageDialog(null,"Geen geldig id", "Oeps..", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    status = false;
                }
            }
        } else if(ae.getSource() == uitloggen) {
            System.out.println("---: Logged out from system");
            inlogFrame.setVisible(true);
            mainFrame.dispose();
        }
    }
}
