import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Main {



    // =========================================================

    // MENU

    // =========================================================



    private static void printMenu() {



        System.out.println("\n========================================");

        System.out.println("       SMART FINANCE MANAGER");

        System.out.println("========================================");



        System.out.println("\n--- Expense Management ---");

        System.out.println("1.  Add Expense");

        System.out.println("2.  View Expenses");

        System.out.println("3.  Delete Expense");

        System.out.println("4.  Update Expense");

        System.out.println("5.  Search Expenses");



        System.out.println("\n--- Filtering & Sorting ---");

        System.out.println("6.  Filter by Category");

        System.out.println("7.  Filter by Amount Range");

        System.out.println("8.  Sort by Amount");

        System.out.println("9.  Sort by Amount (Highest First)");

        System.out.println("10. Sort by Date");



        System.out.println("\n--- Analytics ---");

        System.out.println("11. Monthly Total");

        System.out.println("12. Category Total");

        System.out.println("13. Spending Insights");



        System.out.println("\n--- Budget ---");

        System.out.println("14. Set Monthly Budget");

        System.out.println("15. View Budget Status");



        System.out.println("\n--- Recurring Expenses ---");

        System.out.println("16. View Recurring Expenses");



        System.out.println("\n--- Data Management ---");

        System.out.println("17. Export Expenses to CSV");

        System.out.println("\n--- Crypto Portfolio ---");
        System.out.println("18. Add Crypto Holding");
        System.out.println("19. View Crypto Portfolio");
        System.out.println("20. Delete Crypto Holding");
        System.out.println("21. Exit");



        System.out.println("========================================");

        System.out.print("Choose an option: ");

    }





    // =========================================================

    // MAIN

    // =========================================================



    public static void main(String[] args) {



        Scanner sc = new Scanner(System.in);



        ExpenseManager manager =

                new ExpenseManager();



        while (true) {



            printMenu();



            int choice;



            try {



                choice = sc.nextInt();

                sc.nextLine();



            } catch (InputMismatchException e) {



                System.out.println(

                        "Invalid input! Please enter a number."

                );



                sc.nextLine();

                continue;

            }





            switch (choice) {



                // =====================================================

                // 1. ADD EXPENSE

                // =====================================================



                case 1:



                    double amount;



                    try {



                        System.out.print("Amount: ");

                        amount = sc.nextDouble();

                        sc.nextLine();



                    } catch (InputMismatchException e) {



                        System.out.println(

                                "Invalid amount!"

                        );



                        sc.nextLine();

                        break;

                    }



                    if (amount <= 0) {



                        System.out.println(

                                "Amount must be greater than zero!"

                        );



                        break;

                    }





                    // DATE



                    LocalDate date;



                    while (true) {



                        System.out.print(

                                "Date (YYYY-MM-DD): "

                        );



                        String dateInput =

                                sc.nextLine().trim();



                        try {



                            date =

                                    LocalDate.parse(

                                            dateInput

                                    );



                            break;



                        } catch (DateTimeParseException e) {



                            System.out.println(

                                    "Invalid date. Please use YYYY-MM-DD."

                            );

                        }

                    }





                    // CATEGORY



                    String category;



                    while (true) {



                        System.out.print(

                                "Category: "

                        );



                        category =

                                sc.nextLine().trim();



                        if (!category.isEmpty()) {

                            break;

                        }



                        System.out.println(

                                "Category cannot be empty."

                        );

                    }





                    // DESCRIPTION



                    String description;



                    while (true) {



                        System.out.print(

                                "Description: "

                        );



                        description =

                                sc.nextLine().trim();



                        if (!description.isEmpty()) {

                            break;

                        }



                        System.out.println(

                                "Description cannot be empty."

                        );

                    }





                    // FREQUENCY



                    Frequency frequency =

                            chooseFrequency(sc);





                    // SAVE TO DATABASE



                    try {



                        manager.addExpense(

                                amount,

                                date,

                                category,

                                description,

                                frequency

                        );



                    } catch (IllegalArgumentException e) {



                        System.out.println(

                                "Error: "

                                        + e.getMessage()

                        );

                    }



                    break;





                // =====================================================

                // 2. VIEW EXPENSES

                // =====================================================



                case 2:



                    manager.viewExpenses();



                    break;





                // =====================================================

                // 3. DELETE EXPENSE

                // =====================================================



                case 3:



                    try {



                        System.out.print(

                                "Enter expense ID to delete: "

                        );



                        int id =

                                sc.nextInt();



                        sc.nextLine();



                        manager.deleteExpense(id);



                    } catch (InputMismatchException e) {



                        System.out.println(

                                "Invalid ID!"

                        );



                        sc.nextLine();

                    }



                    break;





                // =====================================================

                // 4. UPDATE EXPENSE

                // =====================================================



                case 4:



                    updateExpense(

                            sc,

                            manager

                    );



                    break;





                // =====================================================

                // 5. SEARCH EXPENSES

                // =====================================================



                case 5:



                    System.out.print(

                            "Enter description or category keyword: "

                    );



                    String keyword =

                            sc.nextLine().trim();



                    if (keyword.isEmpty()) {



                        System.out.println(

                                "Search keyword cannot be empty."

                        );



                    } else {



                        manager.searchExpenses(

                                keyword

                        );

                    }



                    break;





                // =====================================================

                // 6. FILTER BY CATEGORY

                // =====================================================



                case 6:



                    System.out.print(

                            "Enter category: "

                    );



                    String filterCategory =

                            sc.nextLine().trim();



                    manager.filterByCategory(

                            filterCategory

                    );



                    break;





                // =====================================================

                // 7. FILTER BY AMOUNT

                // =====================================================



                case 7:



                    try {



                        System.out.print(

                                "Minimum amount: "

                        );



                        double minimum =

                                sc.nextDouble();



                        System.out.print(

                                "Maximum amount: "

                        );



                        double maximum =

                                sc.nextDouble();



                        sc.nextLine();



                        if (minimum < 0 ||

                                maximum < 0 ||

                                minimum > maximum) {



                            System.out.println(

                                    "Invalid amount range!"

                            );



                            break;

                        }



                        manager.filterByAmount(

                                minimum,

                                maximum

                        );



                    } catch (InputMismatchException e) {



                        System.out.println(

                                "Invalid amount!"

                        );



                        sc.nextLine();

                    }



                    break;





                // =====================================================

                // 8. SORT BY AMOUNT

                // =====================================================



                case 8:



                    manager.sortByAmount();



                    break;





                // =====================================================

                // 9. SORT BY AMOUNT DESCENDING

                // =====================================================



                case 9:



                    manager.sortByAmountDescending();



                    break;





                // =====================================================

                // 10. SORT BY DATE

                // =====================================================



                case 10:



                    manager.sortByDate();



                    break;





                // =====================================================

                // 11. MONTHLY TOTAL

                // =====================================================



                case 11:



                    String month =

                            readValidMonth(sc);



                    manager.monthlyTotal(month);



                    break;





                // =====================================================

                // 12. CATEGORY TOTAL

                // =====================================================



                case 12:



                    System.out.print(

                            "Enter category: "

                    );



                    String categoryName =

                            sc.nextLine().trim();



                    if (categoryName.isEmpty()) {



                        System.out.println(

                                "Category cannot be empty."

                        );



                    } else {



                        manager.categoryTotal(

                                categoryName

                        );

                    }



                    break;





                // =====================================================

                // 13. SPENDING INSIGHTS

                // =====================================================



                case 13:



                    manager.showInsights();



                    break;





                // =====================================================

                // 14. SET MONTHLY BUDGET

                // =====================================================


                case 14: {

                        String budgetMonth =
                                readValidMonth(sc);
                    
                        try {
                    
                            System.out.print(
                                    "Enter monthly budget: "
                            );
                    
                            double budget =
                                    sc.nextDouble();
                    
                            sc.nextLine();
                    
                            if (budget <= 0) {
                    
                                System.out.println(
                                        "Budget must be greater than zero."
                                );
                    
                                break;
                            }
                    
                            manager.setBudget(
                                    budgetMonth,
                                    budget
                            );
                    
                        } catch (InputMismatchException e) {
                    
                            System.out.println(
                                    "Invalid budget!"
                            );
                    
                            sc.nextLine();
                        }
                    
                        break;
                
                }


                // =====================================================

                // 15. VIEW BUDGET STATUS

                // =====================================================



                case 15:



                    String budgetMonth =

                            readValidMonth(sc);



                    manager.showBudgetStatus(

                            budgetMonth

                    );



                    break;
        





                // =====================================================

                // 16. RECURRING EXPENSES

                // =====================================================



                case 16:



                    manager.viewRecurringExpenses();



                    break;





                // =====================================================

                // 17. EXPORT CSV

                // =====================================================



                case 17:



                    manager.exportToCSV();



                    break;





                // =====================================================

                // =====================================================
                // 18. ADD CRYPTO HOLDING
                // =====================================================

                case 18:

                    addCryptoHolding(
                            sc,
                            manager
                    );

                    break;


                // =====================================================
                // 19. VIEW CRYPTO PORTFOLIO
                // =====================================================

                case 19:

                    manager.viewCryptoPortfolio();

                    break;


                // =====================================================
                // 20. DELETE CRYPTO HOLDING
                // =====================================================

                case 20:

                    try {

                        System.out.print(
                                "Enter crypto holding ID to delete: "
                        );

                        int cryptoId =
                                sc.nextInt();

                        sc.nextLine();

                        manager.deleteCryptoHolding(
                                cryptoId
                        );

                    } catch (InputMismatchException e) {

                        System.out.println(
                                "Invalid ID!"
                        );

                        sc.nextLine();
                    }

                    break;


                // =====================================================
                // 21. EXIT
                // =====================================================

                case 21:

                    System.out.println(
                            "\nThank you for using Smart Finance Manager!"
                    );

                    System.out.println(
                            "All database changes have already been saved."
                    );

                    sc.close();

                    return;


                // INVALID OPTION

                // =====================================================



                default:



                    System.out.println(

                            "Invalid choice! Please select 1-21."

                    );

            }

        }

    }





    // =========================================================

    // =========================================================
    // ADD CRYPTO HOLDING
    // =========================================================

    private static void addCryptoHolding(
            Scanner sc,
            ExpenseManager manager) {

        System.out.println(
                "\n========== ADD CRYPTO HOLDING =========="
        );

        System.out.print(
                "CoinGecko asset ID (e.g. bitcoin, ethereum, solana): "
        );

        String assetId =
                sc.nextLine().trim().toLowerCase();

        if (assetId.isEmpty()) {

            System.out.println(
                    "Asset ID cannot be empty."
            );

            return;
        }

        System.out.print(
                "Symbol (e.g. BTC, ETH, SOL): "
        );

        String symbol =
                sc.nextLine().trim().toUpperCase();

        if (symbol.isEmpty()) {

            System.out.println(
                    "Symbol cannot be empty."
            );

            return;
        }

        double quantity;

        try {

            System.out.print("Quantity: ");

            quantity =
                    sc.nextDouble();

            sc.nextLine();

        } catch (InputMismatchException e) {

            System.out.println(
                    "Invalid quantity!"
            );

            sc.nextLine();
            return;
        }

        if (quantity <= 0) {

            System.out.println(
                    "Quantity must be greater than zero."
            );

            return;
        }

        double buyPrice;

        try {

            System.out.print(
                    "Buy price per coin (INR): "
            );

            buyPrice =
                    sc.nextDouble();

            sc.nextLine();

        } catch (InputMismatchException e) {

            System.out.println(
                    "Invalid buy price!"
            );

            sc.nextLine();
            return;
        }

        if (buyPrice <= 0) {

            System.out.println(
                    "Buy price must be greater than zero."
            );

            return;
        }

        LocalDate purchaseDate;

        while (true) {

            System.out.print(
                    "Purchase date (YYYY-MM-DD): "
            );

            String dateInput =
                    sc.nextLine().trim();

            try {

                purchaseDate =
                        LocalDate.parse(dateInput);

                break;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. Please use YYYY-MM-DD."
                );
            }
        }

        manager.addCryptoHolding(
                assetId,
                symbol,
                quantity,
                buyPrice,
                purchaseDate
        );
    }


    // READ VALID MONTH

    // =========================================================



    private static String readValidMonth(

            Scanner sc) {



        while (true) {



            System.out.print(

                    "Enter month (YYYY-MM): "

            );



            String month =

                    sc.nextLine().trim();



            try {



                YearMonth.parse(month);



                return month;



            } catch (DateTimeParseException e) {



                System.out.println(

                        "Invalid month. Please use YYYY-MM."

                );

            }

        }

    }





    // =========================================================

    // UPDATE EXPENSE

    // =========================================================



    private static void updateExpense(

            Scanner sc,

            ExpenseManager manager) {



        try {



            System.out.print(

                    "Enter expense ID to update: "

            );



            int id =

                    sc.nextInt();



            sc.nextLine();





            // AMOUNT



            System.out.print(

                    "New amount: "

            );



            double amount =

                    sc.nextDouble();



            sc.nextLine();



            if (amount <= 0) {



                System.out.println(

                        "Amount must be greater than zero."

                );



                return;

            }





            // DATE



            LocalDate date;



            while (true) {



                System.out.print(

                        "New date (YYYY-MM-DD): "

                );



                String dateInput =

                        sc.nextLine().trim();



                try {



                    date =

                            LocalDate.parse(

                                    dateInput

                            );



                    break;



                } catch (DateTimeParseException e) {



                    System.out.println(

                            "Invalid date. Please use YYYY-MM-DD."

                    );

                }

            }





            // CATEGORY



            System.out.print(

                    "New category: "

            );



            String category =

                    sc.nextLine().trim();



            if (category.isEmpty()) {



                System.out.println(

                        "Category cannot be empty."

                );



                return;

            }





            // DESCRIPTION



            System.out.print(

                    "New description: "

            );



            String description =

                    sc.nextLine().trim();



            if (description.isEmpty()) {



                System.out.println(

                        "Description cannot be empty."

                );



                return;

            }





            // FREQUENCY



            Frequency frequency =

                    chooseFrequency(sc);





            // UPDATE DATABASE



            manager.updateExpense(

                    id,

                    amount,

                    date,

                    category,

                    description,

                    frequency

            );



        } catch (InputMismatchException e) {



            System.out.println(

                    "Invalid input!"

            );



            sc.nextLine();

        }

    }



    // =========================================================
// FREQUENCY SELECTION
// =========================================================

private static Frequency chooseFrequency(Scanner sc) {

        while (true) {
    
            System.out.println("\nExpense Frequency:");
            System.out.println("1. One Time");
            System.out.println("2. Daily");
            System.out.println("3. Weekly");
            System.out.println("4. Monthly");
            System.out.println("5. Yearly");
    
            System.out.print("Choose: ");
    
            int choice;
    
            try {
    
                choice = sc.nextInt();
                sc.nextLine();
    
            } catch (InputMismatchException e) {
    
                System.out.println(
                        "Invalid choice! Enter a number from 1-5."
                );
    
                sc.nextLine();
                continue;
            }
    
            switch (choice) {
    
                case 1:
                    return Frequency.ONE_TIME;
    
                case 2:
                    return Frequency.DAILY;
    
                case 3:
                    return Frequency.WEEKLY;
    
                case 4:
                    return Frequency.MONTHLY;
    
                case 5:
                    return Frequency.YEARLY;
    
                default:
                    System.out.println(
                            "Invalid choice! Enter a number from 1-5."
                    );
            }
        }
    }
}