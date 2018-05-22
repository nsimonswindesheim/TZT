package schermen;

import general.Validate;
import klant.Klant;
import klant.KlantDAODerby;
import locatie.Locatie;
import locatie.LocatieDAODerby;
import verzending.Verzending;
import verzending.VerzendingDAODerby;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class OverzichtBestaandeKlant extends JFrame implements ActionListener {

    private JTextField voornaamInvoer;
    private JTextField achternaamInvoer;
    private JTextField emailInvoer;
    private JTextField telInvoer;
    private JTextField straatInvoer;
    private JTextField huisnummerInvoer;
    private JTextField postcodeInvoer;
    private JTextField plaatsInvoer;
    private JButton klantUpdaten;
    private JButton nieuwRoute;
    private Color orange = new Color(217, 121, 0);
    private JFrame mainFrame;
    private Klant klant;
    private JFrame hoofdscherm;

    public OverzichtBestaandeKlant(Klant klant, JFrame hoofdScherm, int klantID) {

        this.klant = klant;
        this.hoofdscherm = hoofdScherm;

        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.add(new JLabel("Klant overzicht"));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(10, 15, 5, 15));

        JPanel westPanel1 = new JPanel(new GridLayout(10, 2));
        westPanel1.setBackground(orange);
        westPanel1.setBorder(new EmptyBorder(5, 15, 0, 15));

        JPanel middlePanel = new JPanel(new FlowLayout());
        middlePanel.setBackground(orange);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(orange);
        bottomPanel.setBorder(new EmptyBorder(0, 15, 10, 15));


        JLabel id = new JLabel("Id:");
        id.setForeground(Color.white);
        westPanel1.add(id);

        JLabel idText = new JLabel(Integer.toString(klantID));
        idText.setForeground(Color.white);
        westPanel1.add(idText);

        JLabel voornaam = new JLabel("Voornaam:");
        voornaam.setForeground(Color.white);
        westPanel1.add(voornaam);

        voornaamInvoer = new JTextField(10);
        voornaamInvoer.setForeground(Color.darkGray);
        voornaamInvoer.setText(klant.getVoornaam());
        westPanel1.add(voornaamInvoer);

        JLabel achternaam = new JLabel("Achternaam:");
        achternaam.setForeground(Color.white);
        westPanel1.add(achternaam);

        achternaamInvoer = new JTextField(10);
        achternaamInvoer.setForeground(Color.darkGray);
        achternaamInvoer.setText(klant.getAchternaam());
        westPanel1.add(achternaamInvoer);

        JLabel email = new JLabel("E-mailadres:");
        email.setForeground(Color.white);
        westPanel1.add(email);

        emailInvoer = new JTextField(10);
        emailInvoer.setForeground(Color.darkGray);
        emailInvoer.setText(klant.getEmail());
        westPanel1.add(emailInvoer);

        JLabel tel = new JLabel("Telefoonnummer:");
        tel.setForeground(Color.white);
        westPanel1.add(tel);

        telInvoer = new JTextField(10);
        telInvoer.setForeground(Color.darkGray);
        telInvoer.setText(klant.getTelefoonnummer());
        westPanel1.add(telInvoer);

        JLabel straat = new JLabel("Straat:");
        straat.setForeground(Color.white);
        westPanel1.add(straat);

        straatInvoer = new JTextField(10);
        straatInvoer.setForeground(Color.darkGray);
        straatInvoer.setText(klant.getLocatie().getStraat());
        westPanel1.add(straatInvoer);

        JLabel huisnummer = new JLabel("Huisnummer:");
        huisnummer.setForeground(Color.white);
        westPanel1.add(huisnummer);

        huisnummerInvoer = new JTextField(10);
        huisnummerInvoer.setForeground(Color.darkGray);
        huisnummerInvoer.setText(klant.getLocatie().getHuisnummer());
        westPanel1.add(huisnummerInvoer);

        JLabel postcode = new JLabel("Postcode:");
        postcode.setForeground(Color.white);
        westPanel1.add(postcode);

        postcodeInvoer = new JTextField(10);
        postcodeInvoer.setForeground(Color.darkGray);
        postcodeInvoer.setText(klant.getLocatie().getPostcode());
        westPanel1.add(postcodeInvoer);

        JLabel plaats = new JLabel("Plaats:");
        plaats.setForeground(Color.white);
        westPanel1.add(plaats);

        plaatsInvoer = new JTextField(10);
        plaatsInvoer.setForeground(Color.darkGray);
        plaatsInvoer.setText(klant.getLocatie().getPlaats());
        westPanel1.add(plaatsInvoer);

        klantUpdaten = new JButton("Klant updaten");
        klantUpdaten.addActionListener(this);
        bottomPanel.add(klantUpdaten);

        nieuwRoute = new JButton("Nieuwe Verzending");
        nieuwRoute.addActionListener(this);
        bottomPanel.add(nieuwRoute);


        //Create MainFrame
        mainFrame = new JFrame();
        mainFrame.setTitle("TZT Klant Overzicht");
        mainFrame.setSize(500, 400);
        mainFrame.setLocationRelativeTo(null);

        //Add panels to MainFrame
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(westPanel1, BorderLayout.WEST);
        mainFrame.add(middlePanel, BorderLayout.CENTER);
        mainFrame.add(getVerzendingen(klant.getKlantID()), BorderLayout.EAST);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        //Show mainframe
        mainFrame.setVisible(true);

        //Before close show hoofdScherm
        mainFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                hoofdScherm.setVisible(true);
            }
        });
    }

    private JScrollPane getVerzendingen(int klantId) {
        ArrayList<Integer> verzendingen = new VerzendingDAODerby().getAllVerzendingFromKlant(klantId);
        JPanel scrollable = new JPanel();
        scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));

        for(int id:verzendingen) {
            final JLabel object = new JLabel("Verzending: "+id);
            object.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            object.addMouseListener(new MouseListener()
            {
                public void mouseClicked (MouseEvent e) {
                    if (e.getSource() == object) {
                        mainFrame.setVisible(false);
                        new OverzichtRouteVerzending(id, mainFrame, hoofdscherm);
                    }
                }
                public void mouseEntered(MouseEvent arg0) {}
                public void mouseExited(MouseEvent arg0) {}
                public void mousePressed(MouseEvent arg0) {}
                public void mouseReleased(MouseEvent arg0) {}
            });
            scrollable.add(object);
        }
        JScrollPane scrollPane = new JScrollPane(scrollable);
        scrollPane.setPreferredSize(new Dimension(200, 250));
        return scrollPane;
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == klantUpdaten) {
            String[][] arrays = {
                    {"voornaam", voornaamInvoer.getText(), "min:2,max:100"},
                    {"achternaam", achternaamInvoer.getText(), "required,min:2,max:100"},
                    {"email", emailInvoer.getText(), "required,email,max:256"},
                    {"telefoonnummer", telInvoer.getText(), "required,min:6,max:15"},
                    {"straat", straatInvoer.getText(), "required,min:3,max:100"},
                    {"huisnummer", huisnummerInvoer.getText(), "required,min:1,max:6"},
                    {"postcode", postcodeInvoer.getText(), "required,min:6,max:6"},
                    {"plaats", plaatsInvoer.getText(), "required,min:3,max:35"},
            };

            ArrayList<String> errors = Validate.multiple(arrays);
            if(errors.size() > 0) {
                String errorString = "";
                for (String error:errors) {
                    errorString += "- " + error + "\n";
                }
                JOptionPane.showMessageDialog(null,errorString, "Oeps..", JOptionPane.ERROR_MESSAGE);
            } else {
                klant.setVoornaam(voornaamInvoer.getText());
                klant.setAchternaam(achternaamInvoer.getText());
                klant.setEmail(emailInvoer.getText());
                klant.setTelefoonnummer(telInvoer.getText());
                Locatie locatie = new LocatieDAODerby().newLocatie(straatInvoer.getText(), huisnummerInvoer.getText(), postcodeInvoer.getText(), plaatsInvoer.getText());
                klant.setLocatie(locatie);
                if (new KlantDAODerby().saveToDB(klant) != 0) {
                    System.out.println("---: Klant updated");
                    JOptionPane.showMessageDialog(null, "De klant is succesvol bijgewerkt!", "Succes", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Er is iets mis gegaan tijdens het opslaan van de klant, probeer het later nog eens!", "Oeps..", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(ae.getSource() == nieuwRoute) {
            JTextField straat = new JTextField();
            JTextField huisnummer = new JTextField();
            JTextField postcode = new JTextField();
            JTextField plaats = new JTextField();

            JPanel myPanel = new JPanel(new GridLayout(4,2));
            myPanel.setSize(100,150);
            myPanel.add(new JLabel("Straat:"));
            myPanel.add(straat);
            myPanel.add(new JLabel("Huisnummer:"));
            myPanel.add(huisnummer);
            myPanel.add(new JLabel("Postcode:"));
            myPanel.add(postcode);
            myPanel.add(new JLabel("Plaats:"));
            myPanel.add(plaats);

            int result = JOptionPane.showConfirmDialog(null, myPanel,"Voer het bezorg adres in", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                if(!straat.equals("") && !huisnummer.equals("") && !postcode.equals("") && !plaats.equals("")) {
                    Verzending v = new Verzending(klant, new LocatieDAODerby().newLocatie(straat.getText(), huisnummer.getText(), postcode.getText(), plaats.getText()));
                    if(v.calcRoute()) {
                        System.out.println("---: Created new route");
                        mainFrame.setVisible(false);
                        new OverzichtRouteVerzending(v, mainFrame, hoofdscherm);
                    } else {
                        JOptionPane.showMessageDialog(null,"Geen route mogelijk van en naar dit adres", "Oeps..", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Vul alle velden correct in!", "Oeps..", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
}

