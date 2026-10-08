import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseService {

    private final ArrayList<Expense> expenses;
    private final ExpenseDAO expenseDAO;

    public ExpenseService(
            ArrayList<Expense> expenses,
            ExpenseDAO expenseDAO) {

        this.expenses = expenses;
        this.expenseDAO = expenseDAO;
    }

    // =========================================================
    // LOAD FROM DATABASE
    // =========================================================

    public void loadFromDatabase() {

        expenses.clear();

        ArrayList<Expense> databaseExpenses =
                expenseDAO.getAllExpenses();

        expenses.addAll(databaseExpenses);
    }

    // =========================================================
    // ADD EXPENSE
    // =========================================================

    public boolean addExpense(Expense expense) {

        if (expense == null) {
            return false;
        }

        boolean saved =
                expenseDAO.insertExpense(expense);

        if (saved) {
            expenses.add(expense);
        }

        return saved;
    }

    // =========================================================
    // GET ALL EXPENSES
    // =========================================================

    public List<Expense> getAllExpenses() {

        return expenses;
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    public Expense findById(int id) {

        return expenseDAO.findExpenseById(id);
    }

    // =========================================================
    // DELETE EXPENSE
    // =========================================================

    public boolean deleteExpense(int id) {

        boolean deleted =
                expenseDAO.deleteExpense(id);

        if (deleted) {

            expenses.removeIf(
                    expense -> expense.getId() == id
            );
        }

        return deleted;
    }

    // =========================================================
    // UPDATE EXPENSE
    // =========================================================

    public boolean updateExpense(
            int id,
            double amount,
            LocalDate date,
            String category,
            String description,
            Frequency frequency) {

        Expense updatedExpense =
                new Expense(
                        id,
                        amount,
                        date,
                        category,
                        description,
                        frequency
                );

        boolean updated =
                expenseDAO.updateExpense(
                        updatedExpense
                );

        if (updated) {

            for (int i = 0;
                 i < expenses.size();
                 i++) {

                if (expenses.get(i).getId() == id) {

                    expenses.set(
                            i,
                            updatedExpense
                    );

                    break;
                }
            }
        }

        return updated;
    }

    // =========================================================
    // SEARCH
    // =========================================================

    public List<Expense> searchExpenses(
            String keyword) {

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return new ArrayList<>();
        }

        String search =
                keyword.trim().toLowerCase();

        return expenses.stream()
                .filter(expense ->
                        expense.getDescription()
                                .toLowerCase()
                                .contains(search)
                        ||
                        expense.getCategory()
                                .toLowerCase()
                                .contains(search)
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // FILTER BY CATEGORY
    // =========================================================

    public List<Expense> filterByCategory(
            String category) {

        if (category == null ||
                category.trim().isEmpty()) {

            return new ArrayList<>();
        }

        return expenses.stream()
                .filter(expense ->
                        expense.getCategory()
                                .equalsIgnoreCase(
                                        category.trim()
                                )
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // FILTER BY AMOUNT RANGE
    // =========================================================

    public List<Expense> filterByAmountRange(
            double minimum,
            double maximum) {

        if (minimum < 0 ||
                maximum < 0 ||
                minimum > maximum) {

            return new ArrayList<>();
        }

        return expenses.stream()
                .filter(expense ->
                        expense.getAmount() >= minimum
                        &&
                        expense.getAmount() <= maximum
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // SORT BY AMOUNT - LOWEST FIRST
    // =========================================================

    public List<Expense> sortByAmount() {

        return expenses.stream()
                .sorted(
                        Comparator.comparingDouble(
                                Expense::getAmount
                        )
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // SORT BY AMOUNT - HIGHEST FIRST
    // =========================================================

    public List<Expense> sortByAmountDescending() {

        return expenses.stream()
                .sorted(
                        Comparator.comparingDouble(
                                Expense::getAmount
                        ).reversed()
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // SORT BY DATE
    // =========================================================

    public List<Expense> sortByDate() {

        return expenses.stream()
                .sorted(
                        Comparator.comparing(
                                Expense::getDate
                        )
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // MONTHLY TOTAL
    // =========================================================

    public double calculateMonthlyTotal(
            int year,
            int month) {

        if (month < 1 || month > 12) {
            return 0;
        }

        return expenses.stream()
                .filter(expense ->
                        expense.getDate().getYear() == year
                        &&
                        expense.getDate()
                                .getMonthValue() == month
                )
                .mapToDouble(
                        Expense::getAmount
                )
                .sum();
    }

    // =========================================================
    // CATEGORY TOTAL
    // =========================================================

    public double calculateCategoryTotal(
            String category) {

        if (category == null ||
                category.trim().isEmpty()) {

            return 0;
        }

        return expenses.stream()
                .filter(expense ->
                        expense.getCategory()
                                .equalsIgnoreCase(
                                        category.trim()
                                )
                )
                .mapToDouble(
                        Expense::getAmount
                )
                .sum();
    }

    // =========================================================
    // TOTAL SPENDING
    // =========================================================

    public double calculateTotalSpending() {

        return expenses.stream()
                .mapToDouble(
                        Expense::getAmount
                )
                .sum();
    }

    // =========================================================
    // AVERAGE SPENDING
    // =========================================================

    public double calculateAverageSpending() {

        if (expenses.isEmpty()) {
            return 0;
        }

        return calculateTotalSpending()
                / expenses.size();
    }

    // =========================================================
    // TOP SPENDING CATEGORY
    // =========================================================

    public String getTopCategory() {

        if (expenses.isEmpty()) {
            return "No expenses";
        }

        return expenses.stream()
                .collect(
                        Collectors.groupingBy(
                                Expense::getCategory,
                                Collectors.summingDouble(
                                        Expense::getAmount
                                )
                        )
                )
                .entrySet()
                .stream()
                .max(
                        Comparator.comparingDouble(
                                Map.Entry::getValue
                        )
                )
                .map(Map.Entry::getKey)
                .orElse("No expenses");
    }

    // =========================================================
    // CATEGORY-WISE TOTALS
    // =========================================================

    public Map<String, Double> getCategoryTotals() {

        return expenses.stream()
                .collect(
                        Collectors.groupingBy(
                                Expense::getCategory,
                                LinkedHashMap::new,
                                Collectors.summingDouble(
                                        Expense::getAmount
                                )
                        )
                );
    }

    // =========================================================
    // RECURRING EXPENSES
    // =========================================================

    public List<Expense> getRecurringExpenses() {

        return expenses.stream()
                .filter(Expense::isRecurring)
                .collect(Collectors.toList());
    }

    // =========================================================
    // COUNT EXPENSES
    // =========================================================

    public int getExpenseCount() {

        return expenses.size();
    }

    // =========================================================
    // TOTAL RECURRING EXPENSES
    // =========================================================

    public double calculateRecurringTotal() {

        return expenses.stream()
                .filter(Expense::isRecurring)
                .mapToDouble(
                        Expense::getAmount
                )
                .sum();
    }

    // =========================================================
    // HIGHEST EXPENSE
    // =========================================================

    public Expense getHighestExpense() {

        return expenses.stream()
                .max(
                        Comparator.comparingDouble(
                                Expense::getAmount
                        )
                )
                .orElse(null);
    }

    // =========================================================
    // LOWEST EXPENSE
    // =========================================================

    public Expense getLowestExpense() {

        return expenses.stream()
                .min(
                        Comparator.comparingDouble(
                                Expense::getAmount
                        )
                )
                .orElse(null);
    }
}