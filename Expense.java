public class Expense {
    private final int id;
    private final double amount;
    private final String date;
    private final String category;
    private final String description;

    public Expense(int id, double amount, String date, String category, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Amount: %.2f | Date: %s | Category: %s | Desc: %s",
                id, amount, date, category, description);
    }
}