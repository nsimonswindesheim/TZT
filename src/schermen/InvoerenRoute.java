package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InvoerenRoute extends JFrame implements ActionListener {

    private JLabel startpunt;
    private JLabel eindpunt;
    private JTextField startpuntInvoeren;
    private JTextField eindpuntInvoeren;
    private JButton bevestigen;
    private JLabel titel = new JLabel("Nieuwe route invoeren");
    private Color orange = new Color(217, 121, 0);
    private Color orange2 = new Color(223, 221, 220);

    public InvoerenRoute() {

        FlowLayout flowLayout = new FlowLayout();
        JPanel headerPanel = new JPanel(flowLayout);
        headerPanel.add(this.titel);
        headerPanel.setBackground(Color.WHITE);

        startpunt = new JLabel("     Startpunt:");
        startpunt.setForeground(Color.white);
        add(startpunt);

        startpuntInvoeren = new JTextField(10);
        add(startpuntInvoeren);
        startpuntInvoeren.setBackground(orange2);
        startpuntInvoeren.setForeground(Color.darkGray);

        eindpunt = new JLabel("     Eindpunt:");
        eindpunt.setForeground(Color.white);
        add(eindpunt);

        eindpuntInvoeren = new JTextField(10);
        add(eindpuntInvoeren);
        eindpuntInvoeren.setBackground(orange2);
        eindpuntInvoeren.setForeground(Color.darkGray);

        GridLayout gridLayout = new GridLayout(3,2 );
        JPanel middlePanel = new JPanel(gridLayout);
        middlePanel.add(startpunt);
        middlePanel.add(startpuntInvoeren);
        middlePanel.add(eindpunt);
        middlePanel.add(eindpuntInvoeren);
        middlePanel.setBackground(orange);

        bevestigen = new JButton("Bevestig route");
        add(bevestigen);
        bevestigen.addActionListener(this);
        bevestigen.setBackground(Color.darkGray);
        bevestigen.setForeground(Color.white);

        FlowLayout flowLayout1 = new FlowLayout();
        JPanel bottomPanel = new JPanel(flowLayout1);
        bottomPanel.add(bevestigen);
        bottomPanel.setBackground(orange);

        JFrame mainFrame = new JFrame();
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(middlePanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setTitle("TZT Route invoeren");
        mainFrame.setSize(500, 200);
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        JOptionPane.showMessageDialog(null, "De route wordt berekend");
    }
}
