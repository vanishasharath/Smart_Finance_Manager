import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseDAO {

    // =========================================================
    // INSERT EXPENSE
    // =========================================================

    public boolean insertExpense(Expense expense) {

        String sql =
                "INSERT INTO expenses " +
                "(id, amount, expense_date, category, description, frequency) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    expense.getId()
            );

            statement.setDouble(
                    2,
                    expense.getAmount()
            );

            statement.setObject(
                    3,
                    expense.getDate()
            );

            statement.setString(
                    4,
                    expense.getCategory()
            );

            statement.setString(
                    5,
                    expense.getDescription()
            );

            statement.setString(
                    6,
                    expense.getFrequency().name()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error inserting expense: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // GET ALL EXPENSES
    // =========================================================

    public ArrayList<Expense> getAllExpenses() {

        ArrayList<Expense> expenses =
                new ArrayList<>();

        String sql =
                "SELECT id, amount, expense_date, " +
                "category, description, frequency " +
                "FROM expenses " +
                "ORDER BY id";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                int id =
                        resultSet.getInt("id");

                double amount =
                        resultSet.getDouble("amount");

                LocalDate date =
                        resultSet.getDate(
                                "expense_date"
                        ).toLocalDate();

                String category =
                        resultSet.getString(
                                "category"
                        );

                String description =
                        resultSet.getString(
                                "description"
                        );

                Frequency frequency;

                try {

                    frequency =
                            Frequency.valueOf(
                                    resultSet
                                            .getString(
                                                    "frequency"
                                            )
                                            .toUpperCase()
                            );

                } catch (IllegalArgumentException e) {

                    frequency =
                            Frequency.ONE_TIME;
                }

                Expense expense =
                        new Expense(
                                id,
                                amount,
                                date,
                                category,
                                description,
                                frequency
                        );

                expenses.add(expense);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading expenses from database: "
                            + e.getMessage()
            );
        }

        return expenses;
    }

    // =========================================================
    // FIND EXPENSE BY ID
    // =========================================================

    public Expense findExpenseById(int id) {

        String sql =
                "SELECT id, amount, expense_date, " +
                "category, description, frequency " +
                "FROM expenses " +
                "WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapResultSetToExpense(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding expense: "
                            + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // UPDATE EXPENSE
    // =========================================================

    public boolean updateExpense(
            Expense expense) {

        String sql =
                "UPDATE expenses SET " +
                "amount = ?, " +
                "expense_date = ?, " +
                "category = ?, " +
                "description = ?, " +
                "frequency = ? " +
                "WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(
                    1,
                    expense.getAmount()
            );

            statement.setObject(
                    2,
                    expense.getDate()
            );

            statement.setString(
                    3,
                    expense.getCategory()
            );

            statement.setString(
                    4,
                    expense.getDescription()
            );

            statement.setString(
                    5,
                    expense.getFrequency().name()
            );

            statement.setInt(
                    6,
                    expense.getId()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating expense: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DELETE EXPENSE
    // =========================================================

    public boolean deleteExpense(int id) {

        String sql =
                "DELETE FROM expenses WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting expense: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // RESULT SET → EXPENSE
    // =========================================================

    private Expense mapResultSetToExpense(
            ResultSet resultSet)
            throws SQLException {

        int id =
                resultSet.getInt("id");

        double amount =
                resultSet.getDouble("amount");

        LocalDate date =
                resultSet.getDate(
                        "expense_date"
                ).toLocalDate();

        String category =
                resultSet.getString(
                        "category"
                );

        String description =
                resultSet.getString(
                        "description"
                );

        Frequency frequency;

        try {

            frequency =
                    Frequency.valueOf(
                            resultSet
                                    .getString(
                                            "frequency"
                                    )
                                    .toUpperCase()
                    );

        } catch (IllegalArgumentException e) {

            frequency =
                    Frequency.ONE_TIME;
        }

        return new Expense(
                id,
                amount,
                date,
                category,
                description,
                frequency
        );
    }
}