package schermen;

import general.Validate;
import klant.Klant;
import klant.KlantDAODerby;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NieuweKlant extends JFrame implements ActionListener{

    private JTextField voornaamInvoer;
    private JTextField achternaamInvoer;
    private JTextField emailInvoer;
    private JTextField telInvoer;
    private JTextField straatInvoer;
    private JTextField huisnummerInvoer;
    private JTextField postcodeInvoer;
    private JTextField plaatsInvoer;
    private JButton klantToevoegen;
    private Color orange = new Color(217, 121, 0);
    private JFrame hoofdScherm;
    private JFrame mainFrame;

    public NieuweKlant(JFrame hoofdScherm) {
        this.hoofdScherm = hoofdScherm;

        //Create HeaderPanel
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBorder(new EmptyBorder(10, 15, 5, 15));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(new JLabel("Nieuwe klant aanmaken"));

        //Create MiddlePanel
        JPanel middlePanel1 = new JPanel(new GridLayout(9, 2));
        middlePanel1.setBorder(new EmptyBorder(5, 15, 0, 15));
        middlePanel1.setBackground(orange);

        //Create BottomPanel
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBorder(new EmptyBorder(0, 15, 10, 15));
        bottomPanel.setBackground(orange);

        JLabel voornaam = new JLabel("Voornaam:");
        voornaam.setForeground(Color.white);
        middlePanel1.add(voornaam);

        voornaamInvoer = new JTextField(10);
        voornaamInvoer.setForeground(Color.darkGray);
        middlePanel1.add(voornaamInvoer);

        JLabel achternaam = new JLabel("Achternaam:");
        achternaam.setForeground(Color.white);
        middlePanel1.add(achternaam);

        achternaamInvoer = new JTextField(10);
        achternaamInvoer.setForeground(Color.darkGray);
        middlePanel1.add(achternaamInvoer);

        JLabel email = new JLabel("E-mailadres:");
        email.setForeground(Color.white);
        middlePanel1.add(email);

        emailInvoer = new JTextField(10);
        emailInvoer.setForeground(Color.darkGray);
        middlePanel1.add(emailInvoer);

        JLabel tel = new JLabel("Telefoonnummer:");
        tel.setForeground(Color.white);
        middlePanel1.add(tel);

        telInvoer = new JTextField(10);
        telInvoer.setForeground(Color.darkGray);
        middlePanel1.add(telInvoer);

        JLabel straat = new JLabel("Straat:");
        straat.setForeground(Color.white);
        middlePanel1.add(straat);

        straatInvoer = new JTextField(10);
        straatInvoer.setForeground(Color.darkGray);
        middlePanel1.add(straatInvoer);

        JLabel huisnummer = new JLabel("Huisnummer:");
        huisnummer.setForeground(Color.white);
        middlePanel1.add(huisnummer);

        huisnummerInvoer = new JTextField(10);
        huisnummerInvoer.setForeground(Color.darkGray);
        middlePanel1.add(huisnummerInvoer);

        JLabel postcode = new JLabel("Postcode:");
        postcode.setForeground(Color.white);
        middlePanel1.add(postcode);

        postcodeInvoer = new JTextField(10);
        postcodeInvoer.setForeground(Color.darkGray);
        middlePanel1.add(postcodeInvoer);

        JLabel plaats = new JLabel("Plaats:");
        plaats.setForeground(Color.white);
        middlePanel1.add(plaats);

        plaatsInvoer = new JTextField(10);
        plaatsInvoer.setForeground(Color.darkGray);
        middlePanel1.add(plaatsInvoer);

        klantToevoegen = new JButton("Klant toevoegen");
        klantToevoegen.addActionListener(this);
        bottomPanel.add(klantToevoegen);



        //Create MainFrame
        mainFrame = new JFrame();
        mainFrame.setTitle("TZT Nieuwe klant aanmaken");
        mainFrame.setSize(500, 400);
        mainFrame.setLocationRelativeTo(null);

        //Add panels to MainFrame
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(middlePanel1, BorderLayout.CENTER);
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

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == klantToevoegen) {
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
                Klant k = new Klant(voornaamInvoer.getText(), achternaamInvoer.getText(), emailInvoer.getText(), telInvoer.getText(), straatInvoer.getText(), huisnummerInvoer.getText(), postcodeInvoer.getText(), plaatsInvoer.getText());
                int sk = new KlantDAODerby().saveToDB(k);
                if(sk != 0) {
                    k.setKlantID(sk);
                    new OverzichtBestaandeKlant(k, hoofdScherm, sk);
                    mainFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null,"Opslaan van klant mislukt, probeer het later nog eens!", "Oeps..", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
