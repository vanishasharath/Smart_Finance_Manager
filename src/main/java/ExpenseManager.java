import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpenseManager {


    // =========================================================

    // FIELDS

    // =========================================================


    private final ArrayList<Expense> expenses =

            new ArrayList<>();


    private final ExpenseDAO expenseDAO;

    private final ExpenseService service;


    private final BudgetDAO budgetDAO;

    private final BudgetService budgetService;

    private final RecurringExpenseService recurringExpenseService;

    private final ArrayList<CryptoHolding> cryptoHoldings =
            new ArrayList<>();

    private final CryptoDAO cryptoDAO;
    private final CryptoService cryptoService;
    private final CryptoPriceService cryptoPriceService;


    private int currentId = 1;


    private static final DateTimeFormatter MONTH_FORMAT =

            DateTimeFormatter.ofPattern("yyyy-MM");


    // =========================================================

    // CONSTRUCTOR

    // =========================================================


    public ExpenseManager() {


        expenseDAO = new ExpenseDAO();


        service =

                new ExpenseService(

                        expenses,

                        expenseDAO

                );


        budgetDAO = new BudgetDAO();


        budgetService =

                new BudgetService(

                        budgetDAO

                );

        recurringExpenseService =
                new RecurringExpenseService();

        cryptoDAO = new CryptoDAO();

        cryptoService =
                new CryptoService(
                        cryptoHoldings,
                        cryptoDAO
                );

        cryptoPriceService =
                new CryptoPriceService();


        // Load expenses from MySQL

        service.loadFromDatabase();


        // Generate next expense ID

        currentId = 1;


        for (Expense expense : expenses) {


            currentId =

                    Math.max(

                            currentId,

                            expense.getId() + 1

                    );

        }

        cryptoService.loadFromDatabase();

    }


    // =========================================================

    // ADD EXPENSE

    // =========================================================


    public void addExpense(

            double amount,

            LocalDate date,

            String category,

            String description,

            Frequency frequency) {


        try {


            Expense expense =

                    new Expense(

                            currentId,

                            amount,

                            date,

                            category,

                            description,

                            frequency

                    );


            boolean saved =

                    service.addExpense(expense);


            if (saved) {


                currentId++;


                System.out.println(

                        "Expense added successfully!"

                );


            } else {


                System.out.println(

                        "Failed to save expense."

                );

            }


        } catch (IllegalArgumentException e) {


            System.out.println(

                    "Invalid expense: "

                            + e.getMessage()

            );

        }

    }


    // =========================================================

    // VIEW ALL EXPENSES

    // =========================================================


    public void viewExpenses() {


        List<Expense> allExpenses =

                service.getAllExpenses();


        if (allExpenses.isEmpty()) {


            System.out.println(

                    "No expenses found."

            );


            return;

        }


        System.out.println(

                "\n========== ALL EXPENSES =========="

        );


        for (Expense expense : allExpenses) {


            System.out.println(expense);

        }


        System.out.println(

                "=================================="

        );


        System.out.println(

                "Total records: "

                        + allExpenses.size()

        );

    }


    // =========================================================

    // DELETE EXPENSE

    // =========================================================


    public void deleteExpense(int id) {


        boolean deleted =

                service.deleteExpense(id);


        if (deleted) {


            System.out.println(

                    "Expense deleted successfully!"

            );


        } else {


            System.out.println(

                    "Expense with ID "

                            + id

                            + " not found."

            );

        }

    }


    // =========================================================

    // UPDATE EXPENSE

    // =========================================================


    public void updateExpense(

            int id,

            double amount,

            LocalDate date,

            String category,

            String description,

            Frequency frequency) {


        try {


            Expense existingExpense =

                    service.findById(id);


            if (existingExpense == null) {


                System.out.println(

                        "Expense with ID "

                                + id

                                + " not found."

                );


                return;

            }


            boolean updated =

                    service.updateExpense(

                            id,

                            amount,

                            date,

                            category,

                            description,

                            frequency

                    );


            if (updated) {


                System.out.println(

                        "Expense updated successfully!"

                );


            } else {


                System.out.println(

                        "Failed to update expense."

                );

            }


        } catch (IllegalArgumentException e) {


            System.out.println(

                    "Invalid expense: "

                            + e.getMessage()

            );

        }

    }


    // =========================================================

    // SEARCH EXPENSES

    // =========================================================


    public void searchExpenses(

            String keyword) {


        List<Expense> results =

                service.searchExpenses(keyword);


        if (results.isEmpty()) {


            System.out.println(

                    "No matching expenses found."

            );


            return;

        }


        System.out.println(

                "\n========== SEARCH RESULTS =========="

        );


        for (Expense expense : results) {


            System.out.println(expense);

        }


        System.out.println(

                "===================================="

        );

    }


    // =========================================================

    // FILTER BY CATEGORY

    // =========================================================


    public void filterByCategory(

            String category) {


        List<Expense> results =

                service.filterByCategory(category);


        if (results.isEmpty()) {


            System.out.println(

                    "No expenses found for category: "

                            + category

            );


            return;

        }


        System.out.println(

                "\n========== CATEGORY: "

                        + category

                        + " =========="

        );


        for (Expense expense : results) {


            System.out.println(expense);

        }

    }


    // =========================================================

    // FILTER BY AMOUNT RANGE

    // =========================================================


    public void filterByAmountRange(

            double minimum,

            double maximum) {


        List<Expense> results =

                service.filterByAmountRange(

                        minimum,

                        maximum

                );


        if (results.isEmpty()) {


            System.out.println(

                    "No expenses found in the given amount range."

            );


            return;

        }


        System.out.println(

                "\n========== AMOUNT RANGE =========="

        );


        for (Expense expense : results) {


            System.out.println(expense);

        }


        System.out.println(

                "=================================="

        );

    }


    // =========================================================

    // COMPATIBILITY METHOD FOR MAIN

    // =========================================================


    public void filterByAmount(

            double minimum,

            double maximum) {


        filterByAmountRange(

                minimum,

                maximum

        );

    }


    // =========================================================

    // SORT BY AMOUNT

    // =========================================================


    public void sortByAmount() {


        List<Expense> results =

                service.sortByAmount();


        if (results.isEmpty()) {


            System.out.println(

                    "No expenses available."

            );


            return;

        }


        System.out.println(

                "\n========== SORTED BY AMOUNT =========="

        );


        for (Expense expense : results) {


            System.out.println(expense);

        }

    }


    // =========================================================

    // SORT BY AMOUNT - HIGHEST FIRST

    // =========================================================


    public void sortByAmountDescending() {


        List<Expense> results =

                service.sortByAmountDescending();


        if (results.isEmpty()) {


            System.out.println(

                    "No expenses available."

            );


            return;

        }


        System.out.println(

                "\n========== HIGHEST EXPENSES FIRST =========="

        );


        for (Expense expense : results) {


            System.out.println(expense);

        }

    }


    // =========================================================

    // SORT BY DATE

    // =========================================================


    public void sortByDate() {


        List<Expense> results =

                service.sortByDate();


        if (results.isEmpty()) {


            System.out.println(

                    "No expenses available."

            );


            return;

        }


        System.out.println(

                "\n========== SORTED BY DATE =========="

        );


        for (Expense expense : results) {


            System.out.println(expense);

        }

    }


    // =========================================================

    // MONTHLY TOTAL

    // =========================================================


    public void monthlyTotal(

            int year,

            int month) {


        double total =

                service.calculateMonthlyTotal(

                        year,

                        month

                );


        YearMonth yearMonth =

                YearMonth.of(

                        year,

                        month

                );


        System.out.println(

                "\n========== MONTHLY TOTAL =========="

        );


        System.out.println(

                "Month: "

                        + yearMonth

        );


        System.out.printf(

                "Total spending: %.2f%n",

                total

        );


        System.out.println(

                "==================================="

        );

    }


    // =========================================================

    // MONTHLY TOTAL - STRING VERSION

    // =========================================================


    public void monthlyTotal(

            String month) {


        try {


            YearMonth yearMonth =

                    YearMonth.parse(

                            month,

                            MONTH_FORMAT

                    );


            monthlyTotal(

                    yearMonth.getYear(),

                    yearMonth.getMonthValue()

            );


        } catch (Exception e) {


            System.out.println(

                    "Invalid month. Please use YYYY-MM."

            );

        }

    }


    // =========================================================

    // CATEGORY TOTAL

    // =========================================================


    public void categoryTotal(

            String category) {


        double total =

                service.calculateCategoryTotal(

                        category

                );


        System.out.println(

                "\n========== CATEGORY TOTAL =========="

        );


        System.out.println(

                "Category: "

                        + category

        );


        System.out.printf(

                "Total spending: %.2f%n",

                total

        );


        System.out.println(

                "===================================="

        );

    }


    // =========================================================

    // SPENDING INSIGHTS

    // =========================================================


    public void spendingInsights() {


        if (expenses.isEmpty()) {


            System.out.println(

                    "No expenses available for analysis."

            );


            return;

        }


        double total =

                service.calculateTotalSpending();


        double average =

                service.calculateAverageSpending();


        String topCategory =

                service.getTopCategory();


        Expense highest =

                service.getHighestExpense();


        Expense lowest =

                service.getLowestExpense();


        System.out.println(

                "\n========== SPENDING INSIGHTS =========="

        );


        System.out.printf(

                "Total Spending       : %.2f%n",

                total

        );


        System.out.printf(

                "Average Expense      : %.2f%n",

                average

        );


        System.out.println(

                "Top Spending Category: "

                        + topCategory

        );


        if (highest != null) {


            System.out.printf(

                    "Highest Expense      : %.2f%n",

                    highest.getAmount()

            );


            System.out.println(

                    "Highest Expense ID   : "

                            + highest.getId()

            );

        }


        if (lowest != null) {


            System.out.printf(

                    "Lowest Expense       : %.2f%n",

                    lowest.getAmount()

            );


            System.out.println(

                    "Lowest Expense ID    : "

                            + lowest.getId()

            );

        }


        System.out.println(

                "Number of Expenses   : "

                        + service.getExpenseCount()

        );


        System.out.println(

                "======================================="

        );


        System.out.println(

                "\nCategory-wise spending:"

        );


        Map<String, Double> categoryTotals =

                service.getCategoryTotals();


        for (Map.Entry<String, Double> entry :

                categoryTotals.entrySet()) {


            System.out.printf(

                    "%-20s : %.2f%n",

                    entry.getKey(),

                    entry.getValue()

            );

        }

    }


    // =========================================================

    // COMPATIBILITY METHOD FOR MAIN

    // =========================================================


    public void showInsights() {


        spendingInsights();

    }


    // =========================================================

    // SET MONTHLY BUDGET

    // =========================================================


    public void setBudget(

            String month,

            double budget) {


        if (budget <= 0) {


            System.out.println(

                    "Budget must be greater than zero."

            );


            return;

        }


        try {


            YearMonth.parse(

                    month,

                    MONTH_FORMAT

            );


        } catch (Exception e) {


            System.out.println(

                    "Invalid month. Please use YYYY-MM."

            );


            return;

        }


        boolean saved =

                budgetService.setBudget(

                        month,

                        budget

                );


        if (saved) {


            System.out.printf(

                    "Monthly budget for %s set to %.2f%n",

                    month,

                    budget

            );


        } else {


            System.out.println(

                    "Failed to save budget."

            );

        }

    }


    // =========================================================

    // COMPATIBILITY METHOD

    // Sets budget for current month

    // =========================================================


    public void setBudget(

            double budget) {


        String currentMonth =

                YearMonth.now()

                        .format(MONTH_FORMAT);


        setBudget(

                currentMonth,

                budget

        );

    }


    // =========================================================

    // BUDGET STATUS

    // =========================================================


    public void showBudgetStatus(

            String month) {


        try {


            YearMonth yearMonth =

                    YearMonth.parse(

                            month,

                            MONTH_FORMAT

                    );


            Double budget =

                    budgetService.getBudget(

                            month

                    );


            if (budget == null) {


                System.out.println(

                        "No budget has been set for "

                                + month

                );


                return;

            }


            double monthlySpending =

                    service.calculateMonthlyTotal(

                            yearMonth.getYear(),

                            yearMonth.getMonthValue()

                    );


            double remaining =

                    budget - monthlySpending;


            double percentage =

                    (monthlySpending / budget) * 100;


            System.out.println(

                    "\n========== BUDGET STATUS =========="

            );


            System.out.println(

                    "Month            : "

                            + month

            );


            System.out.printf(

                    "Monthly Budget   : %.2f%n",

                    budget

            );


            System.out.printf(

                    "Current Spending : %.2f%n",

                    monthlySpending

            );


            System.out.printf(

                    "Remaining Budget : %.2f%n",

                    remaining

            );


            System.out.printf(

                    "Budget Used      : %.2f%%%n",

                    percentage

            );


            if (remaining < 0) {


                System.out.printf(

                        "Status           : OVER BUDGET by %.2f%n",

                        Math.abs(remaining)

                );


            } else {


                System.out.println(

                        "Status           : WITHIN BUDGET"

                );

            }


            System.out.println(

                    "==================================="

            );


        } catch (Exception e) {


            System.out.println(

                    "Invalid month. Please use YYYY-MM."

            );

        }

    }


    // =========================================================

    // VIEW RECURRING EXPENSES

    // =========================================================


    public void viewRecurringExpenses() {

        List<Expense> recurring =
                recurringExpenseService
                        .getRecurringExpenses(expenses);

        if (recurring.isEmpty()) {

            System.out.println(
                    "No recurring expenses found."
            );

            return;
        }

        System.out.println(
                "\n========== RECURRING EXPENSES =========="
        );

        LocalDate today = LocalDate.now();

        double totalMonthlyProjection = 0;

        for (Expense expense : recurring) {

            double monthlyProjection =
                    recurringExpenseService
                            .calculateMonthlyProjection(
                                    expense
                            );

            LocalDate nextOccurrence =
                    recurringExpenseService
                            .getNextOccurrence(
                                    expense,
                                    today
                            );

            totalMonthlyProjection +=
                    monthlyProjection;

            System.out.println(
                    "\nID: " + expense.getId()
            );

            System.out.printf(
                    "Amount: ₹%.2f%n",
                    expense.getAmount()
            );

            System.out.println(
                    "Category: "
                            + expense.getCategory()
            );

            System.out.println(
                    "Description: "
                            + expense.getDescription()
            );

            System.out.println(
                    "Frequency: "
                            + expense.getFrequency()
            );

            System.out.println(
                    "Next Occurrence: "
                            + nextOccurrence
            );

            System.out.printf(
                    "Estimated Monthly Cost: ₹%.2f%n",
                    monthlyProjection
            );
        }

        System.out.println(
                "\n----------------------------------------"
        );

        System.out.printf(
                "Estimated Monthly Recurring Cost: ₹%.2f%n",
                totalMonthlyProjection
        );

        System.out.println(
                "========================================"
        );
    }


    // =========================================================

    // ADD CRYPTO HOLDING

    // =========================================================

    public void addCryptoHolding(
            String assetId,
            String symbol,
            double quantity,
            double buyPrice,
            LocalDate purchaseDate) {

        try {

            CryptoHolding holding =
                    new CryptoHolding(
                            0,
                            assetId,
                            symbol,
                            quantity,
                            buyPrice,
                            purchaseDate
                    );

            boolean saved =
                    cryptoService.addHolding(holding);

            if (saved) {

                cryptoService.loadFromDatabase();

                System.out.println(
                        "Crypto holding added successfully!"
                );

            } else {

                System.out.println(
                        "Failed to save crypto holding."
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid crypto holding: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================

    // VIEW CRYPTO PORTFOLIO

    // =========================================================

    public void viewCryptoPortfolio() {

        List<CryptoHolding> holdings =
                cryptoService.getAllHoldings();

        if (holdings.isEmpty()) {

            System.out.println(
                    "No crypto holdings found."
            );

            return;
        }

        ArrayList<String> assetIds =
                new ArrayList<>();

        for (CryptoHolding holding : holdings) {

            if (!assetIds.contains(
                    holding.getAssetId())) {

                assetIds.add(
                        holding.getAssetId()
                );
            }
        }

        Map<String, Double> currentPrices =
                cryptoPriceService.getCurrentPrices(
                        assetIds.toArray(new String[0])
                );

        System.out.println(
                "\n========== CRYPTO PORTFOLIO =========="
        );

        for (CryptoHolding holding : holdings) {

            Double currentPrice =
                    currentPrices.get(
                            holding.getAssetId()
                    );

            double invested =
                    holding.getInvestedAmount();

            System.out.println(
                    "\nID: "
                            + holding.getId()
            );

            System.out.println(
                    "Asset: "
                            + holding.getAssetId()
                            + " ("
                            + holding.getSymbol()
                            + ")"
            );

            System.out.printf(
                    "Quantity: %.6f%n",
                    holding.getQuantity()
            );

            System.out.printf(
                    "Buy Price: ₹%.2f%n",
                    holding.getBuyPrice()
            );

            System.out.printf(
                    "Invested: ₹%.2f%n",
                    invested
            );

            if (currentPrice != null) {

                double currentValue =
                        holding.getQuantity()
                                * currentPrice;

                double profitLoss =
                        currentValue - invested;

                double returnPercentage =
                        invested > 0
                                ? (profitLoss / invested) * 100
                                : 0;

                System.out.printf(
                        "Current Price: ₹%.2f%n",
                        currentPrice
                );

                System.out.printf(
                        "Current Value: ₹%.2f%n",
                        currentValue
                );

                System.out.printf(
                        "Profit/Loss: ₹%.2f%n",
                        profitLoss
                );

                System.out.printf(
                        "Return: %.2f%%%n",
                        returnPercentage
                );

            } else {

                System.out.println(
                        "Current Price: Unavailable"
                );

                System.out.println(
                        "Profit/Loss: Unavailable"
                );
            }

            System.out.println(
                    "Purchase Date: "
                            + holding.getPurchaseDate()
            );
        }

        double totalInvested =
                cryptoService.calculateTotalInvested();

        System.out.println(
                "\n----------------------------------------"
        );

        System.out.printf(
                "Total Invested: ₹%.2f%n",
                totalInvested
        );

        if (currentPrices.size() == assetIds.size()) {

            double currentValue =
                    cryptoService.calculateCurrentValue(
                            currentPrices
                    );

            double profitLoss =
                    cryptoService.calculateProfitLoss(
                            currentPrices
                    );

            double returnPercentage =
                    cryptoService.calculateReturnPercentage(
                            currentPrices
                    );

            System.out.printf(
                    "Current Portfolio Value: ₹%.2f%n",
                    currentValue
            );

            System.out.printf(
                    "Total Profit/Loss: ₹%.2f%n",
                    profitLoss
            );

            System.out.printf(
                    "Portfolio Return: %.2f%%%n",
                    returnPercentage
            );

        } else {

            System.out.println(
                    "Current portfolio value and total P/L are unavailable because one or more prices could not be retrieved."
            );
        }

        System.out.println(
                "========================================"
        );
    }


    // =========================================================

    // DELETE CRYPTO HOLDING

    // =========================================================

    public void deleteCryptoHolding(int id) {

        boolean deleted =
                cryptoService.deleteHolding(id);

        if (deleted) {

            System.out.println(
                    "Crypto holding deleted successfully!"
            );

        } else {

            System.out.println(
                    "Crypto holding with ID "
                            + id
                            + " not found."
            );
        }
    }


    // EXPORT TO CSV

    // =========================================================


    public void exportToCSV() {


        String fileName =

                "expenses_export.csv";


        List<Expense> allExpenses =

                service.getAllExpenses();


        if (allExpenses.isEmpty()) {


            System.out.println(

                    "No expenses available to export."

            );


            return;

        }


        try (

                FileWriter writer =

                        new FileWriter(fileName)

        ) {


            writer.write(

                    "ID,Amount,Date,Category,Description,Frequency\n"

            );


            for (Expense expense :

                    allExpenses) {


                writer.write(

                        expense.getId()

                                + ","

                                + expense.getAmount()

                                + ","

                                + expense.getDate()

                                + ","

                                + escapeCSV(

                                        expense.getCategory()

                                )

                                + ","

                                + escapeCSV(

                                        expense.getDescription()

                                )

                                + ","

                                + expense.getFrequency()

                                + "\n"

                );

            }


            System.out.println(

                    "Expenses exported successfully to "

                            + fileName

            );


        } catch (IOException e) {


            System.out.println(

                    "Error exporting expenses: "

                            + e.getMessage()

            );

        }

    }


    // =========================================================

    // CSV ESCAPING

    // =========================================================


    private String escapeCSV(
            String value) {

        if (value == null) {
            return "";
        }

        if (value.contains(",") ||
                value.contains("\"") ||
                value.contains("\n")) {

            value = value.replace(
                    "\"",
                    "\"\""
            );

            return "\"" + value + "\"";
        }

        return value;
    }
}