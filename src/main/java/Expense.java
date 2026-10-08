import java.time.LocalDate;

public class Expense {

    private final int id;
    private final double amount;
    private final LocalDate date;
    private final String category;
    private final String description;
    private final Frequency frequency;

    // =========================================================
    // CONSTRUCTOR - ONE TIME EXPENSE
    // =========================================================

    public Expense(int id,
                   double amount,
                   LocalDate date,
                   String category,
                   String description) {

        this(
                id,
                amount,
                date,
                category,
                description,
                Frequency.ONE_TIME
        );
    }

    // =========================================================
    // MAIN CONSTRUCTOR
    // =========================================================

    public Expense(int id,
                   double amount,
                   LocalDate date,
                   String category,
                   String description,
                   Frequency frequency) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive"
            );
        }

        if (date == null) {
            throw new IllegalArgumentException(
                    "Date cannot be null"
            );
        }

        if (category == null ||
                category.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Category cannot be empty"
            );
        }

        if (description == null ||
                description.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Description cannot be empty"
            );
        }

        if (frequency == null) {
            throw new IllegalArgumentException(
                    "Frequency cannot be null"
            );
        }

        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category.trim();
        this.description = description.trim();
        this.frequency = frequency;
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    // =========================================================
    // RECURRING CHECK
    // =========================================================

    public boolean isRecurring() {
        return frequency != Frequency.ONE_TIME;
    }

    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {

        return String.format(
                "ID: %d | Amount: %.2f | Date: %s | Category: %s | Desc: %s | Frequency: %s",
                id,
                amount,
                date,
                category,
                description,
                frequency
        );
    }
}