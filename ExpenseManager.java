import java.io.*;
import java.util.*;

public class ExpenseManager {

    private final ArrayList<Expense> expenses = new ArrayList<>();
    private int currentId = 1;
    private double monthlyBudget = 0;

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
        boolean removed = expenses.removeIf(expense -> expense.getId() == id);

        if (removed) {
            System.out.println("Expense removed!");
        } else {
            System.out.println("Expense not found!");
        }
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

        if (total == 0) {
            System.out.println("No expenses found for category: " + category);
        } else {
            System.out.println("Total for " + category + ": " + total);
        }
    }

    public void sortByAmount() {
        expenses.sort(Comparator.comparingDouble(Expense::getAmount));
        System.out.println("Expenses sorted by amount.");
        viewExpenses();
    }

    public void sortByDate() {
        expenses.sort(Comparator.comparing(Expense::getDate));
        System.out.println("Expenses sorted by date.");
        viewExpenses();
    }

    public void setBudget(double budget) {
        this.monthlyBudget = budget;
        System.out.println("Monthly budget set to: " + budget);
    }

    public void showInsights() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        double total = 0;
        Map<String, Double> categoryMap = new HashMap<>();

        for (Expense e : expenses) {
            total += e.getAmount();

            categoryMap.put(
                e.getCategory(),
                categoryMap.getOrDefault(e.getCategory(), 0.0) + e.getAmount()
            );
        }

        // Find top category
        String topCategory = "";
        double max = 0;

        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        double average = total / expenses.size();

        System.out.println("\n===== Spending Insights =====");
        System.out.println("Total Spending: " + total);
        System.out.println("Average Expense: " + average);
        System.out.println("Top Category: " + topCategory);

        if (monthlyBudget > 0 && total > monthlyBudget) {
            System.out.println("⚠ Budget exceeded!");
        }
    }

    public void saveToFile() {
        try (FileWriter fw = new FileWriter("expenses.txt")) {
            for (Expense e : expenses) {
                fw.write(e.getId() + "," + e.getAmount() + "," + e.getDate() + "," +
                        e.getCategory() + "," + e.getDescription() + "\n");
            }
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public void loadFromFile() {
        File file = new File("expenses.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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

            System.out.println("Loaded previous data!");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file.");
        }
    }
}