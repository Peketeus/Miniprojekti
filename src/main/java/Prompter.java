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
        int type = askType();
        Reference reference = null;
        
        switch (type) {
            case 1: {
                // String key = askUniqueKey();
                reference = new ArticlePrompter(scanner).create();
                break;
            }
            case 2:
                // reference = new InproceedingsPrompter(scanner).create();
                break;
            case 3:
                // reference = new BookPrompter(scanner).create();
                break;
            case 4:
                return;
            default:
                System.out.println("Error! Something went wrong");
        }
        if (reference == null) {
            System.out.println("Error");
            return;
        }
        
        references.add(reference);
        System.out.println("Reference added successfully!");
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
        references.printRefeneces();
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

    /**
     * Kysytään käyttäjältä lisättävän lähteen tyyppi
     * @return tyypin tunniste kokonaislukuna
     */
    private int askType() {

        int type = -1;

        while (true) {
            System.out.println("\nChoose reference type (1 - 4)");
            System.out.println("1) Add new journal article");
            System.out.println("2) Add new conference paper");
            System.out.println("3) Add new book");
            System.out.println("4) Cancel");

            type = scanner.nextInt();

            if (type >= 1 && type <= 4) break;

            System.out.println("Invalid choice! Try again.");
        }
        
        return type;
    }
 }
