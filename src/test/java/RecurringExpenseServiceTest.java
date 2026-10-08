import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RecurringExpenseServiceTest {

    private final RecurringExpenseService service =
            new RecurringExpenseService();


    @Test
    void shouldCalculateDailyMonthlyProjection() {

        Expense expense =
                new Expense(
                        1,
                        3000.0,
                        LocalDate.of(2026, 10, 9),
                        "Gym",
                        "Fitness",
                        Frequency.DAILY
                );

        double result =
                service.calculateMonthlyProjection(
                        expense
                );

        assertEquals(
                91250.0,
                result,
                0.01
        );
    }


    @Test
    void shouldCalculateMonthlyProjection() {

        Expense expense =
                new Expense(
                        2,
                        3000.0,
                        LocalDate.of(2026, 10, 9),
                        "Gym",
                        "Fitness",
                        Frequency.MONTHLY
                );

        double result =
                service.calculateMonthlyProjection(
                        expense
                );

        assertEquals(
                3000.0,
                result,
                0.01
        );
    }


    @Test
    void shouldCalculateYearlyProjection() {

        Expense expense =
                new Expense(
                        3,
                        3000.0,
                        LocalDate.of(2026, 10, 9),
                        "Insurance",
                        "Annual insurance",
                        Frequency.YEARLY
                );

        double result =
                service.calculateMonthlyProjection(
                        expense
                );

        assertEquals(
                250.0,
                result,
                0.01
        );
    }


    @Test
    void shouldCalculateNextDailyOccurrence() {

        Expense expense =
                new Expense(
                        4,
                        3000.0,
                        LocalDate.of(2026, 10, 9),
                        "Gym",
                        "Fitness",
                        Frequency.DAILY
                );

        LocalDate result =
                service.getNextOccurrence(
                        expense,
                        LocalDate.of(2026, 10, 8)
                );

        assertEquals(
                LocalDate.of(2026, 10, 9),
                result
        );
    }


    @Test
    void shouldCalculateNextMonthlyOccurrence() {

        Expense expense =
                new Expense(
                        5,
                        1500.0,
                        LocalDate.of(2026, 10, 9),
                        "Subscription",
                        "Streaming",
                        Frequency.MONTHLY
                );

        LocalDate result =
                service.getNextOccurrence(
                        expense,
                        LocalDate.of(2026, 10, 9)
                );

        assertEquals(
                LocalDate.of(2026, 11, 9),
                result
        );
    }


    @Test
    void shouldReturnNullForOneTimeExpense() {

        Expense expense =
                new Expense(
                        6,
                        500.0,
                        LocalDate.of(2026, 10, 8),
                        "Food",
                        "Lunch",
                        Frequency.ONE_TIME
                );

        LocalDate result =
                service.getNextOccurrence(
                        expense,
                        LocalDate.of(2026, 10, 8)
                );

        assertNull(result);
    }
}