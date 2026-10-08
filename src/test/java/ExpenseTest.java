import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    @Test
    void shouldCreateValidExpense() {

        Expense expense =
                new Expense(
                        1,
                        500.0,
                        LocalDate.of(2026, 10, 8),
                        "Food",
                        "Lunch",
                        Frequency.ONE_TIME
                );

        assertEquals(1, expense.getId());
        assertEquals(500.0, expense.getAmount());
        assertEquals("Food", expense.getCategory());
        assertEquals("Lunch", expense.getDescription());
        assertEquals(Frequency.ONE_TIME,
                expense.getFrequency());
    }


    @Test
    void shouldRejectNegativeAmount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Expense(
                        1,
                        -500.0,
                        LocalDate.of(2026, 10, 8),
                        "Food",
                        "Lunch",
                        Frequency.ONE_TIME
                )
        );
    }


    @Test
    void shouldRejectZeroAmount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Expense(
                        1,
                        0.0,
                        LocalDate.of(2026, 10, 8),
                        "Food",
                        "Lunch",
                        Frequency.ONE_TIME
                )
        );
    }


    @Test
    void shouldRejectEmptyCategory() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Expense(
                        1,
                        500.0,
                        LocalDate.of(2026, 10, 8),
                        "",
                        "Lunch",
                        Frequency.ONE_TIME
                )
        );
    }


    @Test
    void shouldIdentifyRecurringExpense() {

        Expense expense =
                new Expense(
                        1,
                        3000.0,
                        LocalDate.of(2026, 10, 9),
                        "Gym",
                        "Fitness",
                        Frequency.DAILY
                );

        assertTrue(expense.isRecurring());
    }


    @Test
    void shouldIdentifyOneTimeExpense() {

        Expense expense =
                new Expense(
                        1,
                        500.0,
                        LocalDate.of(2026, 10, 8),
                        "Food",
                        "Lunch",
                        Frequency.ONE_TIME
                );

        assertFalse(expense.isRecurring());
    }
}