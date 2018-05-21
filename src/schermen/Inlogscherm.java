package schermen;

import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Inlogscherm extends JFrame implements ActionListener{

    private JTextField jtf;
    private JPasswordField jpf;
    private JButton inloggen;
    private JButton afsluiten;
    private JLabel titel = new JLabel("Inlogscherm");
    private Color orange = new Color(217, 121, 0);
    private Color orange2 = new Color(223, 221, 220);
    private JFrame mainFrame;

    public Inlogscherm() {

        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.add(new JLabel("Inlogscherm"));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(10, 15, 5, 15));

        JPanel middlePanel1 = new JPanel(new GridLayout(2,2));
        middlePanel1.setBackground(orange);
        middlePanel1.setBorder(new EmptyBorder(5, 15, 0, 15));

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(orange);
        bottomPanel.setBorder(new EmptyBorder(0, 15, 10, 15));

        JLabel jlGebruikersnaam = new JLabel("Gebruikersnaam:");
        jlGebruikersnaam.setForeground(Color.white);
        middlePanel1.add(jlGebruikersnaam);

        jtf = new JTextField();
        jtf.setBackground(orange2);
        jtf.setForeground(Color.darkGray);
        middlePanel1.add(jtf);

        JLabel jlWachtwoord = new JLabel("Wachtwoord:");
        jlWachtwoord.setForeground(Color.white);
        middlePanel1.add(jlWachtwoord);

        jpf = new JPasswordField();
        jpf.setBackground(orange2);
        jpf.setForeground(Color.darkGray);
        middlePanel1.add(jpf);


        inloggen = new JButton("Inloggen");
        inloggen.addActionListener(this);
        bottomPanel.add(inloggen);

        afsluiten = new JButton("Afsluiten");
        afsluiten.addActionListener(this);
        bottomPanel.add(afsluiten);


        //Create MainFrame
        mainFrame = new JFrame();
        mainFrame.setTitle("TZT Inlogscherm");
        mainFrame.setSize(500, 200);
        mainFrame.setLocationRelativeTo(null);

        //Add panels to MainFrame
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(middlePanel1, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        //Show mainframe
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == inloggen) {
            if(jtf.getText().equalsIgnoreCase("ADMIN") && hashCheck(new String(jpf.getPassword()))) {
                System.out.println("---: Logged in to system");
                jtf.setText("");
                jpf.setText("");
                new Hoofdmenu(mainFrame);
                mainFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null,"Geen goede combinatie, probeer het nog een keer", "Oeps..", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(ae.getSource() == afsluiten) {
            mainFrame.dispose();
        }
    }

    private boolean hashCheck(String passwordToHash) {
        String generatedPassword = null;
        String pw = "24f94740f0d1f4bbd13af4f076050be1";
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            if(pw.equals(generatedPassword)) {
                return true;
            }
            return false;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
