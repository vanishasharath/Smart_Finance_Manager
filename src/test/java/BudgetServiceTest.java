import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class BudgetServiceTest {

    @Test
    void shouldRejectInvalidMonth() {

        BudgetDAO dao = new BudgetDAO();
        BudgetService service =
                new BudgetService(dao);

        // Blank month should be rejected before DAO access.
        assertFalse(
                service.setBudget(
                        "",
                        30000.0
                )
        );
    }


    @Test
    void shouldRejectZeroBudget() {

        BudgetDAO dao = new BudgetDAO();
        BudgetService service =
                new BudgetService(dao);

        assertFalse(
                service.setBudget(
                        "2026-10",
                        0.0
                )
        );
    }


    @Test
    void shouldRejectNegativeBudget() {

        BudgetDAO dao = new BudgetDAO();
        BudgetService service =
                new BudgetService(dao);

        assertFalse(
                service.setBudget(
                        "2026-10",
                        -1000.0
                )
        );
    }


    @Test
    void shouldReturnNullForBlankMonth() {

        BudgetDAO dao = new BudgetDAO();
        BudgetService service =
                new BudgetService(dao);

        assertNull(
                service.getBudget("")
        );
    }


    @Test
    void shouldRejectDeleteForBlankMonth() {

        BudgetDAO dao = new BudgetDAO();
        BudgetService service =
                new BudgetService(dao);

        assertFalse(
                service.deleteBudget("")
        );
    }
}
