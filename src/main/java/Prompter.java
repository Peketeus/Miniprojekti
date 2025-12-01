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
     * Käyttäjän antaman valinnan käsittely 
     * ja kutsut valinnan mukaisiin luokkiin.
     */
    public void addNewReference() {
        String type = nonEmptyField("Enter reference type: ");
        String key = nonEmptyField("Enter reference key: ");
        Map<String,String> data = new HashMap<>();

        while (true) { 
            boolean yn = yesOrNo("Do you want add more details? (yes | no) ");
            if (!yn) {
                break;
            }

            String fieldName = nonEmptyField("Enter field name: ");
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
    
    public void editReference() {
        String key = nonEmptyField("Enter the reference key: ");

        //Nyt toimii vain Articlelle
        String choice;
        while (true) {
            System.out.println("\nWhich property would you like to edit (1 - 7)?");
            System.out.println("1) Key");
            System.out.println("2) Author");
            System.out.println("3) Title");
            System.out.println("4) Journal");
            System.out.println("5) Year");
            System.out.println("6) Volume");
            System.out.println("7) Pages");
            System.out.println("Enter your choice: ");
            
            choice = scanner.nextLine();
            
            if (choice.length() > 0)break;
        }
        
        while (true) { 
            
            String input = nonEmptyField("Enter new value: ");
            
            Reference reference = references.findReferenceByKey(key);
            switch (choice) {
                case "1":
                    references.edit(reference, "key", input);
                    break;
                case "2":
                    references.edit(reference,"author", input);
                    break;
                case "3":
                    references.edit(reference,"title", input);
                    break;
                case "4":
                    references.edit(reference,"journal", input);
                    break;
                case "5":
                    references.edit(reference,"year", input);
                    break;
                case "6":
                    references.edit(reference,"volume", input);
                    break;
                case "7":
                    references.edit(reference,"pages", input);
                    break;
                default:
                    System.out.println("\nInvalid choice! Try again.");

                }
            break;
        }
        
    }
    
    public void deleteReference() {
        String key = nonEmptyField("Enter the reference key: ");
        Reference reference = references.findReferenceByKey(key);
        if (references.delete(reference)) {
            System.out.println("Reference removed successfully!");
        } else {
            System.out.println("Could not find a reference with the given key");
        }

    }

    public void listReferences() {
        references.printReferences();
    }

    public String nonEmptyField(String fieldName) {
        String input;

        while (true) {
            System.out.println(fieldName);
            input = scanner.nextLine();

            if (!input.trim().isEmpty()) break;
            System.out.println("Field can't be empty!\n");
        }

        return input;
    }

 }
