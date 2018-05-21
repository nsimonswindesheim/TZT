package verzending;

import klant.Klant;
import route.Route;

import java.util.ArrayList;

public interface VerzendingDAO {
    boolean saveVerzending(Verzending verzending);
    ArrayList<Integer> getAllVerzendingFromKlant(int id);
    Verzending getAllVerzendingById(int id);
}
