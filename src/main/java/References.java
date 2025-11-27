import java.util.ArrayList;
import java.util.List;

public class References {
    // TODO: metodien toteukset ja luokka viitteelle
    private static final List<Reference> list = new ArrayList<>();

    public void add(Reference reference) {
        list.add(reference);
    }

    // public void edit() {}

    public boolean delete(Reference reference) {
        return list.remove(reference);
    }

    // public void printRefeneces() {}

    public Reference findReferenceByKey(String key) {
        // Etsitään lähde viiteavaimen perusteella ja palautetaan lähdeolio
        for (Reference ref : list) {
            if (ref.getKey().equals(key)) {
                return ref;
            }
        }
        return null;
    } 

    // TODO: tee korjaus tähän
    // Tarkistetaan, onko avain olemassa (palautetaan totuusarvo)
    public static boolean isDuplicateKey(String key) {
        return false;
    } 
}