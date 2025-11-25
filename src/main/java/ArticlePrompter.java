import java.util.Scanner;

public class ArticlePrompter {

    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Kysytään käyttäjältä artikkeli-lähteen tiedot
     * TODO: Staattisen metodin refaktorointi
     * @param scanner Syötteenlukija
     * @return uusi Artikkeli-olio
     */
    public static Article createArticle() {

        while (true) {
            System.out.print("Enter unique reference key: ");
            String key = scanner.nextLine();
            if (!key.isBlank() && !References.isDuplicateKey(key)) break;
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

        //Tähän tilalle myöhemmin: return new Article()
        return null;
    }
}