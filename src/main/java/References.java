import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;

public class References {
    
    private final List<Reference> list = new ArrayList<>();

    public void add(Reference reference) {
        if (isDuplicateKey(reference.getKey())) {
            System.out.println("Reference key already exists!");
            return;
        }

        list.add(reference);
        
        try {
            saveToFile();
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }

    
    public boolean edit(Reference oldRef, Reference newRef) {
        if (!list.contains(oldRef)) return false;
        list.remove(oldRef);
        list.add(newRef);

        try {
            saveToFile();
        } catch (Exception e) {
            System.err.println(e);
        }
        return true;
    }


    public boolean delete(Reference reference) {
        return list.remove(reference);
        //tallentaa prompter luokassa
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
            if (ref.getKey().equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    } 

    public void saveToFile() throws Exception{
        StringBuilder sb = new StringBuilder();
        for (Reference ref : list) {
            StringBuilder refSB = new StringBuilder(ref.toString());
            
            sb.append("@" + refSB.substring(6, refSB.indexOf("\n")) + "{");
            refSB = new StringBuilder(refSB.substring(refSB.indexOf("\n")+1));

            sb.append(refSB.substring(5, refSB.indexOf("\n")) + ",\n");
            refSB = new StringBuilder(refSB.substring(refSB.indexOf("\n")+1));
            
            String temp = refSB.toString();
            temp = temp.replace(": ", " = {");
            refSB = new StringBuilder(temp);

            while (true) {
                if (refSB.indexOf("\n") == -1)break;
                
                sb.append("\t" + refSB.substring(0, refSB.indexOf("\n")) + "},\n");

                refSB = new StringBuilder(refSB.substring(refSB.indexOf("\n")+1));
            }
            sb.append("}\n\n");
        }

        Files.write(Paths.get("src/data/references.bib"), sb.toString().getBytes());
        System.out.println(sb);
    }
}