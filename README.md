# 💰 Smart Expense Manager (Java)

A modular and feature-rich **console-based expense management system** built using Core Java.
This application allows users to efficiently track, analyze, and manage their personal expenses with advanced features like **sorting, analytics, budgeting, and persistent storage**.

---

## 🚀 Features

* ➕ Add new expenses (amount, date, category, description)
* 📋 View all recorded expenses
* ❌ Delete expenses by ID
* 📊 Category-wise and monthly expense analysis
* 🔍 Sort expenses by **amount** and **date**
* 💰 Set monthly budget with **overspending alerts**
* 📈 View spending insights:

  * Total spending
  * Average expense
  * Top spending category
* 💾 Persistent storage using file handling (data saved & loaded automatically)
* ⚠️ Robust input validation and error handling

---

## 🛠 Tech Stack

* **Java (Core Java)**
* **Collections Framework** (ArrayList, HashMap, Comparator)
* **OOP Principles** (Encapsulation, modular design)
* **File Handling** (FileWriter, BufferedReader)

---

## 📂 Project Structure

```
ExpenseTrackerProject/
├── Expense.java
├── ExpenseManager.java
├── Main.java
└── expenses.txt   (generated automatically)
```
## 🧠 Key Concepts Demonstrated

* Object-Oriented Programming (OOP)
* Data Structures using Java Collections
* Sorting using Comparator
* File I/O and persistence
* Exception handling and input validation
* Modular and clean code design

---

## ▶️ How to Run

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/ExpenseTrackerProject.git
cd ExpenseTrackerProject
```

---

### 2️⃣ Compile the Program

Make sure Java is installed (`javac -version`)

```bash
javac Main.java ExpenseManager.java Expense.java
```

---

### 3️⃣ Run the Application

```bash
java Main
```

---

### 🖥️ Sample Menu

```
====== Smart Expense Manager ======
1. Add Expense
2. View Expenses
3. Delete Expense
4. Monthly Total
5. Category Total
6. Sort by Amount
7. Sort by Date
8. Save & Exit
9. Set Monthly Budget
10. View Spending Insights
```

---


