public class Expense {
    private int id;
    private double amount;
    private String date;
    private String category;
    private String description;

    public Expense(int id, double amount, String date, String category, String description) {
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
        return id + " | " + amount + " | " + date + " | " + category + " | " + description;
    }
}

