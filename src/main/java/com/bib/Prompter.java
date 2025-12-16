package com.bib;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Prompter {

    private final Scanner scanner;
    private final References references = new References();

    public Prompter(Scanner scanner) {
        this.scanner = scanner;
        references.readFile();
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
        references.printReferences(null, null);

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
        references.printReferences(null, null);

        String key = nonEmptyField("Enter the key of the reference you want to delete: ");
        Reference reference = references.findReferenceByKey(key);
        if (references.delete(reference)) {
            System.out.println("Reference removed successfully!");
            try {
                references.saveToFile();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            System.out.println("Could not find a reference with the given key");
        }
    }


    /**
     * Kutsuu references luokkaa tulostaakseen kaikki viitteet hakuparametrien mukaan
     */
    public void listReferences() {
        System.out.println("Enter search parameter or leave blank to list all references: ");
        String searchParam = scanner.nextLine().trim();
        String fieldname = null;

        if (searchParam != null && !searchParam.equals("")) {

            while (true) {
                System.out.println("\nSearch by: ");
                System.out.println("1) Type (Article, book, etc)");
                System.out.println("2) Data (Author, year, etc)");
                System.out.println("3) Tag (Custom tags)");
                System.out.println("4) Cancel");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        fieldname = "Type";
                        break;
                    case "2":
                        fieldname = "Data";
                        break;
                    case "3":
                        fieldname = "Tag";
                        break;
                    case "4":
                        return;
                    default:
                        System.out.println("\nInvalid choice! Try again.");
                        continue;
                }
                break;
            }
        }

        references.printReferences(searchParam, fieldname);
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


    /**
     * Lisäkenttien muokkaus ja poisto
     * @param ref Muokattava viite
     * @return uusi lisätietodata viitteelle
     */
    private Map<String, String> editExistingFields(Reference ref) {
        Map<String, String> data = new HashMap<>();

        for (Map.Entry<String, String> entry : ref.getData().entrySet()) {
            String field = entry.getKey();
            String oldValue = entry.getValue();
            
            while (true) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Edit '" + field + "' (current: '" + oldValue + "')");
                System.out.println("2) Delete '" + field + "'");
                System.out.println("3) Keep existing value for '" + field + "'");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        String newValue = editField("New value for " + field + ": ", oldValue);
                        data.put(field, newValue);
                        break;
                    case "2":
                        System.out.println("Deleted '" + field + "' with value '" + oldValue + "'");
                        break;
                    case "3":
                        data.put(field, oldValue);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again");
                        continue;
                }
                break;
            } 
        }
        return data;
    }


    /**
     * Uusien kenttien lisäys
     * @param data viitteen lisätiedot
     */
    private void addNewFields(Map<String, String> data) {
        while (yesOrNo("Do you want to add new field? (yes | no) ")) {
            String field = nonEmptyField("Enter new field: ");

            // varmistetaan ettei kahta samannimistä kenttää
            if (data.containsKey(field)) {
                System.out.println("Field already exists!");
                continue;
            }

            String value = nonEmptyField("Enter new value for " + field + ": ").toLowerCase();
            data.put(field, value);

            System.out.println("New field: " + field + " added!");
        }
    }


    /**
     * Viitteen tyypin muokkaus
     * @param current nykyisen tyypin arvo
     * @return tyypin arvo merkkijonona
     */
    private String editType(String current) {
        boolean chance = yesOrNo("Do you want to change the type? (current: " + current + ") (yes | no) ");

        if (!chance) {
            return current;
        }
        return editField("Enter new type: ", current);
    }


    /**
     * Viitteen tagin muokkaus
     * @param current nykyinen tag
     * @return tagin arvo merkkijonona
     */
    private String editTag(String current) {
        String shown = (current == null || current.isEmpty()) ? "(none)" : current;
        boolean chance = yesOrNo("Do you want to change the tags? (current: " + shown + ") (yes | no) ");

        if (!chance) {
            return current;
        }
        return editField("Enter new tags: ", current == null ? "" : current);
    }


    /**
     * Kysyntä kentän muokkausta varten. Voi jättää tyhjäksi jolloin vanha arvo pysyy kentässä
     * @param prompt konsoliin tulostettava kysymys
     * @param oldValue datakentän vanha arvo
     * @return datakentän uusi arvo
     */
    private String editField(String prompt, String oldValue) {
        System.out.println(prompt + "(leave empty to keep: " + oldValue + ")");
        String newValue = scanner.nextLine().trim();
        if (newValue.isEmpty()) { return oldValue; }
        return newValue;
    }

}