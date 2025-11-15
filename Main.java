import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();
        manager.loadFromFile();

        while (true) {
            System.out.println("\n====== Expense Tracker ======");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Monthly Total");
            System.out.println("5. Category Total");
            System.out.println("6. Save & Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

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
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    manager.deleteExpense(id);
                    break;

                case 4:
                    System.out.print("Enter month (YYYY-MM): ");
                    String month = sc.next();
                    manager.monthlyTotal(month);
                    break;

                case 5:
                    System.out.print("Enter category: ");
                    sc.nextLine();
                    String cat = sc.nextLine();
                    manager.categoryTotal(cat);
                    break;

                case 6:
                    manager.saveToFile();
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

