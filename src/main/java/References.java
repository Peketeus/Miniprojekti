import java.util.ArrayList;
import java.util.List;

public class References {
    
    private final List<Reference> list = new ArrayList<>();

    public void add(Reference reference) {
        if (isDuplicateKey(reference.getKey())) {
            System.out.println("Reference key already exists!");
            return;
        }

        list.add(reference);
    }

    
    public void edit(Reference reference, String atribute, String value) {
        reference.edit(atribute, value);
    }


    public boolean delete(Reference reference) {
        return list.remove(reference);
    }


    //testejä varten
    public String[] information(Reference reference) {
        return reference.information();
    }


    public void printReferences() {
        for (Reference ref: list) {
            System.out.println(ref);
        }
    }


    /**
     * Etsitään lähde viiteavaimen perusteella ja palautetaan lähdeolio
     * @param key viitteen yksilöivä avain
     * @return viiteolio
     */
    public Reference findReferenceByKey(String key) {
        for (Reference ref : list) {
            if (ref.getKey().equals(key)) {
                return ref;
            }
        }
        return null;
    } 


    public int getSize() {
        return list.size();
    }


    /**
     * Tarkistetaan, onko avain jo olemassa 
     * @param key viitteen yksilöivä avain
     * @return totuusarvo siitä, onko viiteavain jo olemassa
     */
    public boolean isDuplicateKey(String key) {
        for (Reference ref : list) {
            if (ref.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    } 
}