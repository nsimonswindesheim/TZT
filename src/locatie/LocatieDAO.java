package locatie;

public interface LocatieDAO {
    Locatie newLocatie(String straat, String huisnummer, String postcode, String plaats);
    int saveLocatie(String straat, String huisnummer, String postcode, String plaats);
    Locatie getLocatieById(int id);
}
