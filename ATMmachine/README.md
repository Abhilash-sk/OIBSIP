# 🏦 Console ATM Machine

A simple **console-based ATM Machine simulation built with Java and Object-Oriented Programming (OOP)**.

This project simulates basic banking operations such as user authentication, checking transaction history, depositing money, withdrawing money, and transferring money between accounts.

## 📌 Features

* 🔐 **User Authentication**

  * Login using User ID and PIN
  * Maximum of 3 login attempts
  * Account access is denied after 3 failed attempts

* 💰 **Withdraw Money**

  * Check current balance
  * Withdraw a specified amount
  * Prevent withdrawals when the balance is insufficient

* 💵 **Deposit Money**

  * Deposit money into the current account
  * Automatically update the account balance

* 🔄 **Money Transfer**

  * Transfer money to another registered account
  * Verify that the recipient account exists
  * Prevent transfers to the same account
  * Check for sufficient funds

* 📜 **Transaction History**

  * Records deposits
  * Records withdrawals
  * Records incoming transfers
  * Records outgoing transfers
  * Stores transaction timestamps

* 🚪 **Quit**

  * Safely exit the ATM application

## 🛠️ Technologies Used

* **Java**
* **Object-Oriented Programming**
* Java Collections (`ArrayList`, `HashMap`)
* `LocalDateTime`
* Console-based input using `Scanner`

## 📂 Project Structure

```text
ATM-Machine/
│
├── Account.java
├── ATM.java
├── Bank.java
├── Main.java
└── Transaction.java
```

### `Account.java`

Represents a bank account and manages:

* User ID
* PIN
* Account balance
* Transaction history
* Deposits
* Withdrawals
* Transfers

Each account maintains its transactions using an `ArrayList`.

### `ATM.java`

Handles the main ATM functionality, including:

* Authentication
* Main menu
* Withdrawals
* Deposits
* Transfers
* Transaction history
* User input

The application provides five main menu options: Transaction History, Withdraw, Deposit, Transfer, and Quit.

### `Bank.java`

Manages the available accounts using a `HashMap`.

The project currently includes three sample accounts:

```text
User ID: user1    PIN: 1234    Balance: $1000.00
User ID: user2    PIN: 2222    Balance: $500.00
User ID: alice    PIN: 0000    Balance: $1200.00
```

These accounts are initialized when the `Bank` object is created.

### `Transaction.java`

Represents individual banking transactions.

Supported transaction types are:

```text
WITHDRAW
DEPOSIT
TRANSFER_OUT
TRANSFER_IN
```

Each transaction stores its type, amount, details, and timestamp.

### `Main.java`

The entry point of the application.

It creates the `Bank` and `ATM` objects and starts the ATM application.

## 🚀 How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ATM-Machine.git
```

### 2. Navigate to the Project

```bash
cd ATM-Machine
```

### 3. Compile the Java Files

```bash
javac *.java
```

### 4. Run the Application

```bash
java Main
```

## 🔑 Sample Login Credentials

| User ID |    PIN | Initial Balance |
| ------- | -----: | --------------: |
| `user1` | `1234` |        $1000.00 |
| `user2` | `2222` |         $500.00 |
| `alice` | `0000` |        $1200.00 |

## 🖥️ Application Flow

```text
           ┌──────────────────┐
           │    Start ATM     │
           └────────┬─────────┘
                    ↓
           ┌──────────────────┐
           │ User ID + PIN    │
           └────────┬─────────┘
                    ↓
              Authentication
               ↙          ↘
          Failed           Successful
            ↓                   ↓
       Max 3 Attempts      Main Menu
                                │
              ┌─────────────────┼─────────────────┐
              ↓                 ↓                 ↓
        Transaction         Withdraw          Deposit
         History                │                 │
              ↓                 └────────┬────────┘
              │                          ↓
              └──────────────→       Transfer
                                         │
                                         ↓
                                       Quit
```

## 📋 Main Menu

After successful authentication, the following menu is displayed:

```text
Main Menu:
1. Transaction History
2. Withdraw
3. Deposit
4. Transfer
5. Quit
```

The application allows only three authentication attempts before access is denied.

## 💡 OOP Concepts Demonstrated

This project demonstrates several important Java OOP and programming concepts:

* **Classes and Objects**
* **Encapsulation**
* **Constructors**
* **Methods**
* **Enums**
* **Collections**
* **Object Composition**
* **Data Validation**
* **Exception Handling**
* **Separation of Responsibilities**

The project separates account management, ATM operations, banking data, transactions, and application startup into different classes.

## 🔄 Transaction System

Every successful financial operation creates a transaction record.

For example:

```text
[2026-08-11 13:30:25] DEPOSIT: $500.00
[2026-08-11 13:31:10] WITHDRAW: $200.00
[2026-08-11 13:32:05] TRANSFER_OUT: $100.00 (to user2)
```

Transactions are timestamped using Java's `LocalDateTime`.

## ⚠️ Project Scope

This is an **educational ATM simulation**, not a real banking application.

The project currently stores sample account credentials and balances directly in the Java application. It does not use a database or real banking infrastructure.

## 🔮 Future Improvements

Possible improvements include:

* [ ] Add a graphical user interface (GUI)
* [ ] Add database support using MySQL
* [ ] Encrypt user PINs
* [ ] Add account creation
* [ ] Add account deletion
* [ ] Add balance inquiry option
* [ ] Add transaction persistence
* [ ] Add admin functionality
* [ ] Add receipt generation
* [ ] Improve input validation
* [ ] Add unit tests
* [ ] Add Maven/Gradle project structure

## 👨‍💻 Author

**Abhimanyu**

Computer Science & Engineering

---

⭐ If you found this project useful, consider giving the repository a star!
