public class BudgetService {

    private final BudgetDAO budgetDAO;

    public BudgetService(
            BudgetDAO budgetDAO) {

        this.budgetDAO = budgetDAO;
    }


    // =========================================================
    // SET BUDGET
    // =========================================================

    public boolean setBudget(
            String month,
            double amount) {

        if (month == null ||
                month.trim().isEmpty()) {

            return false;
        }

        if (amount <= 0) {

            return false;
        }

        return budgetDAO.saveBudget(
                month,
                amount
        );
    }


    // =========================================================
    // GET BUDGET
    // =========================================================

    public Double getBudget(
            String month) {

        if (month == null ||
                month.trim().isEmpty()) {

            return null;
        }

        return budgetDAO.getBudget(
                month
        );
    }


    // =========================================================
    // DELETE BUDGET
    // =========================================================

    public boolean deleteBudget(
            String month) {

        if (month == null ||
                month.trim().isEmpty()) {

            return false;
        }

        return budgetDAO.deleteBudget(
                month
        );
    }
}