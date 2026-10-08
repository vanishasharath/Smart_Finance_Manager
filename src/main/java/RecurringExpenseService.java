import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class RecurringExpenseService {

    /**
     * Calculates the next occurrence of a recurring expense
     * after the given date.
     */
    public LocalDate getNextOccurrence(
            Expense expense,
            LocalDate fromDate) {

        if (expense == null ||
                fromDate == null ||
                !expense.isRecurring()) {
            return null;
        }

        LocalDate nextDate =
                expense.getDate();

        Frequency frequency =
                expense.getFrequency();

        while (!nextDate.isAfter(fromDate)) {

            switch (frequency) {

                case DAILY:
                    nextDate =
                            nextDate.plusDays(1);
                    break;

                case WEEKLY:
                    nextDate =
                            nextDate.plusWeeks(1);
                    break;

                case MONTHLY:
                    nextDate =
                            addMonthsSafely(
                                    nextDate,
                                    1
                            );
                    break;

                case YEARLY:
                    nextDate =
                            addYearsSafely(
                                    nextDate,
                                    1
                            );
                    break;

                case ONE_TIME:
                    return null;
            }
        }

        return nextDate;
    }


    /**
     * Calculates the estimated monthly cost
     * of a recurring expense.
     */
    public double calculateMonthlyProjection(
            Expense expense) {

        if (expense == null ||
                !expense.isRecurring()) {
            return 0;
        }

        double amount =
                expense.getAmount();

        switch (expense.getFrequency()) {

            case DAILY:
                return amount * (365.0 / 12.0);

            case WEEKLY:
                return amount * (52.0 / 12.0);

            case MONTHLY:
                return amount;

            case YEARLY:
                return amount / 12.0;

            case ONE_TIME:
            default:
                return 0;
        }
    }


    /**
     * Calculates the total estimated monthly
     * recurring expenditure.
     */
    public double calculateTotalMonthlyProjection(
            List<Expense> expenses) {

        if (expenses == null ||
                expenses.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (Expense expense : expenses) {

            total +=
                    calculateMonthlyProjection(
                            expense
                    );
        }

        return total;
    }


    /**
     * Returns only recurring expenses.
     */
    public List<Expense> getRecurringExpenses(
            List<Expense> expenses) {

        List<Expense> recurringExpenses =
                new ArrayList<>();

        if (expenses == null) {
            return recurringExpenses;
        }

        for (Expense expense : expenses) {

            if (expense != null &&
                    expense.isRecurring()) {

                recurringExpenses.add(expense);
            }
        }

        return recurringExpenses;
    }


    /**
     * Safely adds months while handling dates such as
     * January 31 -> February 28/29.
     */
    private LocalDate addMonthsSafely(
            LocalDate date,
            int months) {

        YearMonth targetMonth =
                YearMonth.from(date)
                        .plusMonths(months);

        int day =
                Math.min(
                        date.getDayOfMonth(),
                        targetMonth.lengthOfMonth()
                );

        return targetMonth.atDay(day);
    }


    /**
     * Safely adds years while handling February 29.
     */
    private LocalDate addYearsSafely(
            LocalDate date,
            int years) {

        YearMonth targetMonth =
                YearMonth.of(
                        date.getYear() + years,
                        date.getMonth()
                );

        int day =
                Math.min(
                        date.getDayOfMonth(),
                        targetMonth.lengthOfMonth()
                );

        return targetMonth.atDay(day);
    }
}