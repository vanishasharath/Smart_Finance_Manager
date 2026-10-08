import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetDAO {

    // =========================================================
    // SET / INSERT / UPDATE BUDGET
    // =========================================================

    public boolean saveBudget(
            String month,
            double amount) {

        String sql =
                "INSERT INTO budgets " +
                "(budget_month, amount) " +
                "VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE amount = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, month);
            statement.setDouble(2, amount);
            statement.setDouble(3, amount);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error saving budget: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // GET BUDGET
    // =========================================================

    public Double getBudget(String month) {

        String sql =
                "SELECT amount " +
                "FROM budgets " +
                "WHERE budget_month = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, month);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return resultSet.getDouble(
                            "amount"
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving budget: "
                            + e.getMessage()
            );
        }

        return null;
    }


    // =========================================================
    // DELETE BUDGET
    // =========================================================

    public boolean deleteBudget(String month) {

        String sql =
                "DELETE FROM budgets " +
                "WHERE budget_month = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, month);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting budget: "
                            + e.getMessage()
            );

            return false;
        }
    }
}