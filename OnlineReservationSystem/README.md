🚆 Online Reservation System

A Java-based desktop Online Railway Reservation System built with Java Swing and SQLite.The project provides a simple graphical interface for booking, searching, viewing, and cancelling railway reservations, along with an admin dashboard for managing bookings and seat inventory.

✨ Features

👤 User Features

User login authentication

Railway ticket booking

Multiple passenger support

Train and class selection

Automatic fare calculation

PNR generation

Search reservation using PNR

View reservation details

Cancel reservations

Seat availability management

Ticket display

PDF ticket export

QR code generation

Dashboard statistics

🔐 Admin Features

Separate admin login

Admin dashboard

View all bookings

View total bookings

View total passengers

View total revenue

View seat inventory

Refresh booking and inventory data

🛠️ Technologies Used

Java 19

Java Swing – Desktop GUI

JDBC – Database connectivity

SQLite – Local database

Apache PDFBox – PDF ticket generation

QR Code Generator – QR code support

VS Code – Development environment

🗂️ Project Structure

OnlineReservationSystem/
│
├── lib/
│   ├── core-3.5.4.jar
│   ├── pdfbox-app-3.0.8.jar
│   └── sqlite-jdbc-3.50.1.0.jar
│
├── src/
│   └── com/
│       └── abhimanyu/
│           └── reservation/
│               ├── AdminDashboardFrame.java
│               ├── App.java
│               ├── BookingHistoryFrame.java
│               ├── BookingService.java
│               ├── CancellationFrame.java
│               ├── CancellationService.java
│               ├── DashboardFrame.java
│               ├── DashboardStats.java
│               ├── DatabaseConnection.java
│               ├── DatabaseInitializer.java
│               ├── FareCalculator.java
│               ├── LoginFrame.java
│               ├── Passenger.java
│               ├── PassengerDAO.java
│               ├── PdfTicketExporter.java
│               ├── PNRGenerator.java
│               ├── QRCodeGenerator.java
│               ├── Reservation.java
│               ├── ReservationDAO.java
│               ├── ReservationFrame.java
│               ├── SearchFrame.java
│               ├── SeatAllocator.java
│               ├── SeatInventory.java
│               ├── SeatInventoryDAO.java
│               ├── SeatInventoryFrame.java
│               ├── StatisticsDAO.java
│               ├── TicketFrame.java
│               ├── TrainData.java
│               ├── UITheme.java
│               ├── User.java
│               ├── UserDAO.java
│               └── ViewReservationsFrame.java
│
├── reservation.db
└── README.md

🏗️ Architecture

The application follows a simple layered structure:

Swing UI
   ↓
Service Layer
   ↓
DAO Layer
   ↓
JDBC
   ↓
SQLite Database

Main Components

UI Layer

LoginFrame

DashboardFrame

ReservationFrame

SearchFrame

CancellationFrame

TicketFrame

AdminDashboardFrame

BookingHistoryFrame

SeatInventoryFrame

Service Layer

BookingService

CancellationService

DAO Layer

ReservationDAO

PassengerDAO

SeatInventoryDAO

UserDAO

StatisticsDAO

Model Classes

Reservation

Passenger

SeatInventory

User

DashboardStats

Utility Components

DatabaseConnection

DatabaseInitializer

FareCalculator

PNRGenerator

SeatAllocator

PdfTicketExporter

QRCodeGenerator

UITheme

🗄️ Database

The application uses SQLite as a local database.

The database contains the following main tables:

users

reservations

passengers

seat_inventory

The application initializes the database automatically through:

DatabaseInitializer.initializeDatabase();
DatabaseInitializer.initializeSeatInventory();

The main application entry point is:

com.abhimanyu.reservation.App

🔑 Default Login Credentials

User

Username: abhimanyu
Password: abhimanyu123

Admin

Username: admin
Password: admin123

These credentials are intended for the local/demo version of the project.

🚀 How to Run

1. Requirements

Install:

JDK 19 or later

Visual Studio Code

Java Extension Pack for VS Code

2. Clone the Repository

git clone <YOUR_GITHUB_REPOSITORY_URL>
cd OnlineReservationSystem

3. Check Libraries

Make sure the required JAR files are available inside the lib folder:

lib/
├── core-3.5.4.jar
├── pdfbox-app-3.0.8.jar
└── sqlite-jdbc-3.50.1.0.jar

4. Run the Application

Run:

src/com/abhimanyu/reservation/App.java

The application initializes the SQLite database and seat inventory before opening the login screen.

🔄 Application Flow

Start Application
       ↓
Initialize SQLite Database
       ↓
Initialize Seat Inventory
       ↓
Login
   ↙       ↘
User       Admin
 ↓           ↓
Dashboard   Admin Dashboard
 ↓           ↓
Book/Search/  Bookings/
Cancel/View   Seat Inventory
 ↓
Ticket + PNR
 ↓
SQLite Database

🎫 Reservation Flow

Login as a user.

Open Book Ticket.

Select train and class.

Enter passenger details.

Check available seats.

Calculate the total fare.

Generate a PNR.

Save reservation and passenger details.

Reduce the available seat count.

Display the generated ticket.

Generate PDF/QR information where applicable.

❌ Cancellation Flow

Search for a reservation using the PNR.

Retrieve reservation and passenger information.

Delete passenger records.

Delete the reservation.

Release the previously occupied seats.

Commit the transaction.

Booking and cancellation operations use database transactions so related database changes can be committed or rolled back together.

📊 Admin Dashboard

The admin dashboard provides:

Total bookings

Total passengers

Total revenue

Booking history

Seat inventory

The seat inventory displays train number, class, and available seats.

🧪 Sample Trains

The initial seat inventory contains these train numbers:

12627
12628
17307
16591

Initial classes include:

Sleeper
3AC
2AC
1AC

📌 Project Purpose

This project was developed as a Java desktop application to practice:

Object-Oriented Programming

Java Swing GUI development

JDBC

SQL and database design

DAO and service-layer architecture

Database transactions

CRUD operations

Authentication and role-based access

File/PDF generation

QR code generation

Building a complete Java application from UI to database

🔮 Future Improvements

Possible future improvements include:

Password hashing instead of storing plain-text passwords

Online/cloud database support

Real railway API integration

Improved seat/coach allocation

User registration

Password reset

Email/SMS ticket notifications

Better validation and error handling

Maven or Gradle dependency management

Automated unit and integration tests

Packaging the application as an executable installer

👨‍💻 Author

Abhimanyu

Computer Science & Engineering
