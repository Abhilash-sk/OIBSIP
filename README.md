# OIBSIP
Project: Console ATM Machine (Java)
Overview
A simple console-based ATM simulation written in Java using object-oriented design. Supports user authentication (PIN), transaction history, withdraw, deposit, transfer between accounts, and session-based logging. Designed for learning/demo purposes.

Features
User login with PIN (access denied after 3 failed attempts)
Main menu: Transaction History, Withdraw, Deposit, Transfer, Quit
Balance checks before withdrawals/transfers; displays "Insufficient Funds"
All transactions logged in an ArrayList per account (session)
Transfer updates both sender and recipient accounts and logs both sides
Seeded sample accounts for testing
Tech Stack
Java (standard console application)
Object-oriented design with separate classes:
Main.java
Bank.java
ATM.java
Account.java
Transaction.java
Repository layout
/src/
Main.java
Bank.java
ATM.java
Account.java
Transaction.java
README.md
.gitignore (optional)
build/ or out/ (optional compiled classes)
Setup & Requirements
Java JDK 8 or later
(Optional) IDE: VS Code, IntelliJ IDEA, Eclipse
(Optional) Build tools: Maven or Gradle (not required for simple compilation)
Compile & Run (command line)
Open terminal in project root (where .java files are located).
Compile: javac *.java
Run: java Main
If your sources are inside src/ and you want compiled classes in out/:

javac -d out src/*.java
java -cp out Main
Usage
Run the program.
Enter a User ID and PIN (example seeded accounts: user1/1234, user2/2222, alice/0000).
After successful login, use the menu:
1: Transaction History — view session transactions and current balance
2: Withdraw — enter amount; validated against balance
3: Deposit — enter amount; adds to balance
4: Transfer — enter recipient ID and amount; validates recipient and balance
5: Quit — exit program
Notes
Transactions are stored per account in memory (ArrayList) for the running session. No persistence across runs.
Ensure file/class names match (case-sensitive). If using packages, place files in corresponding directories and include a package declaration.
Extending the project
Ideas for enhancements:

Persist accounts/transactions to disk (JSON, CSV, or database)
Add account creation and PIN change features
Improve input validation and formatting
Implement a GUI or web front-end
Add unit tests and CI (GitHub Actions)
Contributing
Fork the repository.
Create a feature branch: git checkout -b feature-name
Commit changes: git commit -m "Add feature"
Push and open a pull request.
License
Add your preferred license (e.g., MIT). Example:
MIT License — see LICENSE file.

Contact
For questions or suggestions, open an issue in this repository.

Feel free to tell me if you want this README tailored for Maven/Gradle, include sample screenshots, or a LICENSE file added
