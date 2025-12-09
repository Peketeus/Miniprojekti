import java.util.Scanner;

public class BibManager {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== Welcome to Bib Manager! =====");

        Prompter prompter = new Prompter(SCANNER);
        while (true) {
            
            System.out.println("\nWhat would you like to do (1 - 5)?");
            System.out.println("1) Add reference");
            System.out.println("2) Edit reference");
            System.out.println("3) Delete reference");
            System.out.println("4) List references");
            System.out.println("5) Exit");
            System.out.println("Enter your choice: ");

            String choice = SCANNER.nextLine();

            switch (choice) {
                case "1":
                    prompter.addNewReference();
                    break;
                case "2":
                    prompter.editReference();
                    break;
                case "3":
                    prompter.deleteReference();
                    break;
                case "4":
                    System.out.println("\nThank you for using Bib Manager!");
                    return;
                default:
                    System.out.println("\nInvalid choice! Try again.");
            }
        }
    }
}