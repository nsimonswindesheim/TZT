package klant;

public interface KlantDAO {
    boolean saveToDB(Klant klant);
    boolean ifExistDB(int id);
    Klant getKlantById(int klantID);
}
