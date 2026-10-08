import java.time.LocalDate;

public class CryptoHolding {

    private final int id;
    private final String assetId;
    private final String symbol;
    private final double quantity;
    private final double buyPrice;
    private final LocalDate purchaseDate;

    public CryptoHolding(
            int id,
            String assetId,
            String symbol,
            double quantity,
            double buyPrice,
            LocalDate purchaseDate) {

        if (assetId == null ||
                assetId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Asset ID cannot be empty"
            );
        }

        if (symbol == null ||
                symbol.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Symbol cannot be empty"
            );
        }

        if (quantity <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        if (buyPrice <= 0) {

            throw new IllegalArgumentException(
                    "Buy price must be greater than zero"
            );
        }

        if (purchaseDate == null) {

            throw new IllegalArgumentException(
                    "Purchase date cannot be null"
            );
        }

        this.id = id;
        this.assetId = assetId.trim().toLowerCase();
        this.symbol = symbol.trim().toUpperCase();
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public double getInvestedAmount() {
        return quantity * buyPrice;
    }

    @Override
    public String toString() {

        return String.format(
                "ID: %d | %s (%s) | Quantity: %.6f | Buy Price: %.2f | Invested: %.2f | Date: %s",
                id,
                assetId,
                symbol,
                quantity,
                buyPrice,
                getInvestedAmount(),
                purchaseDate
        );
    }
}