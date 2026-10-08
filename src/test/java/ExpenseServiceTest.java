import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseServiceTest {

    private ExpenseService service;

    @BeforeEach
    void setUp() {

        ArrayList<Expense> expenses =
                new ArrayList<>();

        expenses.add(
                new Expense(
                        1,
                        500.0,
                        LocalDate.of(2026, 10, 1),
                        "Food",
                        "Lunch",
                        Frequency.ONE_TIME
                )
        );

        expenses.add(
                new Expense(
                        2,
                        1500.0,
                        LocalDate.of(2026, 10, 5),
                        "Travel",
                        "Cab",
                        Frequency.ONE_TIME
                )
        );

        expenses.add(
                new Expense(
                        3,
                        3000.0,
                        LocalDate.of(2026, 10, 9),
                        "Gym",
                        "Fitness",
                        Frequency.MONTHLY
                )
        );

        service =
                new ExpenseService(
                        expenses,
                        null
                );
    }


    @Test
    void shouldSearchExpenses() {

        List<Expense> results =
                service.searchExpenses("lunch");

        assertEquals(1, results.size());

        assertEquals(
                "Food",
                results.get(0).getCategory()
        );
    }


    @Test
    void shouldSearchByCategory() {

        List<Expense> results =
                service.searchExpenses("travel");

        assertEquals(1, results.size());

        assertEquals(
                1500.0,
                results.get(0).getAmount()
        );
    }


    @Test
    void shouldFilterByCategory() {

        List<Expense> results =
                service.filterByCategory("Food");

        assertEquals(1, results.size());

        assertEquals(
                500.0,
                results.get(0).getAmount()
        );
    }


    @Test
    void shouldFilterByAmountRange() {

        List<Expense> results =
                service.filterByAmountRange(
                        1000.0,
                        2000.0
                );

        assertEquals(1, results.size());

        assertEquals(
                "Travel",
                results.get(0).getCategory()
        );
    }


    @Test
    void shouldSortByAmount() {

        List<Expense> results =
                service.sortByAmount();

        assertEquals(
                500.0,
                results.get(0).getAmount()
        );

        assertEquals(
                3000.0,
                results.get(2).getAmount()
        );
    }


    @Test
    void shouldSortByAmountDescending() {

        List<Expense> results =
                service.sortByAmountDescending();

        assertEquals(
                3000.0,
                results.get(0).getAmount()
        );

        assertEquals(
                500.0,
                results.get(2).getAmount()
        );
    }


    @Test
    void shouldSortByDate() {

        List<Expense> results =
                service.sortByDate();

        assertEquals(
                LocalDate.of(2026, 10, 1),
                results.get(0).getDate()
        );

        assertEquals(
                LocalDate.of(2026, 10, 9),
                results.get(2).getDate()
        );
    }


    @Test
    void shouldCalculateMonthlyTotal() {

        double total =
                service.calculateMonthlyTotal(
                        2026,
                        10
                );

        assertEquals(
                5000.0,
                total,
                0.01
        );
    }


    @Test
    void shouldCalculateCategoryTotal() {

        double total =
                service.calculateCategoryTotal(
                        "Travel"
                );

        assertEquals(
                1500.0,
                total,
                0.01
        );
    }


    @Test
    void shouldCalculateTotalSpending() {

        double total =
                service.calculateTotalSpending();

        assertEquals(
                5000.0,
                total,
                0.01
        );
    }


    @Test
    void shouldCalculateAverageSpending() {

        double average =
                service.calculateAverageSpending();

        assertEquals(
                1666.6667,
                average,
                0.01
        );
    }


    @Test
    void shouldFindTopCategory() {

        String category =
                service.getTopCategory();

        assertEquals(
                "Gym",
                category
        );
    }


    @Test
    void shouldCountExpenses() {

        assertEquals(
                3,
                service.getExpenseCount()
        );
    }
}