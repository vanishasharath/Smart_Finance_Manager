import java.io.*;
import java.util.*;

public class ExpenseManager {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private int currentId = 1;

    public void addExpense(double amount, String date, String category, String description) {
        Expense e = new Expense(currentId++, amount, date, category, description);
        expenses.add(e);
        System.out.println("Expense added!");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    public void deleteExpense(int id) {
        expenses.removeIf(expense -> expense.getId() == id);
        System.out.println("Expense removed!");
    }

    public void monthlyTotal(String month) {
        double total = 0;
        for (Expense e : expenses) {
            if (e.getDate().startsWith(month)) {
                total += e.getAmount();
            }
        }
        System.out.println("Total for " + month + ": " + total);
    }

    public void categoryTotal(String category) {
        double total = 0;
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                total += e.getAmount();
            }
        }
        System.out.println("Total for " + category + ": " + total);
    }

    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("expenses.txt");
            for (Expense e : expenses) {
                fw.write(e.getId() + "," + e.getAmount() + "," + e.getDate() + "," +
                         e.getCategory() + "," + e.getDescription() + "\n");
            }
            fw.close();
            System.out.println("Saved!");
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    public void loadFromFile() {
        try {
            File file = new File("expenses.txt");
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Expense e = new Expense(
                    Integer.parseInt(data[0]),
                    Double.parseDouble(data[1]),
                    data[2],
                    data[3],
                    data[4]
                );
                expenses.add(e);
                currentId = Math.max(currentId, e.getId() + 1);
            }
            br.close();
            System.out.println("Loaded previous data!");
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}
