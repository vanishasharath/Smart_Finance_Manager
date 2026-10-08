import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CryptoDAO {

    public boolean insertHolding(
            CryptoHolding holding) {

        String sql =
                "INSERT INTO crypto_holdings " +
                "(asset_id, symbol, quantity, buy_price, purchase_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    holding.getAssetId()
            );

            statement.setString(
                    2,
                    holding.getSymbol()
            );

            statement.setDouble(
                    3,
                    holding.getQuantity()
            );

            statement.setDouble(
                    4,
                    holding.getBuyPrice()
            );

            statement.setObject(
                    5,
                    holding.getPurchaseDate()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error saving crypto holding: "
                            + e.getMessage()
            );

            return false;
        }
    }


    public ArrayList<CryptoHolding> getAllHoldings() {

        ArrayList<CryptoHolding> holdings =
                new ArrayList<>();

        String sql =
                "SELECT id, asset_id, symbol, " +
                "quantity, buy_price, purchase_date " +
                "FROM crypto_holdings " +
                "ORDER BY id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                int id =
                        resultSet.getInt("id");

                String assetId =
                        resultSet.getString("asset_id");

                String symbol =
                        resultSet.getString("symbol");

                double quantity =
                        resultSet.getDouble("quantity");

                double buyPrice =
                        resultSet.getDouble("buy_price");

                LocalDate purchaseDate =
                        resultSet.getDate(
                                "purchase_date"
                        ).toLocalDate();

                CryptoHolding holding =
                        new CryptoHolding(
                                id,
                                assetId,
                                symbol,
                                quantity,
                                buyPrice,
                                purchaseDate
                        );

                holdings.add(holding);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading crypto holdings: "
                            + e.getMessage()
            );
        }

        return holdings;
    }


    public boolean deleteHolding(int id) {

        String sql =
                "DELETE FROM crypto_holdings " +
                "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting crypto holding: "
                            + e.getMessage()
            );

            return false;
        }
    }
}