import java.util.Scanner;

public class BibManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== Welcome to Bib Manager! =====");

        boolean systemRunning = true;
        while (systemRunning) {
            // TODO: Omaan luokkaan kyselyt
            System.out.println("\nWhat would you like to do (1 - 5)?");
            System.out.println("1) Add reference");
            System.out.println("2) Edit reference");
            System.out.println("3) Delete reference");
            System.out.println("4) List references");
            System.out.println("5) Exit");
            System.out.println("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    break; // TODO: kutsu luokkaan x, jossa kysellään käyttäjältä viitteen tiedot
                case "2":
                    break; // TODO: kutsu luokkaan x, jossa listataan olemassa olevat viitteet ja kysytään mitä muokataan
                case "3":
                    break; // TODO: kutsu luokkaan x, jossa listataan olemassa olevat viitteet ja kysytään mikä poistetaan
                case "4":
                    break; // TODO: kutsu luokkaan x, jossa listataan olemassa olevat viitteet
                case "5":
                    System.out.println("\nThank you for using Bib Manager!");
                    systemRunning = false;
                    return;
                default:
                    System.out.println("\nInvalid choice! Try again.");
            }
        }
    }
}