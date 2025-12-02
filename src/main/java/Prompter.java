import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Prompter {

    private final Scanner scanner;
    private final References references = new References();

    public Prompter(Scanner scanner) {
        this.scanner = scanner;
    }

   
    /**
     * Luo uuden viitteen ja lisää sen viitelistaan.
     */
    public void addNewReference() {
        String type = nonEmptyField("Enter reference type: ");
        String key;
        
        while (true) { 
            key = nonEmptyField("Enter reference key: ");
            boolean dupe = references.isDuplicateKey(key);
            if (dupe == true) {
                System.out.println("\nReference with " + key + " already exists!");
            } else {
                break;
            }
        }
        
        Map<String, String> data = new HashMap<>();

        while (true) { 
            boolean yn = yesOrNo("Do you want add more details? (yes | no) ");
            if (!yn) {
                break;
            }

            String fieldName = nonEmptyField("Enter field name: ").toLowerCase();

            if (data.containsKey(fieldName)) {
                System.out.println("Field name already exists!");
                continue;
            }

            String fieldValue = nonEmptyField("Enter field value: ");
            data.put(fieldName, fieldValue);
        }

        boolean yn = yesOrNo("Do you want to add this reference? (yes | no) ");
        if (!yn) {
            return;
        }

        Reference r = new Reference(type, key, data);
        references.add(r);
        System.out.println("");
        System.out.println("Reference " + type + " added!");
    }


    /**
     * Kysyy käyttäjältä kyllä tai ei kysymyksen ja odottaa 
     * käyttäjän vastausta.
     * @param question Kysymys käyttäjälle
     * @return boolean arvon true tai false
     */
    private boolean yesOrNo(String question) {
        while (true) {
            System.out.print("\n" + question);
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("\n Invalid choice try again.");
        }
    }

    
    /**
     * Muokkaa yksittäistä viitettä
     */
    public void editReference() {

        references.printReferences();
        String key = nonEmptyField("Enter key of reference to edit: ");

        Reference referenceToEdit = references.findReferenceByKey(key);

        if (referenceToEdit == null) {
            System.out.println("Can't find reference with given key: " + key);
            return;
        }

        System.out.println(referenceToEdit);

        String type = referenceToEdit.getType();
        boolean changeType = yesOrNo("Do you want to change the type? (current: " + type + ") (y/n) ");

        if (changeType) {
            type = nonEmptyField("Enter new type: ");
        }

        Map<String, String> newData = new HashMap<>();

        for (Map.Entry<String, String> entry : referenceToEdit.getData().entrySet()) {
            String fieldName = entry.getKey();
            String oldValue = entry.getValue();
            
            boolean edit = yesOrNo("Do you want to edit field '" + fieldName + "'': "  + oldValue + "? (y/n) ");

            if (edit) {
                String newValue = nonEmptyField("Enter new field value for " + fieldName + ": ");
                newData.put(fieldName, newValue);
            }

            else {
                newData.put(fieldName, oldValue);
            }
        }

        while (true) {
            boolean edit = yesOrNo("Do you want to add new field? (y/n) ");
            if (edit) {
                // TODO: Tähän uuden kentän lisäys
            }
            else {
                break;
            }
        }

        Reference updatedReference = new Reference(type, key, newData);

        references.delete(referenceToEdit);
        references.add(updatedReference);
        
        System.out.println("Reference with key " + updatedReference.getKey() + " updated.");
    }

    
    /**
     * Kysyy käyttäjältä viitteen tunnistetta ja poistaa tunnisteen viitteen
     */
    public void deleteReference() {
        String key = nonEmptyField("Enter the reference key: ");
        Reference reference = references.findReferenceByKey(key);
        if (references.delete(reference)) {
            System.out.println("Reference removed successfully!");
        } else {
            System.out.println("Could not find a reference with the given key");
        }
    }


    /**
     * Kutsuu references luokkaa tulostaakseen kaikki viitteet
     */
    public void listReferences() {
        references.printReferences();
    }


    /**
     * Kysyy käyttäjältä kysymyksen ja odottaa siihen syötettä
     * @param fieldName kysymys käyttäjälle
     * @return
     */
    public String nonEmptyField(String fieldName) {
        String input;

        while (true) {
            System.out.println(fieldName);
            input = scanner.nextLine().trim();

            if (!input.trim().isEmpty()) {
                break;
            }
            System.out.println("Field can't be empty!\n");
        }
        return input;
    }
}
