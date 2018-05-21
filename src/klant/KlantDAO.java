package klant;

public interface KlantDAO {
    int saveToDB(Klant klant);
    boolean ifExistDB(int id);
    Klant getKlantById(int klantID);
}
