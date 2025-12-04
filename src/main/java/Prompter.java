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
        addNewFields(data);

        System.out.println("Add tags (Press Enter to skip): ");
        String tag = scanner.nextLine().trim(); 

        Reference ref = new Reference(type, key, tag, data);

        if (!yesOrNo("Do you want to add this reference? (yes | no) ")) {
            return;
        }
        references.add(ref);
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

        System.out.println("Reference to edit:");
        System.out.println(referenceToEdit);

        String type = editType(referenceToEdit.getType());
        String tag = editTag(referenceToEdit.getTag());

        Map<String, String> newData = editExistingFields(referenceToEdit);
        addNewFields(newData);

        Reference updatedReference = new Reference(
            type, 
            referenceToEdit.getKey(),
            tag, 
            newData
        );

        if (references.edit(referenceToEdit, updatedReference)) {
            System.out.println("\n Reference " + updatedReference.getKey() + " updated successfully!");
        } else {
            System.out.println("\n Failed to update reference " + referenceToEdit.getKey());
        }
    }

    
    /**
     * Kysyy käyttäjältä viitteen tunnistetta ja poistaa tunnisteen viitteen
     */
    public void deleteReference() {
        references.printReferences();

        String key = nonEmptyField("Enter the key of the reference you want to delete: ");
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


    private Map<String, String> editExistingFields(Reference ref) {
        Map<String, String> data = new HashMap<>();

        for (Map.Entry<String, String> entry : ref.getData().entrySet()) {
            String field = entry.getKey();
            String oldValue = entry.getValue();

            boolean editField = yesOrNo("Edit '" + field + "' (current: " + oldValue + ") (yes | no): ");

            if  (editField) {
                String newValue = editField("New value for " + field + ": ", oldValue);
                data.put(field, newValue);
            } else {
                data.put(field, oldValue);
            }
        }
        return data;
    }


    private void addNewFields(Map<String, String> data) {
        while (yesOrNo("Do you want to add new field? (yes | no) ")) {
            String field = nonEmptyField("Enter new field: ");

            // varmistetaan ettei kahta samannimistä kenttää
            if (data.containsKey(field)) {
                System.out.println("Field already exists!");
                continue;
            }

            String value = nonEmptyField("Enter new value for " + field + ": ");
            data.put(field, value);

            System.out.println("New field: " + field + " added!");
        }
    }

    private String editType(String current) {
        boolean chance = yesOrNo("Do you want to change the type? (current: " + current + ") (yes | no) ");

        if (!chance) return current;
        return editField("Enter new type: ", current);
    }

    private String editTag(String current) {
        String shown = (current == null || current.isEmpty()) ? "(none)" : current;
        boolean chance = yesOrNo("Do you want to change the tags? (current: " + shown + ") (yes | no) ");

        if (!chance) return current;
        return editField("Enter new tags: ", current == null ? "" : current);
    }


    // Kysyntä kentän muokkausta varten. Voi jättää tyhjäksi jolloin vanha arvo pysyy kentässä
    private String editField(String prompt, String oldValue) {
        System.out.println(prompt + "(leave empty to keep: " + oldValue + ")");
        String newValue = scanner.nextLine().trim();
        if (newValue.isEmpty()) { return oldValue; }
        return newValue;
    }
}
