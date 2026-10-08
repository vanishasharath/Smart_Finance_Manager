import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CryptoService {

    private final ArrayList<CryptoHolding> holdings;
    private final CryptoDAO cryptoDAO;

    public CryptoService(
            ArrayList<CryptoHolding> holdings,
            CryptoDAO cryptoDAO) {

        this.holdings = holdings;
        this.cryptoDAO = cryptoDAO;
    }


    // =========================================================
    // ADD HOLDING
    // =========================================================

    public boolean addHolding(
            CryptoHolding holding) {

        if (holding == null) {
            return false;
        }

        boolean saved =
                cryptoDAO.insertHolding(
                        holding
                );

        if (saved) {
            holdings.add(holding);
        }

        return saved;
    }


    // =========================================================
    // LOAD HOLDINGS FROM DATABASE
    // =========================================================

    public void loadFromDatabase() {

        holdings.clear();

        ArrayList<CryptoHolding> databaseHoldings =
                cryptoDAO.getAllHoldings();

        holdings.addAll(
                databaseHoldings
        );
    }


    // =========================================================
    // GET ALL HOLDINGS
    // =========================================================

    public List<CryptoHolding> getAllHoldings() {

        return holdings;
    }


    // =========================================================
    // DELETE HOLDING
    // =========================================================

    public boolean deleteHolding(int id) {

        boolean deleted =
                cryptoDAO.deleteHolding(id);

        if (deleted) {

            holdings.removeIf(
                    holding ->
                            holding.getId() == id
            );
        }

        return deleted;
    }


    // =========================================================
    // TOTAL INVESTED
    // =========================================================

    public double calculateTotalInvested() {

        return holdings.stream()
                .mapToDouble(
                        CryptoHolding::getInvestedAmount
                )
                .sum();
    }


    // =========================================================
    // CURRENT PORTFOLIO VALUE
    // =========================================================

    public double calculateCurrentValue(
            Map<String, Double> currentPrices) {

        if (currentPrices == null ||
                currentPrices.isEmpty()) {

            return 0;
        }

        double total = 0;

        for (CryptoHolding holding :
                holdings) {

            Double currentPrice =
                    currentPrices.get(
                            holding.getAssetId()
                    );

            if (currentPrice != null) {

                total +=
                        holding.getQuantity()
                                * currentPrice;
            }
        }

        return total;
    }


    // =========================================================
    // PROFIT / LOSS
    // =========================================================

    public double calculateProfitLoss(
            Map<String, Double> currentPrices) {

        double currentValue =
                calculateCurrentValue(
                        currentPrices
                );

        double invested =
                calculateTotalInvested();

        return currentValue - invested;
    }


    // =========================================================
    // RETURN PERCENTAGE
    // =========================================================

    public double calculateReturnPercentage(
            Map<String, Double> currentPrices) {

        double invested =
                calculateTotalInvested();

        if (invested <= 0) {
            return 0;
        }

        double profitLoss =
                calculateProfitLoss(
                        currentPrices
                );

        return
                (profitLoss / invested) * 100;
    }
}