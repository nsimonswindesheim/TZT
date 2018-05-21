package schermen;

import route.Route;
import route.RouteDAODerby;
import route.RouteStep;
import verzending.Verzending;
import verzending.VerzendingDAODerby;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class OverzichtRouteVerzending extends JFrame implements ActionListener {

    private JLabel afhaalLocatie;
    private JLabel bezorgLocatie;
    private JLabel status;
    private JLabel afstand;
    private JLabel tijd;
    private JLabel kosten;
    private JFrame hoofdScherm;
    private JPanel bottomPanel;
    private Color orange = new Color(217, 121, 0);
    private JFrame mainFrame;
    private ArrayList<RouteStep> routeSteps;
    private JButton saveRoute;
    private Verzending verzending;
    private JFrame mainScherm;


    public OverzichtRouteVerzending(int id, JFrame hoofdScherm, JFrame mainScherm) {
        this(new VerzendingDAODerby().getAllVerzendingById(id),hoofdScherm, mainScherm);
    }
    public OverzichtRouteVerzending(Verzending verzending, JFrame hoofdScherm, JFrame mainScherm) {
        this.hoofdScherm = hoofdScherm;
        this.verzending = verzending;
        this.mainScherm = mainScherm;

        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.add(new JLabel("Details verzending"));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(10, 15, 5, 15));

        JPanel middlePanel = new JPanel(new GridLayout(6, 2));
        middlePanel.setBackground(orange);
        middlePanel.setForeground(Color.white);
        middlePanel.setBorder(new EmptyBorder(5, 15, 0, 15));


        JLabel locatie1 = new JLabel("Afhaallocatie:");
        middlePanel.add(locatie1);
        afhaalLocatie = new JLabel("...");
        middlePanel.add(afhaalLocatie);

        JLabel locatie2 = new JLabel("Bezorglocatie:");
        middlePanel.add(locatie2);
        bezorgLocatie = new JLabel("...");
        middlePanel.add(bezorgLocatie);

        JLabel stv = new JLabel("Status verzending:");
        middlePanel.add(stv);
        status = new JLabel("...");
        middlePanel.add(status);

        JLabel afst = new JLabel("Afstand route:");
        middlePanel.add(afst);
        afstand = new JLabel("...");
        middlePanel.add(afstand);

        JLabel t = new JLabel("Tijdsduur route:");
        middlePanel.add(t);
        tijd = new JLabel("...");
        middlePanel.add(tijd);

        JLabel k = new JLabel("Kosten verzending:");
        middlePanel.add(k);
        kosten = new JLabel("...");
        middlePanel.add(kosten);


        if(verzending != null) {
            routeSteps = getVerzending(verzending);

            if(routeSteps.size() == 1) {
                bottomPanel = new JPanel(new GridLayout(2, 1));
            } else {
                bottomPanel = new JPanel(new GridLayout(2, 3));
            }

            if(!routeSteps.isEmpty()) {
                bottomPanel.setBorder(new EmptyBorder(15, 15, 10, 15));
                int count = 1;
                for (RouteStep step:routeSteps) {
                    JPanel innerBottom = new JPanel(new GridLayout(8,2));
                    innerBottom.add(new JLabel("Route stap:"));
                    innerBottom.add(new JLabel(Integer.toString(count)));

                    innerBottom.add(new JLabel("Koerier:"));
                    innerBottom.add(new JLabel(step.getKoerier().toString()));

                    innerBottom.add(new JLabel("Groene route:"));
                    if(step.getGreen() == 1) {
                        innerBottom.add(new JLabel("Ja"));
                    } else {
                        innerBottom.add(new JLabel("Nee"));
                    }

                    innerBottom.add(new JLabel("Afstand stap:"));
                    innerBottom.add(new JLabel(Route.getAfstandText(step.getAfstand())));

                    innerBottom.add(new JLabel("Tijdsduur stap:"));
                    innerBottom.add(new JLabel(Route.getTijdText(step.getTijd())));

                    innerBottom.add(new JLabel("Kosten stap:"));
                    innerBottom.add(new JLabel(Route.getKostenText(step.getKosten())));

                    innerBottom.add(new JLabel(""));
                    innerBottom.add(new JLabel(""));
                    bottomPanel.add(innerBottom);
                    count++;
                }
                if(verzending.getVerzendID() == 0) {
                    saveRoute = new JButton("Verzending bevestigen");
                    saveRoute.addActionListener(this);
                    bottomPanel.add(new JPanel(new FlowLayout()).add(saveRoute));
                } else {
                    bottomPanel.add(new JLabel(""));
                }
                bottomPanel.add(new JLabel(""));
                bottomPanel.add(new JLabel(""));
            }
        } else {
            JOptionPane.showMessageDialog(null,"Er is iets mis gegaan met de verzending, probeer het later nog eens!", "Oeps..", JOptionPane.ERROR_MESSAGE);
        }


        //Create MainFrame
        mainFrame = new JFrame();
        mainFrame.setTitle("TZT Overzicht verzending");
        if(routeSteps.size() == 1) {
            mainFrame.setSize(800, 500);
        } else {
            mainFrame.setSize(800, 500);
        }
        mainFrame.setLocationRelativeTo(null);

        //Add panels to MainFrame
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(middlePanel, BorderLayout.CENTER);
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

    private ArrayList<RouteStep> getVerzending(Verzending verzending) {
        afhaalLocatie.setText(verzending.getOrigin().getFullAddress());
        bezorgLocatie.setText(verzending.getDestination().getFullAddress());
        afstand.setText(Route.getAfstandText(verzending.getRoute().getAfstand()));
        kosten.setText(Route.getKostenText(verzending.getRoute().getKosten()));
        tijd.setText(Route.getTijdText(verzending.getRoute().getTijd()));
        return verzending.getRoute().getSteps();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == saveRoute) {
            if(new VerzendingDAODerby().saveVerzending(verzending)) {
                JOptionPane.showMessageDialog(null,"Verzending is bevestigd!", "Succes", JOptionPane.INFORMATION_MESSAGE);
                hoofdScherm.dispose();
                mainFrame.dispose();
                mainScherm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,"Verzending is niet opgeslagen, probeer het later nog eens.", "Oeps..", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
