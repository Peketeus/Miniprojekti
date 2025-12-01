import java.util.Scanner;

public class ArticlePrompter {
    private final Scanner scanner;

    public ArticlePrompter(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Kysytään käyttäjältä artikkeli-lähteen tiedot
     * TODO: Staattisen metodin refaktorointi
     * @return uusi Artikkeli-olio
     */
    public Reference create() {
        String key = "";

        while (true) {
            System.out.print("Enter unique reference key: ");
            key = scanner.nextLine();
            if (!key.trim().isEmpty() && !References.isDuplicateKey(key)) break;
            System.out.println("\nInvalid key. Enter new unique reference key.");
        }
     
        System.out.print("\nEnter author: ");
        String author = scanner.nextLine();

        System.out.print("\nEnter title: ");
        String title = scanner.nextLine();

        System.out.print("\nEnter journal: ");
        String journal = scanner.nextLine();

        System.out.print("\nEnter year: ");
        String year = scanner.nextLine();

        System.out.print("\nEnter volume: ");
        String volume = scanner.nextLine();

        System.out.print("\nEnter pages: ");
        String pages = scanner.nextLine();
        
        return new Article(key, author, title, journal, year, volume, pages);
    }

}