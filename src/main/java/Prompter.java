import java.util.Scanner;

public class Prompter {
    
    private static final Scanner scanner = new Scanner(System.in);
    private References references = new References();

   
    /**
     * Käyttäjän antaman valinnan käsittely 
     * ja kutsut valinnan mukaisiin luokkiin.
     */
    public void addNewReference() {
        int type = askType();
        Reference reference = null;
        
        switch (type) {
            case 1:

                reference = ArticlePrompter.createArticle();
                break;
            case 2:
                
                break;
            case 3:
                
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
        String key;

        System.out.println("Enter the reference key: ");
        while (true) {
            key = scanner.nextLine();
            if (key.length() > 0)break;
        }
        
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
            
            String input;
            
            System.out.println("Enter new value: ");
            while (true) {
                input = scanner.nextLine();
                if (input.length() > 0)break;
            }
            
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
        String key;

        System.out.println("Enter the reference key: ");
        while (true) {
            key = scanner.nextLine();
            if (key.length() > 0)break;
        }
        Reference reference = references.findReferenceByKey(key);
        if (references.delete(reference)) {
            System.out.println("Reference removed successfully!");
        } else {
            System.out.println("Could not find a reference with the given key");
        }

    }
      
    public void listReferences() {

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
