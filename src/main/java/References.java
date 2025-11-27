import java.util.ArrayList;
import java.util.List;

public class References {
    // TODO: metodien toteukset ja luokka viitteelle
    private static final List<Reference> list = new ArrayList<>();

    public void add(Reference reference) {
        list.add(reference);
        System.out.println("lisätty");
    }

    // public void edit() {}

    public void delete(Reference reference) {
        list.remove(reference);
    }

    // public void printRefeneces() {}

    // public Reference findReferenceByKey(String key) {} // Etsitään lähde viiteavaimen perusteella ja palautetaan lähdeolio

    // TODO: tee korjaus tähän
    // Tarkistetaan, onko avain olemassa (palautetaan totuusarvo)
    public static boolean isDuplicateKey(String key) {
        return false;
    } 
}