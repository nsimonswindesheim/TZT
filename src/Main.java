import general.Validate;
import klant.Klant;
import klant.KlantDAODerby;
import locatie.Locatie;
import locatie.LocatieDAODerby;
import route.Route;
import route.RouteStep;
import schermen.Hoofdmenu;
import schermen.Inlogscherm;
import verzending.Verzending;
import verzending.VerzendingDAODerby;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Inlogscherm scherm = new Inlogscherm();
        scherm.setVisible(false);
        Hoofdmenu scherm1 = new Hoofdmenu(scherm);
    }
}

