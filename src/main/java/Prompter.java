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
