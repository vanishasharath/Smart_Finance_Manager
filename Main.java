import java.util.*;

public class Main {

    private static void printMenu() {
        System.out.println("\n====== Smart Expense Manager ======");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Delete Expense");
        System.out.println("4. Monthly Total");
        System.out.println("5. Category Total");
        System.out.println("6. Sort by Amount");
        System.out.println("7. Sort by Date");
        System.out.println("8. Save & Exit");
        System.out.println("9. Set Monthly Budget");
        System.out.println("10. View Spending Insights");
        System.out.print("Choose: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();
        manager.loadFromFile();

        while (true) {

            printMenu();

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            sc.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    double amount;
                    try {
                        System.out.print("Amount: ");
                        amount = sc.nextDouble();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount!");
                        sc.nextLine();
                        break;
                    }

                    if (amount <= 0) {
                        System.out.println("Amount must be positive!");
                        break;
                    }

                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();

                    System.out.print("Category: ");
                    String category = sc.nextLine();

                    System.out.print("Description: ");
                    String desc = sc.nextLine();

                    manager.addExpense(amount, date, category, desc);
                    break;

                case 2:
                    manager.viewExpenses();
                    break;

                case 3:
                    try {
                        System.out.print("Enter ID to delete: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        manager.deleteExpense(id);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid ID!");
                        sc.nextLine();
                    }
                    break;

                case 4:
                    System.out.print("Enter month (YYYY-MM): ");
                    String month = sc.nextLine();
                    manager.monthlyTotal(month);
                    break;

                case 5:
                    System.out.print("Enter category: ");
                    String cat = sc.nextLine();
                    manager.categoryTotal(cat);
                    break;

                case 6:
                    manager.sortByAmount();
                    break;

                case 7:
                    manager.sortByDate();
                    break;

                case 8:
                    manager.saveToFile();
                    System.out.println("Goodbye!");
                    return;

                case 9:
                    try {
                        System.out.print("Enter monthly budget: ");
                        double budget = sc.nextDouble();
                        sc.nextLine();
                        manager.setBudget(budget);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid budget!");
                        sc.nextLine();
                    }
                    break;

                case 10:
                    manager.showInsights();
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-10.");
            }
        }
    }
}