# Smart Finance Manager

A Java-based personal finance management application that helps users track expenses, manage monthly budgets, analyze spending patterns, handle recurring expenses, export data, and track a small cryptocurrency portfolio using live market prices.

The project uses a layered architecture with MySQL persistence through JDBC and integrates the CoinGecko API for live cryptocurrency prices.

---

## Features

### Expense Management

- Add expenses with amount, date, category, description, and frequency
- View all expenses
- Update existing expenses
- Delete expenses
- Search expenses by category or description
- Filter expenses by category
- Filter expenses by amount range
- Sort expenses by amount
- Sort expenses by date
- Support for one-time, daily, weekly, monthly, and yearly expenses

### Financial Analytics

- Calculate monthly spending
- Calculate category-wise spending
- Calculate total spending
- Calculate average expense
- Identify highest and lowest expenses
- Identify top spending category
- Generate spending insights
- Calculate projected monthly recurring expenses

### Budget Management

- Set a monthly budget
- View budget status
- Calculate budget utilization percentage
- Calculate remaining budget
- Detect over-budget conditions

### Recurring Expenses

Supports:

- One-time
- Daily
- Weekly
- Monthly
- Yearly

The application calculates:

- Next occurrence
- Estimated monthly recurring cost
- Total projected recurring expenses

### CSV Export

Expenses can be exported into:
expenses_export.csv
The generated CSV file is excluded from Git using .gitignore.


## Cryptocurrency Portfolio
The project also includes a small cryptocurrency portfolio module.

### Features:
- Add cryptocurrency holdings
- View portfolio
- Delete holdings
- Store asset ID, symbol, quantity, buy price, and purchase date
- Fetch live INR prices using CoinGecko
- Calculate invested amount
- Calculate current portfolio value
- Calculate profit/loss
- Calculate portfolio return percentage

  
## Architecture
The project follows a layered architecture:

                         SMART FINANCE MANAGER

                                Main
                                 |
                                 v
                          ExpenseManager
                                 |
              +------------------+------------------+
              |                  |                  |
              v                  v                  v
       ExpenseService      BudgetService      CryptoService
              |                  |                  |
              v                  v                  v
          ExpenseDAO         BudgetDAO         CryptoDAO
              |                  |                  |
              +------------------+------------------+
                                 |
                                 v
                                JDBC
                                 |
                                 v
                               MySQL


                         CryptoPriceService
                                 |
                                 v
                           CoinGecko API

This separation keeps application flow, business logic, and database access organized and maintainable.

## Technology Stack
- Java 17
- MySQL
- JDBC
- Maven
- JUnit 5
- Gson
- CoinGecko REST API
- Git / GitHub

  
## Project Structure
ExpenseTrackerProject/
│
├── pom.xml
├── README.md
├── .gitignore
│
└── src/
    ├── main/
    │   └── java/
    │       ├── Main.java
    │       ├── ExpenseManager.java
    │       ├── Expense.java
    │       ├── Frequency.java
    │       ├── DatabaseConnection.java
    │       ├── ExpenseDAO.java
    │       ├── ExpenseService.java
    │       ├── BudgetDAO.java
    │       ├── BudgetService.java
    │       ├── RecurringExpenseService.java
    │       ├── CryptoHolding.java
    │       ├── CryptoDAO.java
    │       ├── CryptoService.java
    │       └── CryptoPriceService.java
    │
    └── test/
        └── java/
            ├── BudgetServiceTest.java
            ├── ExpenseServiceTest.java
            ├── ExpenseTest.java
            └── RecurringExpenseServiceTest.java

## Database
The application uses a MySQL database named:
smart_expense_manager

### Expenses Table
CREATE TABLE expenses (
    id INT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    expense_date DATE NOT NULL,
    category VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    frequency VARCHAR(20) NOT NULL
);

### Budgets Table
CREATE TABLE budgets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    budget_month VARCHAR(7) NOT NULL UNIQUE,
    amount DOUBLE NOT NULL
);

### Crypto Holdings Table
CREATE TABLE crypto_holdings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id VARCHAR(50) NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    quantity DOUBLE NOT NULL,
    buy_price DOUBLE NOT NULL,
    purchase_date DATE NOT NULL
);

The database also uses indexes and validation triggers.

## Configuration
The application uses environment variables for sensitive credentials.
MySQL Password
Set:
export MYSQL_PASSWORD='your_mysql_password'

CoinGecko API Key
Set:
export COINGECKO_API_KEY='your_coingecko_api_key'

Never commit real passwords or API keys to GitHub.
You can verify that the variables are set without printing their actual values:
echo ${MYSQL_PASSWORD:+MYSQL_PASSWORD_SET}
echo ${COINGECKO_API_KEY:+API_KEY_SET}

Expected output:
MYSQL_PASSWORD_SET
API_KEY_SET

## Build and Test
From the project root, run:
mvn clean test

The project currently contains 30 automated JUnit tests covering:
- Expense validation
- Expense service operations
- Budget service operations
- Recurring expense calculations
Current test result:
Tests run: 30
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS

## Run the Application
Set the required environment variables:
export MYSQL_PASSWORD='your_mysql_password'
export COINGECKO_API_KEY='your_coingecko_api_key'

Then run:
mvn exec:java -Dexec.mainClass=Main

## Example Crypto Portfolio Calculation
Example holding:
Asset: bitcoin
Quantity: 0.01
Buy Price: ₹7,000,000

The application calculates:
Invested Amount
Current Price
Current Portfolio Value
Profit/Loss
Return %

The formulas used are:
Invested Amount = Quantity × Buy Price

Current Value = Quantity × Current Market Price

Profit/Loss = Current Value − Invested Amount

Return % = (Profit/Loss / Invested Amount) × 100

Live cryptocurrency prices are retrieved from CoinGecko in INR.

## Testing Performed
The application has been tested for:
- Expense CRUD operations
- MySQL persistence
- Budget management
- Recurring expense calculations
- Spending analytics
- CSV export
- Crypto holding persistence
- Live CoinGecko price retrieval
- Portfolio valuation
- Profit/loss calculation
- Return percentage calculation
- Crypto deletion
All 30 JUnit tests pass successfully, and the major application workflows have been tested end-to-end.

## Future Enhancements
Possible future improvements:
- JavaFX or web-based graphical interface
- Expense charts and dashboards
- User authentication
- Multi-user support
- Portfolio history and performance tracking
- More advanced financial reports
- Spring Boot REST API
- Dockerized deployment
- Additional market data integrations

  
### Author
### Vanisha Sharath
Computer Science Engineering Student
